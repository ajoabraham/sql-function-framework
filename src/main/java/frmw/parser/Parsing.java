package frmw.parser;

import frmw.model.Formula;
import frmw.model.FormulaElement;
import frmw.model.FunctionRegistry;
import frmw.model.Join;
import frmw.model.exception.ParsingException;
import frmw.model.exception.SQLFrameworkInternalException;
import frmw.model.exception.WrongFunctionNameException;
import frmw.model.fun.FunctionSpec;
import frmw.model.position.PositionMap;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.error.ParseErrorDetails;
import org.codehaus.jparsec.error.ParserException;

import java.util.*;

import static com.google.common.collect.Iterables.addAll;
import static frmw.model.position.ProvidingPositionsOnExceptionAspect.currentFormulaPositions;
import static frmw.parser.Common.conditional;
import static frmw.parser.Common.withOperators;
import static frmw.parser.FunctionType.*;
import static java.lang.Character.isWhitespace;
import static org.codehaus.jparsec.Parser.Reference;
import static org.codehaus.jparsec.Parser.newReference;
import static org.codehaus.jparsec.Parsers.or;

/**
 * Application should have one instance of this class,
 * e.g. as final static variable or as a singleton through any DI-frameworks.
 * <p/>
 * Thread safe.
 *
 * @author Alexey Paramonov
 */
public class Parsing {

	private final Reference<FormulaElement> scalar = newReference();
	private final Reference<FormulaElement> aggregation = newReference();
	private final Reference<FormulaElement> olap = newReference();
	private final Reference<FormulaElement> commons = newReference();

	private final Parser<FormulaElement> parser;

	private final Parser<FormulaElement> joinParser;

	private final FunctionRegistry registry;

	public Parsing() {
		Parser<FormulaElement> p = withOperators(or(scalar.lazy(), aggregation.lazy(), this.olap.lazy(), commons.lazy()));

		Common c = new Common(p).withColumn();
		commons.set(or(c.parsers));

		Aggregations aggr = new Aggregations(scalar.lazy(), commons.lazy());
		aggr.parsers.add(c.customFun);
		aggregation.set(or(aggr.parsers).label(AGGREGATION.name()));

		Olap olap = new Olap(aggregation.lazy(), scalar.lazy(), commons.lazy());
		aggr.parsers.add(c.customFun);
		this.olap.set(or(olap.parsers).label(OLAP.name()));

		Scalars s = new Scalars(p);
		aggr.parsers.add(c.customFun);
		scalar.set(or(s.parsers).label(SCALAR.name()));

		parser = withOperators(or(aggregation.lazy(), this.olap.lazy(), scalar.lazy(), commons.lazy()));
		joinParser = joinParser();

		registry = new FunctionRegistry(aggr.specs, olap.specs, s.specs, c.specs);
	}

	private Parser<FormulaElement> joinParser() {
		Reference<FormulaElement> commons = newReference();
		Reference<FormulaElement> scalar = newReference();

		Parser<FormulaElement> p = withOperators(or(scalar.lazy(), commons.lazy()));

		Scalars s = new Scalars(p);
		scalar.set(or(s.parsers).label(SCALAR.name()));

		Common c = new Common(p).withTableColumn();
		commons.set(or(c.parsers));
		return conditional(p);
	}

	/**
	 * @throws WrongFunctionNameException if framework decides that user misspelled a function name
	 * @throws ParsingException           on any other error during parsing
	 */
	public Formula parse(String formula) {
		PositionMap map = new PositionMap();
		currentFormulaPositions.set(map);

		try {
			FormulaElement root = parser.parse(formula);
			return new Formula(root, map);
		} catch (ParserException e) {
			return convertException(formula, e);
		}
	}

	public Join parseJoin(String formula) {
		PositionMap map = new PositionMap();
		currentFormulaPositions.set(map);

		try {
			FormulaElement root = joinParser.parse(formula);
			return new Join(formula, root, map);
		} catch (ParserException e) {
			return convertException(formula, e);
		}
	}

	private <T> T convertException(String formula, ParserException e) {
		ParseErrorDetails details = e.getErrorDetails();
		if (details == null) {
			throw SQLFrameworkInternalException.wrap(e);
		}

		List<String> expected = details.getExpected();

		Set<FunctionType> types = EnumSet.noneOf(FunctionType.class);
		List<FunctionSpec> expectedFuncs = new ArrayList<FunctionSpec>();
		for (FunctionType type : EnumSet.allOf(FunctionType.class)) {
			if (expected.contains(type.name())) {
				types.add(type);
				Iterable<FunctionSpec> functions = type.functions(this);
				addAll(expectedFuncs, functions);
			}
		}

		int errorAt = details.getIndex();
		if (types.isEmpty()) {
			throw new ParsingException(errorAt, new HashSet<String>(expected), e);
		} else {
			String wrongName = findFunctionName(formula, errorAt);
			throw new WrongFunctionNameException(formula, errorAt, wrongName, types, expectedFuncs);
		}
	}

	/**
	 * @return registry of the functions that can be used in formula
	 */
	public FunctionRegistry registry() {
		return registry;
	}

	private static String findFunctionName(String formula, int errorAt) {
		int last = errorAt;
		while (last < formula.length()) {
			char c = formula.charAt(last);
			if (isWhitespace(c) || c == '(') {
				break;
			}
			last++;
		}

		return formula.substring(errorAt, last);
	}
}

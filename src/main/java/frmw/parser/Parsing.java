package frmw.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import frmw.model.Formula;
import frmw.model.FormulaElement;
import frmw.model.exception.ParsingException;
import frmw.model.exception.WrongFunctionNameException;
import frmw.model.position.PositionMap;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.error.ParseErrorDetails;
import org.codehaus.jparsec.error.ParserException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static frmw.model.position.ProvidingPositionsOnExceptionAspect.currentFormulaPositions;
import static frmw.parser.Common.withOperators;
import static frmw.parser.FunctionType.*;
import static java.lang.Character.isWhitespace;
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

	private final Parser.Reference<FormulaElement> scalar = Parser.newReference();
	private final Parser.Reference<FormulaElement> aggregation = Parser.newReference();
	private final Parser.Reference<FormulaElement> olap = Parser.newReference();
	private final Parser.Reference<FormulaElement> commons = Parser.newReference();

	private final Parser<FormulaElement> parser;

	private final List<String> scalarNames;
	private final List<String> aggregationNames;
	private final List<String> olapNames;
	private final Iterable<String> all;

	public Parsing() {
		Aggregations aggr = new Aggregations(scalar.lazy(), commons.lazy());
		aggregationNames = ImmutableList.copyOf(aggr.names);
		aggregation.set(or(aggr.parsers).label(AGGREGATION.name()));

		Olap olap = new Olap(aggregation.lazy(), scalar.lazy(), commons.lazy());
		olapNames = ImmutableList.copyOf(olap.names);
		this.olap.set(or(olap.parsers).label(OLAP.name()));

		Scalars s = new Scalars(scalar.lazy(), aggregation.lazy(), this.olap.lazy(), commons.lazy());
		scalarNames = ImmutableList.copyOf(s.names);
		scalar.set(or(s.parsers).label(SCALAR.name()));

		Common c = new Common(scalar.lazy(), aggregation.lazy(), commons.lazy());
		commons.set(or(c.parsers));

		parser = withOperators(or(aggregation.lazy(), this.olap.lazy(), scalar.lazy(), commons.lazy()));
		all = Iterables.concat(scalarNames, aggregationNames, olapNames);
	}

	/**
	 * @throws WrongFunctionNameException if framework decides that user misspelled a function name
	 * @throws ParsingException on any other error during parsing
	 */
	public Formula parse(String formula) {
		PositionMap map = new PositionMap();
		currentFormulaPositions.set(map);

		try {
			FormulaElement root = parser.parse(formula);
			return new Formula(root, map);
		} catch (ParserException e) {
			ParseErrorDetails details = e.getErrorDetails();
			List<String> expected = details.getExpected();

			Set<FunctionType> types = EnumSet.noneOf(FunctionType.class);
			List<String> expectedFuncs = new ArrayList<String>();
			for (FunctionType type : EnumSet.allOf(FunctionType.class)) {
				if (expected.contains(type.name())) {
					types.add(type);
					expectedFuncs.addAll(type.functions(this));
				}
			}

			int errorAt = details.getIndex();
			if (types.isEmpty()) {
				throw new ParsingException(errorAt, expected);
			} else {
				String wrongName = findFunctionName(formula, errorAt);
				throw new WrongFunctionNameException(errorAt, wrongName, types, expectedFuncs);
			}
		}
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

	public List<String> scalarFunctions() {
		return scalarNames;
	}

	public List<String> aggregationFunctions() {
		return aggregationNames;
	}

	public List<String> olapFunctions() {
		return olapNames;
	}

	public Iterable<String> functions() {
		return all;
	}
}

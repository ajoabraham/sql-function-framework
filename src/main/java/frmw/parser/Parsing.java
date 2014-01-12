package frmw.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import frmw.model.FormulaElement;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.error.ParserException;

import java.util.List;

import static frmw.parser.Common.withOperators;
import static frmw.parser.FunctionType.*;
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

	public FormulaElement parse(String formula) {
		try {
			return parser.parse(formula);
		} catch (ParserException e) {
			// todo: build SQLFrameworkException
			throw e;
		}
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

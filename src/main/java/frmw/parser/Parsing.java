package frmw.parser;

import com.google.common.collect.ImmutableList;
import frmw.model.FormulaElement;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.error.ParserException;

import java.util.List;

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
	private final Parser.Reference<FormulaElement> commons = Parser.newReference();

	private final Parser<FormulaElement> parser;

	private final List<String> scalarNames;
	private final List<String> aggregationNames;

	public Parsing() {
		Aggregations aggr = new Aggregations(scalar.lazy(), commons.lazy());
		aggregationNames = ImmutableList.copyOf(aggr.names);
		aggregation.set(or(aggr.parsers));

		Scalars s = new Scalars(scalar.lazy(), aggregation.lazy(), commons.lazy());
		scalarNames = ImmutableList.copyOf(s.names);
		scalar.set(or(s.parsers));

		Common c = new Common();
		commons.set(or(c.parsers));

		parser = or(aggregation.lazy(), scalar.lazy(), commons.lazy());
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
}

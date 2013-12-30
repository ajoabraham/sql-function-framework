package frmw.parser;

import frmw.model.FormulaElement;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.error.ParserException;

import java.util.List;

import static frmw.parser.Common.COLUMN;
import static frmw.parser.Common.scalar;

/**
 * Application should have one instance of this class,
 * e.g. as final static variable or as a singleton through any DI-frameworks.
 * <p/>
 * Thread safe.
 *
 * @author Alexey Paramonov
 */
public class Parsing {

	private final Aggregations aggr = new Aggregations();

	private final Parser<FormulaElement> parser;

	public Parsing() {
		parser = aggr.parser.or(COLUMN).or(scalar());
	}

	public FormulaElement parse(String formula) {
		try {
			return parser.parse(formula);
		} catch (ParserException e) {
			// todo: build SQLFrameworkException
			throw e;
		}
	}

	public List<String> aggregationFunctions() {
		return aggr.names;
	}
}

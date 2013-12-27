package frmw.parser;

import frmw.model.FormulaElement;
import org.codehaus.jparsec.error.ParserException;

import static frmw.parser.Aggregations.AGGREGATIONS;
import static frmw.parser.Common.COLUMN;
import static frmw.parser.Common.scalar;

/**
 * @author Alexey Paramonov
 */
public class Parsing {

	public static FormulaElement parse(String formula) {
		try {
			return AGGREGATIONS.or(COLUMN).or(scalar()).parse(formula);
		} catch (ParserException e) {
			// todo: build SQLFrameworkException
			throw e;
		}
	}
}

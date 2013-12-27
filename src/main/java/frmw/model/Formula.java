package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.position.PositionHolder;
import frmw.parser.Parsing;
import org.codehaus.jparsec.error.ParserException;

/**
 * @author Alexey Paramonov
 */
public class Formula {

	private final FormulaElement root;

	public Formula(String formula) {
		PositionHolder.INST.currentFormula(this);

		try {
			this.root = Parsing.parse(formula);
		} catch (ParserException e) {
			throw e;
//			throw new ParsingException();
		}
	}

	public String sql(Dialect dialect) {
		StringBuilder sb = new StringBuilder();
		root.sql(dialect, sb);
		return sb.toString();
	}
}

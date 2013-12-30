package frmw.dialect;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class TeradataSQL extends GenericSQL {

	@Override
	public void abs(StringBuilder sb, FormulaElement column) {
		sb.append("abs(");
		column.sql(this, sb);
		sb.append(')');
	}
}

package frmw.dialect;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Teradata14SQL extends TeradataSQL {

	@Override
	public void replace(StringBuilder sb, FormulaElement str, FormulaElement search, FormulaElement replace) {
		sb.append("oreplace(");
		str.sql(this, sb);
		sb.append(" from ");
		search.sql(this, sb);
		sb.append(" for ");
		replace.sql(this, sb);
		sb.append(')');
	}
}

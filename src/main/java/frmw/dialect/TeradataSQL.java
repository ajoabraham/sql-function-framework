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

	@Override
	public void exp(StringBuilder sb, FormulaElement arg) {
		sb.append("exp(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void ln(StringBuilder sb, FormulaElement arg) {
		sb.append("ln(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void log(StringBuilder sb, FormulaElement arg) {
		sb.append("log(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void mod(StringBuilder sb, FormulaElement arg) {
		sb.append("mod(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void pow(StringBuilder sb, FormulaElement arg) {
		sb.append("pow(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void round(StringBuilder sb, FormulaElement arg) {
		sb.append("round(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void sqrt(StringBuilder sb, FormulaElement arg) {
		sb.append("sqrt(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void stdDevS(StringBuilder sb, FormulaElement column) {
		sb.append("stddev_samp(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void stdDevP(StringBuilder sb, FormulaElement column) {
		sb.append("stddev_pop(");
		column.sql(this, sb);
		sb.append(')');
	}
}

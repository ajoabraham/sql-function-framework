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
	public void aCos(StringBuilder sb, FormulaElement arg) {
		sb.append("aCos(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void aCosH(StringBuilder sb, FormulaElement arg) {
		sb.append("aCosH(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void aSin(StringBuilder sb, FormulaElement arg) {
		sb.append("aSin(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void aSinH(StringBuilder sb, FormulaElement arg) {
		sb.append("aSinH(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void aTan(StringBuilder sb, FormulaElement arg) {
		sb.append("aTan(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void aTan2(StringBuilder sb, FormulaElement x, FormulaElement y) {
		sb.append("aTan2(");
		x.sql(this, sb);
		sb.append(", ");
		y.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void aTanH(StringBuilder sb, FormulaElement arg) {
		sb.append("aTanH(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void cos(StringBuilder sb, FormulaElement arg) {
		sb.append("cos(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void cosH(StringBuilder sb, FormulaElement arg) {
		sb.append("cosH(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void sin(StringBuilder sb, FormulaElement arg) {
		sb.append("sin(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void sinH(StringBuilder sb, FormulaElement arg) {
		sb.append("sinH(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void tan(StringBuilder sb, FormulaElement arg) {
		sb.append("tan(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void tanH(StringBuilder sb, FormulaElement arg) {
		sb.append("tanH(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void addMonths(StringBuilder sb, FormulaElement date, FormulaElement number) {
		sb.append("add_months(");
		date.sql(this, sb);
		sb.append(", ");
		number.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void random(StringBuilder sb, FormulaElement lower, FormulaElement upper) {
		sb.append("random(");
		lower.sql(this, sb);
		sb.append(", ");
		upper.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void index(StringBuilder sb, FormulaElement str, FormulaElement searched) {
		sb.append("index(");
		str.sql(this, sb);
		sb.append(", ");
		searched.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void zeroIfNull(StringBuilder sb, FormulaElement arg) {
		sb.append("ZeroIfNull(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void nullIfZero(StringBuilder sb, FormulaElement arg) {
		sb.append("NullIfZero(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void nullIf(StringBuilder sb, FormulaElement arg, FormulaElement nullable) {
		sb.append("NullIf(");
		arg.sql(this, sb);
		sb.append(", ");
		nullable.sql(this, sb);
		sb.append(')');
	}
}

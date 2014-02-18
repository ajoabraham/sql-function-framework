package frmw.dialect;

import frmw.model.FormulaElement;

/**
 * Supported versions are 2005 and above.
 */
public class MSSQL extends GenericSQL {

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
		sb.append("log(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void log(StringBuilder sb, FormulaElement arg) {
		sb.append("log10(");
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
		sb.append("power(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void round(StringBuilder sb, FormulaElement arg) {
		sb.append("round(");
		arg.sql(this, sb);
		sb.append(", 0)");
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
	public void aSin(StringBuilder sb, FormulaElement arg) {
		sb.append("aSin(");
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
		sb.append("ATN2(");
		x.sql(this, sb);
		sb.append(", ");
		y.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void cos(StringBuilder sb, FormulaElement arg) {
		sb.append("cos(");
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
	public void tan(StringBuilder sb, FormulaElement arg) {
		sb.append("tan(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void currentDate(StringBuilder sb) {
		sb.append("CAST(GETDATE() AS DATE)");
	}

	@Override
	public void addMonths(StringBuilder sb, FormulaElement date, FormulaElement number) {
		sb.append("DATEADD(month, ");
		number.sql(this, sb);
		sb.append(", ");
		date.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void random(StringBuilder sb, FormulaElement lower, FormulaElement upper) {
		sb.append("(RAND() * (");
		upper.sql(this, sb);
		sb.append(" - ");
		lower.sql(this, sb);
		sb.append(") + ");
		lower.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void index(StringBuilder sb, FormulaElement str, FormulaElement searched) {
		sb.append("CHARINDEX(");
		searched.sql(this, sb);
		sb.append(", ");
		str.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void zeroIfNull(StringBuilder sb, FormulaElement arg) {
		sb.append("coalesce(");
		arg.sql(this, sb);
		sb.append(", 0)");
	}

	@Override
	public void nullIfZero(StringBuilder sb, FormulaElement arg) {
		sb.append("NullIf(");
		arg.sql(this, sb);
		sb.append(", 0)");
	}

	@Override
	public void nullIf(StringBuilder sb, FormulaElement arg, FormulaElement nullable) {
		sb.append("NullIf(");
		arg.sql(this, sb);
		sb.append(", ");
		nullable.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void replace(StringBuilder sb, FormulaElement str, FormulaElement search, FormulaElement replace) {
		sb.append("replace(");
		str.sql(this, sb);
		sb.append(", ");
		search.sql(this, sb);
		sb.append(", ");
		replace.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void extractDateTime(StringBuilder sb, DateTimeElement e, FormulaElement arg) {
		String element;

		switch (e) {
			case DAY:
				element = "dd";
				break;
			case HOUR:
				element = "hh";
				break;
			case MINUTE:
				element = "mi";
				break;
			case MONTH:
				element = "mm";
				break;
			case SECOND:
				element = "ss";
				break;
			case WEEK:
				element = "ww";
				break;
			case YEAR:
				element = "yyyy";
				break;
			default:
				throw new UnsupportedOperationException();
		}

		sb.append("DATEPART(").append(element).append(", ");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void trim(StringBuilder sb, FormulaElement arg) {
		sb.append("LTRIM(RTRIM(");
		arg.sql(this, sb);
		sb.append("))");
	}

	@Override
	public void leftTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void rightTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public void substring(StringBuilder sb, FormulaElement str, FormulaElement start, FormulaElement length) {
		sb.append("substring(");
		str.sql(this, sb);
		sb.append(", ");
		start.sql(this, sb);
		sb.append(", ");
		length.sql(this, sb);
		sb.append(')');
	}
}

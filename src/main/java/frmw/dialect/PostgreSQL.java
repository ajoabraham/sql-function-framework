package frmw.dialect;

import frmw.model.FormulaElement;

/**
 * Supported versions are 8.3 and above.
 */
public class PostgreSQL extends GenericSQL {

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
		sb.append("power(");
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
		sb.append("aTan2(");
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
	public void extractDateTime(StringBuilder sb, DateTimeElement e, FormulaElement arg) {
		String element;

		switch (e) {
			case DAY:
				element = "Day";
				break;
			case HOUR:
				element = "Hour";
				break;
			case MINUTE:
				element = "Minute";
				break;
			case MONTH:
				element = "Month";
				break;
			case SECOND:
				element = "Second";
				break;
			case WEEK:
				element = "Week";
				break;
			case YEAR:
				element = "Year";
				break;
			default:
				throw new UnsupportedOperationException();
		}

		sb.append("extract(").append(element).append(" from ");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void addMonths(StringBuilder sb, FormulaElement date, FormulaElement number) {
		sb.append('(');
		date.sql(this, sb);
		sb.append(" + cast('");
		number.sql(this, sb);
		sb.append(" months' as interval))");
	}

	@Override
	public void random(StringBuilder sb, FormulaElement lower, FormulaElement upper) {
		sb.append("(random() * (");
		upper.sql(this, sb);
		sb.append(" - ");
		lower.sql(this, sb);
		sb.append(") + ");
		lower.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void leftTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed) {
		sb.append("trim(Leading ");
		trimmed.sql(this, sb);
		sb.append(" From ");
		str.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void rightTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed) {
		sb.append("trim(Trailing ");
		trimmed.sql(this, sb);
		sb.append(" From ");
		str.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void index(StringBuilder sb, FormulaElement str, FormulaElement searched) {
		sb.append("position(");
		searched.sql(this, sb);
		sb.append(" in ");
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
}

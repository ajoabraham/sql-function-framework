package frmw.dialect;

import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;

import static org.codehaus.jparsec.pattern.CharPredicates.IS_ALPHA_;
import static org.codehaus.jparsec.pattern.CharPredicates.IS_ALPHA_NUMERIC_;

/**
 * @author Alexey Paramonov
 */
public class GenericSQL implements Dialect {

	@Override
	public void avg(StringBuilder sb, FormulaElement column) {
		sb.append("avg(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void sum(StringBuilder sb, FormulaElement column) {
		sb.append("sum(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void validateColumnName(String name) {
		if (name == null || name.isEmpty()) {
			throw new SQLFrameworkException(name, "Column name should contain at least one character");
		}

		char firstChar = name.charAt(0);
		boolean firstOk = IS_ALPHA_.isChar(firstChar);
		if (!firstOk) {
			throw new SQLFrameworkException(name, "The first symbol in column name should be or letter [a-zA-Z] or underscore [_], but found : " + firstChar);
		}

		for (int i = 1 ; i < name.length() ; i++) {
			char ch = name.charAt(i);
			if (!IS_ALPHA_NUMERIC_.isChar(ch)) {
				throw new SQLFrameworkException(name, "Column name should contains numbers [0-9], letters [a-zA-Z] or underscores [_], but found : " + ch);
			}
		}
	}

	@Override
	public void rank(StringBuilder sb) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void min(StringBuilder sb, FormulaElement column) {
		sb.append("min(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void max(StringBuilder sb, FormulaElement column) {
		sb.append("max(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void count(StringBuilder sb, FormulaElement column) {
		sb.append("count(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void abs(StringBuilder sb, FormulaElement column) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exp(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ln(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void log(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mod(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pow(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void round(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sqrt(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stdDevP(StringBuilder sb, FormulaElement column) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stdDevS(StringBuilder sb, FormulaElement column) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aCos(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aCosH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aSin(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aSinH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aTan(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aTanH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cos(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cosH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sin(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sinH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tan(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tanH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void trim(StringBuilder sb, FormulaElement arg) {
		sb.append("trim(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void extractDateTime(StringBuilder sb, DateTimeElement e, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}
}

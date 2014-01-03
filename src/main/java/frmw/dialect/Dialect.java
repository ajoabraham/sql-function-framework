package frmw.dialect;

import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;

import java.lang.UnsupportedOperationException;

/**
 * All methods may throw {@link UnsupportedOperationException}
 * if dialect does not implement particular operation.
 *
 * @author Alexey Paramonov
 */
public interface Dialect {

	void avg(StringBuilder sb, FormulaElement column);

	void sum(StringBuilder sb, FormulaElement column);

	/**
	 * Validates column name for current database.
	 *
	 * @param name column name
	 * @throws SQLFrameworkException if validation isn't passed successfully
	 */
	void validateColumnName(String name) throws SQLFrameworkException;

	void rank(StringBuilder sb);

	void min(StringBuilder sb, FormulaElement column);

	void max(StringBuilder sb, FormulaElement column);

	void count(StringBuilder sb, FormulaElement column);

	void abs(StringBuilder sb, FormulaElement column);

	void exp(StringBuilder sb, FormulaElement arg);

	void ln(StringBuilder sb, FormulaElement arg);

	void log(StringBuilder sb, FormulaElement arg);

	void mod(StringBuilder sb, FormulaElement arg);

	void pow(StringBuilder sb, FormulaElement arg);

	void round(StringBuilder sb, FormulaElement arg);

	void sqrt(StringBuilder sb, FormulaElement arg);

	/**
	 * The population standard deviation
	 */
	void stdDevP(StringBuilder sb, FormulaElement column);

	/**
	 * The sample standard deviation
	 */
	void stdDevS(StringBuilder sb, FormulaElement column);

	void aCos(StringBuilder sb, FormulaElement arg);

	void aCosH(StringBuilder sb, FormulaElement arg);

	void aSin(StringBuilder sb, FormulaElement arg);

	void aSinH(StringBuilder sb, FormulaElement arg);

	void aTan(StringBuilder sb, FormulaElement arg);

	void aTanH(StringBuilder sb, FormulaElement arg);

	void cos(StringBuilder sb, FormulaElement arg);

	void cosH(StringBuilder sb, FormulaElement arg);

	void sin(StringBuilder sb, FormulaElement arg);

	void sinH(StringBuilder sb, FormulaElement arg);

	void tan(StringBuilder sb, FormulaElement arg);

	void tanH(StringBuilder sb, FormulaElement arg);

	void trim(StringBuilder sb, FormulaElement arg);

	void extractDateTime(StringBuilder sb, DateTimeElement e, FormulaElement arg);
}

package frmw.dialect;

import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;

/**
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
}

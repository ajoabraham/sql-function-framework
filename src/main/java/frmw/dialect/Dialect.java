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
}

package frmw.dialect;

import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.fun.aggregation.Aggregation;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.fun.olap.support.Rows;
import frmw.model.ifelse.Case;
import frmw.model.ifelse.SimpleCase;

import java.lang.UnsupportedOperationException;

/**
 * All methods may throw {@link UnsupportedOperationException}
 * if dialect does not implement particular operation.
 *
 * @author Alexey Paramonov
 */
public interface Dialect {

	void avg(StringBuilder sb, FormulaElement column, boolean distinct);

	void sum(StringBuilder sb, FormulaElement column, boolean distinct);

	/**
	 * Validates column name for current database.
	 *
	 * @param name column name
	 * @throws SQLFrameworkException if validation isn't passed successfully
	 */
	void validateColumnName(String name) throws SQLFrameworkException;

	void rank(StringBuilder sb);

	void min(StringBuilder sb, FormulaElement column, boolean distinct);

	void max(StringBuilder sb, FormulaElement column, boolean distinct);

	void count(StringBuilder sb, FormulaElement column, boolean distinct);

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
	void stdDevP(StringBuilder sb, FormulaElement column, boolean distinct);

	/**
	 * The sample standard deviation
	 */
	void stdDevS(StringBuilder sb, FormulaElement column, boolean distinct);

	void aCos(StringBuilder sb, FormulaElement arg);

	void aCosH(StringBuilder sb, FormulaElement arg);

	void aSin(StringBuilder sb, FormulaElement arg);

	void aSinH(StringBuilder sb, FormulaElement arg);

	void aTan(StringBuilder sb, FormulaElement arg);

	void aTan2(StringBuilder sb, FormulaElement x, FormulaElement y);

	void aTanH(StringBuilder sb, FormulaElement arg);

	void cos(StringBuilder sb, FormulaElement arg);

	void cosH(StringBuilder sb, FormulaElement arg);

	void sin(StringBuilder sb, FormulaElement arg);

	void sinH(StringBuilder sb, FormulaElement arg);

	void tan(StringBuilder sb, FormulaElement arg);

	void tanH(StringBuilder sb, FormulaElement arg);

	void trim(StringBuilder sb, FormulaElement arg);

	void extractDateTime(StringBuilder sb, DateTimeElement e, FormulaElement arg);

	void currentDate(StringBuilder sb);

	void currentTimestamp(StringBuilder sb);

	void addMonths(StringBuilder sb, FormulaElement date, FormulaElement number);

	void random(StringBuilder sb, FormulaElement lower, FormulaElement upper);

	void lower(StringBuilder sb, FormulaElement arg);

	void upper(StringBuilder sb, FormulaElement arg);

	void leftTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed);

	void rightTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed);

	void index(StringBuilder sb, FormulaElement str, FormulaElement searched);

	void substring(StringBuilder sb, FormulaElement str, FormulaElement start, FormulaElement length);

	void replace(StringBuilder sb, FormulaElement str, FormulaElement search, FormulaElement replace);

	void simpleCase(StringBuilder sb, SimpleCase inst);

	void searchedCase(StringBuilder sb, Case inst);

	void zeroIfNull(StringBuilder sb, FormulaElement arg);

	void nullIfZero(StringBuilder sb, FormulaElement arg);

	void nullIf(StringBuilder sb, FormulaElement arg, FormulaElement nullable);

	void window(StringBuilder sb, Aggregation arg, Rows preceding, Rows following, WindowParameters params);
}

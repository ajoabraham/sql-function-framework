package frmw.dialect;

import frmw.model.Formula;
import frmw.model.exception.UnsupportedFunctionException;
import org.junit.Test;

import static frmw.TestSupport.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class TeradataTest {

	@Test
	public void abs() {
		Formula f = new Formula("abs(\"name\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(name)", sql);
	}

	@Test
	public void abs_withoutQuotes() {
		Formula f = new Formula("abs(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(name)", sql);
	}

	@Test
	public void mixScalarWithAggregation() {
		Formula f = new Formula("abs(sum(\"name\"))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(sum(name))", sql);
	}

	@Test
	public void mixScalarWithScalar() {
		Formula f = new Formula("abs(abs(\"name\"))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(abs(name))", sql);
	}

	@Test
	public void mixScalarWithAggregation_withoutQuotes() {
		Formula f = new Formula("abs(sum(name))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(sum(name))", sql);
	}

	@Test
	public void exp() {
		Formula f = new Formula("exp(\"name\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("exp(name)", sql);
	}

	@Test
	public void ln() {
		Formula f = new Formula("ln(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(name)", sql);
	}

	@Test
	public void log() {
		Formula f = new Formula("log(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("log(name)", sql);
	}

	@Test
	public void mod() {
		Formula f = new Formula("mod(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("mod(name)", sql);
	}

	@Test
	public void pow() {
		Formula f = new Formula("pow(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("pow(name)", sql);
	}

	@Test
	public void round() {
		Formula f = new Formula("round(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("round(name)", sql);
	}

	@Test
	public void sqrt() {
		Formula f = new Formula("sqrt(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sqrt(name)", sql);
	}

	@Test
	public void stdDevS() {
		Formula f = new Formula("stdDevS(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("stddev_samp(name)", sql);
	}

	@Test
	public void stdDevP() {
		Formula f = new Formula("stdDevP(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("stddev_pop(name)", sql);
	}

	@Test
	public void aCos() {
		Formula f = new Formula("acos(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aCos(name)", sql);
	}

	@Test
	public void aCosH() {
		Formula f = new Formula("acosh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aCosH(name)", sql);
	}

	@Test
	public void aSin() {
		Formula f = new Formula("asin(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aSin(name)", sql);
	}

	@Test
	public void aSinH() {
		Formula f = new Formula("asinh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aSinH(name)", sql);
	}

	@Test
	public void aTan() {
		Formula f = new Formula("atan(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTan(name)", sql);
	}

	@Test
	public void aTan2() {
		Formula f = new Formula("atan2(12, 13)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTan2(12, 13)", sql);
	}

	@Test
	public void aTanH() {
		Formula f = new Formula("atanh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTanH(name)", sql);
	}

	@Test
	public void cos() {
		Formula f = new Formula("cos(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("cos(name)", sql);
	}

	@Test
	public void cosH() {
		Formula f = new Formula("cosh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("cosH(name)", sql);
	}

	@Test
	public void sin() {
		Formula f = new Formula("sin(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sin(name)", sql);
	}

	@Test
	public void sinH() {
		Formula f = new Formula("sinh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sinH(name)", sql);
	}

	@Test
	public void tan() {
		Formula f = new Formula("tan(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("tan(name)", sql);
	}

	@Test
	public void tanH() {
		Formula f = new Formula("tanH(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("tanH(name)", sql);
	}

	@Test
	public void year() {
		Formula f = new Formula("year(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Year from col1)", sql);
	}

	@Test
	public void month() {
		Formula f = new Formula("month(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Month from col1)", sql);
	}

	@Test
	public void day() {
		Formula f = new Formula("day(currentDate())", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Day from CURRENT_DATE)", sql);
	}

	@Test
	public void week() {
		Formula f = new Formula("week(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Week from col1)", sql);
	}

	@Test
	public void hour() {
		Formula f = new Formula("hour(currentTimestamp())", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Hour from CURRENT_TIMESTAMP)", sql);
	}

	@Test
	public void minute() {
		Formula f = new Formula("minute(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Minute from col1)", sql);
	}

	@Test
	public void second() {
		Formula f = new Formula("second(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Second from col1)", sql);
	}

	@Test
	public void addMonths() {
		Formula f = new Formula("addMonths(col1, 5)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("add_months(col1, 5)", sql);
	}

	@Test
	public void random() {
		Formula f = new Formula("random(1, 100)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("random(1, 100)", sql);
	}

	@Test(expected = UnsupportedFunctionException.class)
	public void teradataDoesntSupportReplace() {
		Formula f = new Formula("replace(col1, 'old_data', 'new_data')", PARSER);
		f.sql(TERADATA_SQL);
	}

	@Test
	public void substring() {
		Formula f = new Formula("substring(col1, 2, 10)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("substring(col1 from 2 for 10)", sql);
	}

	@Test
	public void index() {
		Formula f = new Formula("index(col1, 'data')", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("index(col1, 'data')", sql);
	}

	@Test
	public void leftTrim() {
		Formula f = new Formula("leftTrim(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("trim(Leading ' ' From col1)", sql);
	}

	@Test
	public void leftTrimWithTrimmedSymbol() {
		Formula f = new Formula("leftTrim(\"col1\", 'x')", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("trim(Leading 'x' From col1)", sql);
	}

	@Test
	public void rightTrim() {
		Formula f = new Formula("rightTrim(\"col1\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("trim(Trailing ' ' From col1)", sql);
	}

	@Test
	public void rightTrimWithTrimmedSymbol() {
		Formula f = new Formula("rightTrim(\"col1\", 'x')", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("trim(Trailing 'x' From col1)", sql);
	}

	@Test
	public void nullIf() {
		Formula f = new Formula("nullIf(\"col1\", 0)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("NullIf(col1, 0)", sql);
	}

	@Test
	public void nullIfZero() {
		Formula f = new Formula("nullIfZero(\"col1\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("NullIfZero(col1)", sql);
	}

	@Test
	public void zeroIfNull() {
		Formula f = new Formula("zeroIfNull(\"col1\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ZeroIfNull(col1)", sql);
	}
}

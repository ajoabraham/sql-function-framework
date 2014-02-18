package frmw.dialect.teradata;

import frmw.model.Formula;
import frmw.model.exception.UnsupportedFunctionException;
import frmw.model.fun.olap.support.Order;
import org.junit.Test;

import static frmw.TestSupport.PARSER;
import static frmw.TestSupport.TERADATA_SQL;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Alexey Paramonov
 */
public class TeradataTest {

	@Test
	public void abs() {
		Formula f = PARSER.parse("abs(\"name\")");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(\"name\")", sql);
	}

	@Test
	public void abs_withoutQuotes() {
		Formula f = PARSER.parse("abs(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(\"name\")", sql);
	}

	@Test
	public void mixScalarWithAggregation() {
		Formula f = PARSER.parse("abs(sum(\"name\"))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(sum(\"name\"))", sql);
	}

	@Test
	public void mixScalarWithScalar() {
		Formula f = PARSER.parse("abs(abs(\"name\"))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(abs(\"name\"))", sql);
	}

	@Test
	public void mixScalarWithAggregation_withoutQuotes() {
		Formula f = PARSER.parse("abs(sum(name))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(sum(\"name\"))", sql);
	}

	@Test
	public void exp() {
		Formula f = PARSER.parse("exp(\"name\")");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("exp(\"name\")", sql);
	}

	@Test
	public void ln() {
		Formula f = PARSER.parse("ln(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(\"name\")", sql);
	}

	@Test
	public void log() {
		Formula f = PARSER.parse("log(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("log(\"name\")", sql);
	}

	@Test
	public void mod() {
		Formula f = PARSER.parse("mod(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("mod(\"name\")", sql);
	}

	@Test
	public void pow() {
		Formula f = PARSER.parse("pow(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("pow(\"name\")", sql);
	}

	@Test
	public void round() {
		Formula f = PARSER.parse("round(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("round(\"name\")", sql);
	}

	@Test
	public void sqrt() {
		Formula f = PARSER.parse("sqrt(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sqrt(\"name\")", sql);
	}

	@Test
	public void aCos() {
		Formula f = PARSER.parse("acos(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aCos(\"name\")", sql);
	}

	@Test
	public void aCosH() {
		Formula f = PARSER.parse("acosh(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aCosH(\"name\")", sql);
	}

	@Test
	public void aSin() {
		Formula f = PARSER.parse("asin(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aSin(\"name\")", sql);
	}

	@Test
	public void aSinH() {
		Formula f = PARSER.parse("asinh(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aSinH(\"name\")", sql);
	}

	@Test
	public void aTan() {
		Formula f = PARSER.parse("atan(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTan(\"name\")", sql);
	}

	@Test
	public void aTan2() {
		Formula f = PARSER.parse("atan2(12, 13)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTan2(12, 13)", sql);
	}

	@Test
	public void aTanH() {
		Formula f = PARSER.parse("atanh(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTanH(\"name\")", sql);
	}

	@Test
	public void cos() {
		Formula f = PARSER.parse("cos(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("cos(\"name\")", sql);
	}

	@Test
	public void cosH() {
		Formula f = PARSER.parse("cosh(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("cosH(\"name\")", sql);
	}

	@Test
	public void sin() {
		Formula f = PARSER.parse("sin(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sin(\"name\")", sql);
	}

	@Test
	public void sinH() {
		Formula f = PARSER.parse("sinh(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sinH(\"name\")", sql);
	}

	@Test
	public void tan() {
		Formula f = PARSER.parse("tan(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("tan(\"name\")", sql);
	}

	@Test
	public void tanH() {
		Formula f = PARSER.parse("tanH(name)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("tanH(\"name\")", sql);
	}

	@Test
	public void year() {
		Formula f = PARSER.parse("year(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Year from \"col1\")", sql);
	}

	@Test
	public void month() {
		Formula f = PARSER.parse("month(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Month from \"col1\")", sql);
	}

	@Test
	public void day() {
		Formula f = PARSER.parse("day(currentDate())");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Day from CURRENT_DATE)", sql);
	}

	@Test
	public void week() {
		Formula f = PARSER.parse("week(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Week from \"col1\")", sql);
	}

	@Test
	public void hour() {
		Formula f = PARSER.parse("hour(currentTimestamp())");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Hour from CURRENT_TIMESTAMP)", sql);
	}

	@Test
	public void minute() {
		Formula f = PARSER.parse("minute(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Minute from \"col1\")", sql);
	}

	@Test
	public void second() {
		Formula f = PARSER.parse("second(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("extract(Second from \"col1\")", sql);
	}

	@Test
	public void addMonths() {
		Formula f = PARSER.parse("addMonths(col1, 5)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("add_months(\"col1\", 5)", sql);
	}

	@Test
	public void random() {
		Formula f = PARSER.parse("random(1, 100)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("random(1, 100)", sql);
	}

	@Test(expected = UnsupportedFunctionException.class)
	public void teradataDoesntSupportReplace() {
		Formula f = PARSER.parse("replace(col1, 'old_data', 'new_data')");
		f.sql(TERADATA_SQL);
	}

	@Test
	public void substring() {
		Formula f = PARSER.parse("substring(col1, 2, 10)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("substring(\"col1\" from 2 for 10)", sql);
	}

	@Test
	public void index() {
		Formula f = PARSER.parse("index(col1, 'data')");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("index(\"col1\", 'data')", sql);
	}

	@Test
	public void nullIf() {
		Formula f = PARSER.parse("nullIf(\"col1\", 0)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("NullIf(\"col1\", 0)", sql);
	}

	@Test
	public void nullIfZero() {
		Formula f = PARSER.parse("nullIfZero(\"col1\")");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("NullIfZero(\"col1\")", sql);
	}

	@Test
	public void zeroIfNull() {
		Formula f = PARSER.parse("zeroIfNull(\"col1\")");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ZeroIfNull(\"col1\")", sql);
	}

	@Test
	public void complicatedFormula() {
		Formula f = PARSER.parse("" +
				"case when customWindow(avg(ln(col1) + 12), all, all) + count(sin(col3)) > customWindow(min(abs(col2) + 12), 12, 14) " +
				"or runningAvg(ln(col8)) + count(col9) <> col4 " +
				"then avg(col5) || customWindow(max(14), 100, 800) " +
				"else count(col6) + customWindow(count(col7), 1000, 15_000) end");

		assertEquals(5, f.windowParameters().size());
		assertEquals(4, f.aggregationParameters().size());
		assertEquals(0, f.rankParameters().size());
		assertThat(f.entityNames(), containsInAnyOrder("col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9"));
		assertEquals("CASE WHEN (((avg((ln(\"col1\") + 12)) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) + count(sin(\"col3\"))) > min((abs(\"col2\") + 12)) OVER ( ROWS BETWEEN 12 PRECEDING AND 14 FOLLOWING)) OR ((avg(ln(\"col8\")) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) + count(\"col9\")) <> \"col4\")) THEN (avg(\"col5\") || max(14) OVER ( ROWS BETWEEN 100 PRECEDING AND 800 FOLLOWING)) ELSE (count(\"col6\") + count(\"col7\") OVER ( ROWS BETWEEN 1000 PRECEDING AND 15000 FOLLOWING)) END", f.sql(TERADATA_SQL));
	}


	/**
	 * Z-Score is common algorithm used to identify
	 * outliers in a data set.  This is commonly referred to as
	 * how many standard of deviations does a data point vary from the average.
	 * <p/>
	 * z = ( Score - Avg )/ Std. Deviation
	 */
	@Test
	public void zScoreComplexFormulaTest() {
		Formula f = PARSER.parse("(col1 - movingavg(col1,13)) / customwindow(stddevp(col1),13,current row)");
		Formula f2 = PARSER.parse("week(col_date)");
		f.windowParameters().get(0).partition(f2.sql(TERADATA_SQL)).order("\"col_date\"", Order.DESC);
		f.windowParameters().get(1).partition(f2.sql(TERADATA_SQL)).order("\"col_date\"", Order.DESC);
		String expected = "((\"col1\" - avg(\"col1\") OVER ( PARTITION BY extract(Week from \"col_date\") ORDER BY \"col_date\" DESC ROWS 13 PRECEDING)) / stddev_pop(\"col1\") OVER ( PARTITION BY extract(Week from \"col_date\") ORDER BY \"col_date\" DESC ROWS BETWEEN 13 PRECEDING AND CURRENT ROW))";
		//System.out.println(f.sql(TERADATA_SQL));
		assertEquals(expected, f.sql(TERADATA_SQL));
	}
}

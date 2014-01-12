package frmw.dialect.generic;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class GenericTest {

	@Test
	public void avg() {
		Formula f = PARSER.parse("avg(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(\"name\")", sql);
	}

	@Test
	public void avg_distinct() {
		Formula f = PARSER.parse("avg(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(DISTINCT \"name\")", sql);
	}

	@Test
	public void avg_caseInsensitivity() {
		Formula f = PARSER.parse("AvG(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(\"name\")", sql);
	}

	@Test
	public void min() {
		Formula f = PARSER.parse("min(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(\"name\")", sql);
	}

	@Test
	public void min_distinct() {
		Formula f = PARSER.parse("min(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(DISTINCT \"name\")", sql);
	}

	@Test
	public void max() {
		Formula f = PARSER.parse("max(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("max(\"name\")", sql);
	}

	@Test
	public void max_distinct() {
		Formula f = PARSER.parse("max(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("max(DISTINCT \"name\")", sql);
	}

	@Test
	public void count() {
		Formula f = PARSER.parse("count(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(\"name\")", sql);
	}

	@Test
	public void count_distinct() {
		Formula f = PARSER.parse("count(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(DISTINCT \"name\")", sql);
	}

	@Test
	public void avgWithWhitespaces() {
		Formula f = PARSER.parse(" \t avg ( \" name \n\" ) \n\r");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(\"name\")", sql);
	}
        
	@Test
	public void trim() {
		Formula f = PARSER.parse("trim(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(\"name\")", sql);
	}

	@Test
	public void concat() {
		Formula f = PARSER.parse("\"col1\" || \"col2\"");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(\"col1\" || \"col2\")", sql);
	}

	@Test
	public void concatInFunction() {
		Formula f = PARSER.parse("count(trim(\"col1\" || \"col2\"))");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(trim((\"col1\" || \"col2\")))", sql);
	}

	@Test
	public void concatOperatorHasLowestPriorityThanArithmeticalOperators() {
		Formula f = PARSER.parse("col1 + col2 - col3 || col4 - col5");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(((col1 + col2) - col3) || (col4 - col5))", sql);
	}

	@Test
	public void currentDate() {
		Formula f = PARSER.parse("currentDate()");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CURRENT_DATE", sql);
	}

	@Test
	public void currentTimestamp() {
		Formula f = PARSER.parse("currentTimestamp()");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CURRENT_TIMESTAMP", sql);
	}

	@Test
	public void upper() {
		Formula f = PARSER.parse("upper(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("upper(col1)", sql);
	}

	@Test
	public void lower() {
		Formula f = PARSER.parse("lower(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("lower(col1)", sql);
	}

	@Test
	public void avgWithSpaceInColumnName() {
		Formula f = PARSER.parse("avg(\"column name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(\"column name\")", sql);
	}

	@Test
	public void sumWithArithmetic() {
		Formula f = PARSER.parse("sum(\"column name\"+2-4*col1/3)");
		assertEquals("sum(((\"column name\" + 2) - ((4 * col1) / 3)))", f.sql(GENERIC_SQL));
	}
}

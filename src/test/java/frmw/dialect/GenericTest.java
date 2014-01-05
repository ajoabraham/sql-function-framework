package frmw.dialect;

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
		Formula f = new Formula("avg(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(name)", sql);
	}

	@Test
	public void avg_caseInsensitivity() {
		Formula f = new Formula("AvG(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(name)", sql);
	}

	@Test
	public void min() {
		Formula f = new Formula("min(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(name)", sql);
	}

	@Test
	public void max() {
		Formula f = new Formula("max(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("max(name)", sql);
	}

	@Test
	public void count() {
		Formula f = new Formula("count(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(name)", sql);
	}

	@Test
	public void avgWithWhitespaces() {
		Formula f = new Formula(" \t avg ( \" name \n\" ) \n\r", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(name)", sql);
	}

	@Test
	public void trim() {
		Formula f = new Formula("trim(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(name)", sql);
	}

	@Test
	public void concat() {
		Formula f = new Formula("\"col1\" || \"col2\"", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(col1 || col2)", sql);
	}

	@Test
	public void concatInFunction() {
		Formula f = new Formula("count(trim(\"col1\" || \"col2\"))", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(trim((col1 || col2)))", sql);
	}

	@Test
	public void concatOperatorHasLowestPriorityThanArithmeticalOperators() {
		Formula f = new Formula("col1 + col2 - col3 || col4 - col5", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(((col1 + col2) - col3) || (col4 - col5))", sql);
	}

	@Test
	public void currentDate() {
		Formula f = new Formula("currentDate()", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CURRENT_DATE", sql);
	}

	@Test
	public void currentTimestamp() {
		Formula f = new Formula("currentTimestamp()", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CURRENT_TIMESTAMP", sql);
	}

	@Test
	public void upper() {
		Formula f = new Formula("upper(col1)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("upper(col1)", sql);
	}

	@Test
	public void lower() {
		Formula f = new Formula("lower(col1)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("lower(col1)", sql);
	}
}

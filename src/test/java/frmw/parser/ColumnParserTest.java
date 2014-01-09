package frmw.parser;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class ColumnParserTest {

	@Test
	public void columnNameStartedFromDigit() {
		Formula f = new Formula("\"1name\"", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"1name\"", sql);
	}

	@Test
	public void quotedColumnName() {
		Formula f = new Formula("\"name\"", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"name\"", sql);
	}

	@Test
	public void columnName() {
		Formula f = new Formula("name", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("name", sql);
	}

	@Test
	public void columnNameWithBlanks() {
		Formula f = new Formula(" \t name  \n", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("name", sql);
	}
}

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
		Formula f = PARSER.parse("\"1name\"");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"1name\"", sql);
	}

	@Test
	public void quotedColumnName() {
		Formula f = PARSER.parse("\"name\"");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"name\"", sql);
	}

	@Test
	public void columnName() {
		Formula f = PARSER.parse("name");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"name\"", sql);
	}

	@Test
	public void columnNameWithBlanks() {
		Formula f = PARSER.parse(" \t name  \n");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"name\"", sql);
	}
}

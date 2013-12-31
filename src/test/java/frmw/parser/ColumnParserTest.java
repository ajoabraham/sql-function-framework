package frmw.parser;

import frmw.model.Formula;
import frmw.model.exception.SQLFrameworkException;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexey Paramonov
 */
public class ColumnParserTest {

	@Test
	public void wrongColumnName() {
		try {
			Formula f = new Formula("\"1name\"", PARSER);
			f.sql(GENERIC_SQL);
			fail();
		} catch (SQLFrameworkException e) {
			assertEquals("1name", e.source);
			assertEquals(0, e.index());
			assertEquals(7, e.length());
		}
	}

	@Test
	public void quotedColumnName() {
		Formula f = new Formula("\"name\"", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("name", sql);
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

package frmw.parser;

import frmw.dialect.GenericSQL;
import frmw.model.Formula;
import frmw.model.exception.SQLFrameworkException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexey Paramonov
 */
public class ColumnParserTest {

	private final static GenericSQL DIALECT = new GenericSQL();

	@Test
	public void wrongColumnName() {
		try {
			Formula f = new Formula("\"1name\"");
			f.sql(DIALECT);
			fail();
		} catch (SQLFrameworkException e) {
			assertEquals("1name", e.source);
			assertEquals(0, e.index());
			assertEquals(7, e.length());
		}
	}
}

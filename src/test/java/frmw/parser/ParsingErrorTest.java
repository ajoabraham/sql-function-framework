package frmw.parser;

import frmw.model.Formula;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.exception.UnsupportedFunctionException;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexey Paramonov
 */
public class ParsingErrorTest {

	@Test
	public void notExistedFunction() {
		try {
			Formula f = new Formula("  ranl(\"name\")", PARSER);
			fail(f.sql(GENERIC_SQL));
		} catch (SQLFrameworkException e) {
//			assertEquals("ranl", e.source);
			assertEquals(0, e.index());
			assertEquals(12, e.length());
		}
	}

	@Test
	public void unsupportedOperation() {
		try {
			Formula f = new Formula("rank(\"name\")", PARSER);
			f.sql(GENERIC_SQL);
			fail();
		} catch (UnsupportedFunctionException e) {
			assertEquals("Rank", e.source);
			assertEquals("GenericSQL", e.dialect);
			assertEquals(0, e.index());
			assertEquals(12, e.length());
		}
	}
}

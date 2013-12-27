package frmw.parser;

import frmw.dialect.GenericSQL;
import frmw.model.Formula;
import frmw.model.exception.UnsupportedFunctionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexey Paramonov
 */
public class ParsingErrorTest {

	private final static GenericSQL DIALECT = new GenericSQL();

	@Test
	public void notExistedFunction() {
		Formula f = new Formula("  ranl(\"name\")");
	}

	@Test
	public void unsupportedOperation() {
		try {
			Formula f = new Formula("rank(\"name\")");
			f.sql(DIALECT);
			fail();
		} catch (UnsupportedFunctionException e) {
			assertEquals("Rank", e.source);
			assertEquals("GenericSQL", e.dialect);
			assertEquals(0, e.index());
			assertEquals(12, e.length());
		}
	}
}

package frmw.parser;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class ParsingTest {

	@Test
	public void avg() {
		Formula f = new Formula("avg(\"name\")", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(name)", sql);
	}

	@Test
	public void avgWithWhitespaces() {
		Formula f = new Formula(" \t avg ( \" name \n\" ) \n\r", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(name)", sql);
	}
}

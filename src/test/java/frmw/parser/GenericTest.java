package frmw.parser;

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
}

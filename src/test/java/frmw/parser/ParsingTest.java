package frmw.parser;

import frmw.dialect.GenericSQL;
import frmw.model.Formula;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class ParsingTest {

	private final static GenericSQL DIALECT = new GenericSQL();

	@Test
	public void avg() {
		Formula f = new Formula("avg(\"name\")");
		String sql = f.sql(DIALECT);
		assertEquals("avg(name)", sql);
	}

	@Test
	public void avgWithWhitespaces() {
		Formula f = new Formula(" \t avg ( \" name \n\" ) \n\r");
		String sql = f.sql(DIALECT);
		assertEquals("avg(name)", sql);
	}
}

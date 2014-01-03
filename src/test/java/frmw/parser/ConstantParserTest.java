package frmw.parser;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class ConstantParserTest {

	@Test
	public void string() {
		Formula f = new Formula("'literal'", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("'literal'", sql);
	}

	@Test
	public void stringAsFunctionArgument() {
		Formula f = new Formula("trim('123')", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim('123')", sql);
	}

	@Test
	public void stringWithEscapedSingleQuote() {
		Formula f = new Formula("'lit''eral'", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("'lit''eral'", sql);
	}

	@Test
	public void stringWithEscapedSingleQuoteAsFunctionArgument() {
		Formula f = new Formula("trim('lit''eral')", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim('lit''eral')", sql);
	}
}

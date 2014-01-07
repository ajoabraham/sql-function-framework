package frmw.parser;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static frmw.TestSupport.TERADATA_SQL;
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

	@Test
	public void number() {
		Formula f = new Formula("120", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("120", sql);
	}

	@Test
	public void numberNegative() {
		Formula f = new Formula("-120", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("-120", sql);
	}

	@Test
	public void decimal() {
		Formula f = new Formula("253.", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("253.", sql);
	}

	@Test
	public void decimal2() {
		Formula f = new Formula("3.14159", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("3.14159", sql);
	}

	@Test
	public void bigDecimal() {
		Formula f = new Formula("334_343_273_585_840_594.141_598_905_437_584_738_957_439_899", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("334343273585840594.141598905437584738957439899", sql);
	}

	@Test
	public void bigDecimalInFunction() {
		Formula f = new Formula("ln(abs(334_343_273_585_840_594.141_598_905_437_584_738_957_439_899))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(abs(334343273585840594.141598905437584738957439899))", sql);
	}

	@Test
	public void fraction() {
		Formula f = new Formula(".120", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals(".120", sql);
	}

	@Test
	public void fractionInFunction() {
		Formula f = new Formula("ln(abs(.120))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(abs(.120))", sql);
	}

	@Test
	public void fractionWithExp() {
		Formula f = new Formula(".120e12", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals(".120e12", sql);
	}

	@Test
	public void bigNumberWithUnderscores() {
		Formula f = new Formula("100_000_000", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("100000000", sql);
	}

	@Test
	public void exp1() {
		Formula f = new Formula("1E100", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("1E100", sql);
	}

	@Test
	public void exp2() {
		Formula f = new Formula("3.14E-10", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("3.14E-10", sql);
	}

	@Test
	public void exp3() {
		Formula f = new Formula("6.023E23", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("6.023E23", sql);
	}

	@Test
	public void exponentWithUnderscore() {
		Formula f = new Formula("6.023E231_112", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("6.023E231112", sql);
	}

	@Test
	public void exponentWithUnderscoreInFunction() {
		Formula f = new Formula("ln(abs(6.023E231_112))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(abs(6.023E231112))", sql);
	}
}

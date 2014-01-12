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
		Formula f = PARSER.parse("'literal'");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("'literal'", sql);
	}

	@Test
	public void stringAsFunctionArgument() {
		Formula f = PARSER.parse("trim('123')");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim('123')", sql);
	}

	@Test
	public void stringWithEscapedSingleQuote() {
		Formula f = PARSER.parse("'lit''eral'");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("'lit''eral'", sql);
	}

	@Test
	public void stringWithEscapedSingleQuoteAsFunctionArgument() {
		Formula f = PARSER.parse("trim('lit''eral')");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim('lit''eral')", sql);
	}

	@Test
	public void number() {
		Formula f = PARSER.parse("120");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("120", sql);
	}

	@Test
	public void numberNegative() {
		Formula f = PARSER.parse("-120");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("-120", sql);
	}

	@Test
	public void decimal() {
		Formula f = PARSER.parse("253.");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("253.", sql);
	}

	@Test
	public void decimal2() {
		Formula f = PARSER.parse("3.14159");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("3.14159", sql);
	}

	@Test
	public void bigDecimal() {
		Formula f = PARSER.parse("334_343_273_585_840_594.141_598_905_437_584_738_957_439_899");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("334343273585840594.141598905437584738957439899", sql);
	}

	@Test
	public void bigDecimalInFunction() {
		Formula f = PARSER.parse("ln(abs(334_343_273_585_840_594.141_598_905_437_584_738_957_439_899))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(abs(334343273585840594.141598905437584738957439899))", sql);
	}

	@Test
	public void fraction() {
		Formula f = PARSER.parse(".120");
		String sql = f.sql(GENERIC_SQL);
		assertEquals(".120", sql);
	}

	@Test
	public void fractionInFunction() {
		Formula f = PARSER.parse("ln(abs(.120))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(abs(.120))", sql);
	}

	@Test
	public void fractionWithExp() {
		Formula f = PARSER.parse(".120e12");
		String sql = f.sql(GENERIC_SQL);
		assertEquals(".120e12", sql);
	}

	@Test
	public void bigNumberWithUnderscores() {
		Formula f = PARSER.parse("100_000_000");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("100000000", sql);
	}

	@Test
	public void exp1() {
		Formula f = PARSER.parse("1E100");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("1E100", sql);
	}

	@Test
	public void exp2() {
		Formula f = PARSER.parse("3.14E-10");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("3.14E-10", sql);
	}

	@Test
	public void exp3() {
		Formula f = PARSER.parse("6.023E23");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("6.023E23", sql);
	}

	@Test
	public void exponentWithUnderscore() {
		Formula f = PARSER.parse("6.023E231_112");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("6.023E231112", sql);
	}

	@Test
	public void exponentWithUnderscoreInFunction() {
		Formula f = PARSER.parse("ln(abs(6.023E231_112))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(abs(6.023E231112))", sql);
	}
}

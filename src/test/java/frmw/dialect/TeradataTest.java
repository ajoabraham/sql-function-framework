package frmw.dialect;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.PARSER;
import static frmw.TestSupport.TERADATA_SQL;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class TeradataTest {

	@Test
	public void abs() {
		Formula f = new Formula("abs(\"name\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(name)", sql);
	}

	@Test
	public void abs_withoutQuotes() {
		Formula f = new Formula("abs(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(name)", sql);
	}

	@Test
	public void mixScalarWithAggregation() {
		Formula f = new Formula("abs(sum(\"name\"))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(sum(name))", sql);
	}

	@Test
	public void mixScalarWithScalar() {
		Formula f = new Formula("abs(abs(\"name\"))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(abs(name))", sql);
	}

	@Test
	public void mixScalarWithAggregation_withoutQuotes() {
		Formula f = new Formula("abs(sum(name))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(sum(name))", sql);
	}

	@Test
	public void exp() {
		Formula f = new Formula("exp(\"name\")", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("exp(name)", sql);
	}

	@Test
	public void ln() {
		Formula f = new Formula("ln(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("ln(name)", sql);
	}

	@Test
	public void log() {
		Formula f = new Formula("log(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("log(name)", sql);
	}

	@Test
	public void mod() {
		Formula f = new Formula("mod(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("mod(name)", sql);
	}

	@Test
	public void pow() {
		Formula f = new Formula("pow(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("pow(name)", sql);
	}

	@Test
	public void round() {
		Formula f = new Formula("round(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("round(name)", sql);
	}

	@Test
	public void sqrt() {
		Formula f = new Formula("sqrt(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sqrt(name)", sql);
	}
}

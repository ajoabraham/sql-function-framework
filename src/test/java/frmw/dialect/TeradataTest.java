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

	@Test
	public void stdDevS() {
		Formula f = new Formula("stdDevS(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("stddev_samp(name)", sql);
	}

	@Test
	public void stdDevP() {
		Formula f = new Formula("stdDevP(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("stddev_pop(name)", sql);
	}

	@Test
	public void aCos() {
		Formula f = new Formula("acos(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aCos(name)", sql);
	}

	@Test
	public void aCosH() {
		Formula f = new Formula("acosh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aCosH(name)", sql);
	}

	@Test
	public void aSin() {
		Formula f = new Formula("asin(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aSin(name)", sql);
	}

	@Test
	public void aSinH() {
		Formula f = new Formula("asinh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aSinH(name)", sql);
	}

	@Test
	public void aTan() {
		Formula f = new Formula("atan(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTan(name)", sql);
	}

	@Test
	public void aTanH() {
		Formula f = new Formula("atanh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("aTanH(name)", sql);
	}

	@Test
	public void cos() {
		Formula f = new Formula("cos(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("cos(name)", sql);
	}

	@Test
	public void cosH() {
		Formula f = new Formula("cosh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("cosH(name)", sql);
	}

	@Test
	public void sin() {
		Formula f = new Formula("sin(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sin(name)", sql);
	}

	@Test
	public void sinH() {
		Formula f = new Formula("sinh(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("sinH(name)", sql);
	}

	@Test
	public void tan() {
		Formula f = new Formula("tan(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("tan(name)", sql);
	}

	@Test
	public void tanH() {
		Formula f = new Formula("tanH(name)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("tanH(name)", sql);
	}
}

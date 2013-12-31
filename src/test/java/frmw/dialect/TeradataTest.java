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
}

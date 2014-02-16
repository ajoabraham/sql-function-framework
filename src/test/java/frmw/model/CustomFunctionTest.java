package frmw.model;

import org.junit.Test;

import static frmw.TestSupport.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class CustomFunctionTest {

	@Test
	public void noArgs() {
		Formula f = PARSER.parse("custom('trim(leading ''x'' from col1)')");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(leading 'x' from col1)", sql);
	}

	@Test
	public void escaped$() {
		Formula f = PARSER.parse("custom('trim(leading ''x'' from co$$l1)')");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(leading 'x' from co$l1)", sql);
	}

	@Test
	public void oneArg() {
		Formula f = PARSER.parse("custom('trim(leading ''x'' from col1) || $0', rightTrim(col2))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("trim(leading 'x' from col1) || trim(Trailing ' ' From \"col2\")", sql);
	}

	@Test
	public void oneArgTwice() {
		Formula f = PARSER.parse("custom('trim(leading ''x'' from $0) || $0', rightTrim(col2))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("trim(leading 'x' from trim(Trailing ' ' From \"col2\")) || trim(Trailing ' ' From \"col2\")", sql);
	}

	@Test
	public void inOLAP() {
		Formula f = PARSER.parse("customWindow(custom('$0', min(col1)), 100_000, all)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(\"col1\") OVER ( ROWS BETWEEN 100000 PRECEDING AND UNBOUNDED FOLLOWING)", sql);
	}

	@Test
	public void inOLAP_setDistinct() {
		Formula f = PARSER.parse("customWindow(custom('$0', min(col1)), 100_000, all)");
		f.windowParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(DISTINCT \"col1\") OVER ( ROWS BETWEEN 100000 PRECEDING AND UNBOUNDED FOLLOWING)", sql);
	}

	@Test
	public void inOLAP_noDistinct() {
		Formula f = PARSER.parse("customWindow(custom('min(col1)'), 100_000, all)");
		f.windowParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(col1) OVER ( ROWS BETWEEN 100000 PRECEDING AND UNBOUNDED FOLLOWING)", sql);
	}
}

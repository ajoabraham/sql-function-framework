package frmw.parser;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class CommonRulesTest {

	@Test
	public void functionsWithoutParenthesisShouldMapToColumnName() {
		Formula f = PARSER.parse("currentDate");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("\"currentDate\"", sql);
	}

	@Test
	public void lotsOfWhitespacesInFunction() {
		Formula f = PARSER.parse("  \n \t min \n\r \t ( \n\t col1 \t  \r\n ) \t \t \n \r");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(\"col1\")", sql);
	}
}

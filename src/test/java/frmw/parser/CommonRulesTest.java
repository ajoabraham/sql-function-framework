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
		Formula f = new Formula("currentDate", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("currentDate", sql);
	}
}

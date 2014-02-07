package frmw.model;

import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class JoinClauseTest {

	@Test
	public void aggregationParameters() {
		Join j = PARSER.parseJoin("trim(col1) = col2 and col3 = col4");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((trim(col1) = col2) AND (col3 = col4))", sql);
	}
}

package frmw.model;

import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class JoinTest {

	@Test
	public void and() {
		Join j = PARSER.parseJoin("trim(T1.col1) = T2.col2 and T1.col3 = T2.col4");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((trim(\"T1\".col1) = \"T2\".col2) AND (\"T1\".col3 = \"T2\".col4))", sql);
	}

	@Test
	public void or() {
		Join j = PARSER.parseJoin("trim(T1.col1) = T2.col2 or T1.col3 = T2.col4");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((trim(\"T1\".col1) = \"T2\".col2) OR (\"T1\".col3 = \"T2\".col4))", sql);
	}

	@Test
	public void between() {
		Join j = PARSER.parseJoin("trim(T1.col1) between T2.col2 and T2.col4");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("trim(\"T1\".col1) BETWEEN \"T2\".col2 AND \"T2\".col4", sql);
	}

	@Test
	public void in() {
		Join j = PARSER.parseJoin("trim(T1.col1) in (T2.col2 , T2.col4)");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("trim(\"T1\".col1) IN (\"T2\".col2, \"T2\".col4)", sql);
	}

	@Test
	public void quotedColumnName() {
		Join j = PARSER.parseJoin("trim(T1.\"col1\") = T2.\"col2\" and T1.\"col3\" = T2.col4");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((trim(\"T1\".\"col1\") = \"T2\".\"col2\") AND (\"T1\".\"col3\" = \"T2\".col4))", sql);
	}

	@Test
	public void whitespacesNearDot() {
		Join j = PARSER.parseJoin("trim(T1 \t .  col1) = T2.col2 ");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("(trim(\"T1\".col1) = \"T2\".col2)", sql);
	}

	@Test
	public void caseSupport() {
		Join j = PARSER.parseJoin("trim(T1.col1) = case T2.col2 when 2 then T2.col3 else T2.col4 end");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("(trim(\"T1\".col1) = CASE \"T2\".col2 WHEN 2 THEN \"T2\".col3 ELSE \"T2\".col4 END)", sql);
	}
}

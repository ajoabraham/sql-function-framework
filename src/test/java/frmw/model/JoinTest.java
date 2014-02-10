package frmw.model;

import frmw.model.exception.InvalidTableAlias;
import frmw.model.exception.UnexpectedTablesAmountInJoin;
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
	public void betweenDateRange() {
		Join j = PARSER.parseJoin("trim(T1.col1) between T2.col2-30 and currentDate()");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("trim(\"T1\".col1) BETWEEN \"T2\".col2-30 AND current_date", sql);
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

	@Test
	public void updateAliases() {
		Join j = PARSER.parseJoin("T1.col1 = T2.col3 and T1.col_b = T2.col_b");
		j.changeTableAliases("tLeft", "tRight");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((\"tLeft\".col1 = \"tRight\".col3) AND (\"tLeft\".col_b = \"tRight\".col_b))", sql);
	}

	@Test
	public void updateAliases_oneElementSwapped() {
		Join j = PARSER.parseJoin("T1.col1 = T2.col3 and T2.col_b = T1.col_b");
		j.changeTableAliases("tLeft", "tRight");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((\"tLeft\".col1 = \"tRight\".col3) AND (\"tRight\".col_b = \"tLeft\".col_b))", sql);
	}

	@Test
	public void updateAliases_allElementsSwapped() {
		Join j = PARSER.parseJoin("T2.col3 = T1.col1 and T2.col_b = T1.col_b");
		j.changeTableAliases("tLeft", "tRight");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((\"tRight\".col3 = \"tLeft\".col1) AND (\"tRight\".col_b = \"tLeft\".col_b))", sql);
	}

	@Test
	public void updateAliases_inAlphanumericOrder() {
		Join j = PARSER.parseJoin("T10.col3 = T9.col1 and T10.col_b = T9.col_b");
		j.changeTableAliases("tLeft", "tRight");
		String sql = j.sql(GENERIC_SQL);
		assertEquals("((\"tRight\".col3 = \"tLeft\".col1) AND (\"tRight\".col_b = \"tLeft\".col_b))", sql);
	}

	@Test(expected = UnexpectedTablesAmountInJoin.class)
	public void updateAliases_3AliasesThrowError() {
		Join j = PARSER.parseJoin("T1.col1 = T2.col3 and T1.col_b = T3.col_b");
		j.validate(GENERIC_SQL);
	}

	@Test(expected = UnexpectedTablesAmountInJoin.class)
	public void updateAliases_1AliasesThrowError() {
		Join j = PARSER.parseJoin("T1.col1 = T1.col3");
		j.validate(GENERIC_SQL);
	}

	@Test
	public void validationPassed() {
		Join j = PARSER.parseJoin("T1.col1 = t1000.col3");
		j.validate(GENERIC_SQL);
	}

	@Test(expected = InvalidTableAlias.class)
	public void aliasHasWrongLetter() {
		Join j = PARSER.parseJoin("N1.col1 = N2.col3");
		j.validate(GENERIC_SQL);
	}

	@Test(expected = InvalidTableAlias.class)
	public void aliasHasWrongLetter2() {
		Join j = PARSER.parseJoin("Tt1.col1 = TT2.col3");
		j.validate(GENERIC_SQL);
	}
}

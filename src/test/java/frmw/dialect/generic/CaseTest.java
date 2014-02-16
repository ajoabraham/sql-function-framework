package frmw.dialect.generic;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Alexey Paramonov
 */
public class CaseTest {

	@Test
	public void simpleCase() {
		Formula f = PARSER.parse("case col1 when 1 then 2 when 2 then 3 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE \"col1\" WHEN 1 THEN 2 WHEN 2 THEN 3 ELSE 5 END", sql);
	}

	@Test
	public void simpleCaseNested() {
		Formula f = PARSER.parse("case col1 when case col2 when 9 then 9 when 9 then 8 end then 2 when 2 then 3 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE \"col1\" WHEN CASE \"col2\" WHEN 9 THEN 9 WHEN 9 THEN 8 END THEN 2 WHEN 2 THEN 3 ELSE 5 END", sql);
	}

	@Test
	public void simpleCaseInFunction() {
		Formula f = PARSER.parse("min(abs(case col1 when 1 then 2 when 2 then 3 else 5 end))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("min(abs(CASE \"col1\" WHEN 1 THEN 2 WHEN 2 THEN 3 ELSE 5 END))", sql);
	}

	@Test
	public void simpleCaseOneWhen() {
		Formula f = PARSER.parse("case col1 when 1 then 2 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE \"col1\" WHEN 1 THEN 2 END", sql);
	}

	@Test
	public void simpleCaseWithoutElse() {
		Formula f = PARSER.parse("case col1 when 1 then 2 when 2 then 3 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE \"col1\" WHEN 1 THEN 2 WHEN 2 THEN 3 END", sql);
	}

	@Test
	public void searchedCaseEQ() {
		Formula f = PARSER.parse("case when col1=1 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" = 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseNE() {
		Formula f = PARSER.parse("case when col1<>1 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" <> 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseGT() {
		Formula f = PARSER.parse("case when col1>1 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" > 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseGE() {
		Formula f = PARSER.parse("case when col1>=1 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" >= 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseLT() {
		Formula f = PARSER.parse("case when col1<1 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" < 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseWithFunction() {
		Formula f = PARSER.parse("case when col1 < Trim(col2) then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" < trim(\"col2\")) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseLE() {
		Formula f = PARSER.parse("case when col1<=1 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" <= 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseIsNull() {
		Formula f = PARSER.parse("case when col1 is null then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN \"col1\" IS NULL THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseIsNotNull() {
		Formula f = PARSER.parse("case when col1 is not null then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN \"col1\" IS NOT NULL THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseNestedWhen() {
		Formula f = PARSER.parse("case when col1 = (case when col2 = 1 then 2 else 5 end) then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" = CASE WHEN (\"col2\" = 1) THEN 2 ELSE 5 END) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseNestedThen() {
		Formula f = PARSER.parse("case when col1 = 1 then (case when col2 = 1 then 2 else 5 end) else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" = 1) THEN CASE WHEN (\"col2\" = 1) THEN 2 ELSE 5 END ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseInFunction() {
		Formula f = PARSER.parse("min(abs(case when col1 = 1 then 2 else 5 end))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("min(abs(CASE WHEN (\"col1\" = 1) THEN 2 ELSE 5 END))", sql);
	}

	@Test
	public void searchedCaseWithoutElse() {
		Formula f = PARSER.parse("case when col1 = 1 then 2 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" = 1) THEN 2 END", sql);
	}

	@Test
	public void searchedCaseLotsMany() {
		Formula f = PARSER.parse("case when col1 = 1 then 2 when col1 = 2 then 3 when col1 = 3 then 4 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" = 1) THEN 2 WHEN (\"col1\" = 2) THEN 3 WHEN (\"col1\" = 3) THEN 4 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseOr() {
		Formula f = PARSER.parse("case when col1=1 or col1=2 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN ((\"col1\" = 1) OR (\"col1\" = 2)) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseAnd() {
		Formula f = PARSER.parse("case when col1=1 and col1=2 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN ((\"col1\" = 1) AND (\"col1\" = 2)) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseAndHasMorePriority() {
		Formula f = PARSER.parse("case when col1=1 or col1=3 and col1=2 or col1=4 then 2 else 5 end");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN ((\"col1\" = 1) OR (((\"col1\" = 3) AND (\"col1\" = 2)) OR (\"col1\" = 4))) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseComplexColumnList() {
		Formula f = PARSER.parse("case when (col1/(col_longer_name3+col_9/col10))=2 then col2 else col5 end");
		assertThat(f.entityNames(), containsInAnyOrder("col1", "col_longer_name3", "col2", "col5", "col_9", "col10"));
		System.out.println(f.sql(GENERIC_SQL));
		assertEquals("CASE WHEN ((\"col1\" / (\"col_longer_name3\" + (\"col_9\" / \"col10\"))) = 2) THEN \"col2\" ELSE \"col5\" END", f.sql(GENERIC_SQL));
	}

	@Test
	public void between() {
		Formula f = PARSER.parse("case when col1 between 1 and 2 then 2 when col2 between 6 and 7 or col3 between col4 and Trim(col5) then 3 end");
		assertThat(f.entityNames(), containsInAnyOrder("col1", "col2", "col3", "col4", "col5"));
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN \"col1\" BETWEEN 1 AND 2 THEN 2 WHEN (\"col2\" BETWEEN 6 AND 7 OR \"col3\" BETWEEN \"col4\" AND trim(\"col5\")) THEN 3 END", sql);
	}

	@Test
	public void in() {
		Formula f = PARSER.parse("case when col1 in (col, \"col2\", 100_500, Avg(col3), Trim(col4)) and col2 in (12, 14) then 2 end");
		assertThat(f.entityNames(), containsInAnyOrder("col1", "col", "col2", "col3", "col4"));
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (\"col1\" IN (\"col\", \"col2\", 100500, avg(\"col3\"), trim(\"col4\")) AND \"col2\" IN (12, 14)) THEN 2 END", sql);
	}
}

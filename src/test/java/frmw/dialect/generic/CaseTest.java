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
		Formula f = new Formula("case col1 when 1 then 2 when 2 then 3 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE col1 WHEN 1 THEN 2 WHEN 2 THEN 3 ELSE 5 END", sql);
	}

	@Test
	public void simpleCaseNested() {
		Formula f = new Formula("case col1 when case col2 when 9 then 9 when 9 then 8 end then 2 when 2 then 3 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE col1 WHEN CASE col2 WHEN 9 THEN 9 WHEN 9 THEN 8 END THEN 2 WHEN 2 THEN 3 ELSE 5 END", sql);
	}

	@Test
	public void simpleCaseInFunction() {
		Formula f = new Formula("min(abs(case col1 when 1 then 2 when 2 then 3 else 5 end))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("min(abs(CASE col1 WHEN 1 THEN 2 WHEN 2 THEN 3 ELSE 5 END))", sql);
	}

	@Test
	public void simpleCaseOneWhen() {
		Formula f = new Formula("case col1 when 1 then 2 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE col1 WHEN 1 THEN 2 END", sql);
	}

	@Test
	public void simpleCaseWithoutElse() {
		Formula f = new Formula("case col1 when 1 then 2 when 2 then 3 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE col1 WHEN 1 THEN 2 WHEN 2 THEN 3 END", sql);
	}

	@Test
	public void searchedCaseEQ() {
		Formula f = new Formula("case when col1=1 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseNE() {
		Formula f = new Formula("case when col1!=1 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 != 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseGT() {
		Formula f = new Formula("case when col1>1 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 > 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseGE() {
		Formula f = new Formula("case when col1>=1 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 >= 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseLT() {
		Formula f = new Formula("case when col1<1 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 < 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseLE() {
		Formula f = new Formula("case when col1<=1 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 <= 1) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseIsNull() {
		Formula f = new Formula("case when col1 is null then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN col1 IS NULL THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseIsNotNull() {
		Formula f = new Formula("case when col1 is not null then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN col1 IS NOT NULL THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseNestedWhen() {
		Formula f = new Formula("case when col1 = (case when col2 = 1 then 2 else 5 end) then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = CASE WHEN (col2 = 1) THEN 2 ELSE 5 END) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseNestedThen() {
		Formula f = new Formula("case when col1 = 1 then (case when col2 = 1 then 2 else 5 end) else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN CASE WHEN (col2 = 1) THEN 2 ELSE 5 END ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseInFunction() {
		Formula f = new Formula("min(abs(case when col1 = 1 then 2 else 5 end))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("min(abs(CASE WHEN (col1 = 1) THEN 2 ELSE 5 END))", sql);
	}

	@Test
	public void searchedCaseWithoutElse() {
		Formula f = new Formula("case when col1 = 1 then 2 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN 2 END", sql);
	}

	@Test
	public void searchedCaseLotsMany() {
		Formula f = new Formula("case when col1 = 1 then 2 when col1 = 2 then 3 when col1 = 3 then 4 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN 2 WHEN (col1 = 2) THEN 3 WHEN (col1 = 3) THEN 4 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseOr() {
		Formula f = new Formula("case when col1=1 or col1=2 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN 2 WHEN (col1 = 2) THEN 2 ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseAnd() {
		Formula f = new Formula("case when col1=1 and col1=2 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN CASE WHEN (col1 = 2) THEN 2 ELSE 5 END ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseAndHasMorePriority() {
		Formula f = new Formula("case when col1=1 or col1=3 and col1=2 or col1=4 then 2 else 5 end", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CASE WHEN (col1 = 1) THEN CASE WHEN (col1 = 2) THEN 2 WHEN (col1 = 4) THEN 2 ELSE 5 END WHEN (col1 = 3) THEN CASE WHEN (col1 = 2) THEN 2 WHEN (col1 = 4) THEN 2 ELSE 5 END ELSE 5 END", sql);
	}

	@Test
	public void searchedCaseComplexColumnList() {
		Formula f = new Formula("case when (col1/(col_longer_name3+col_9/col10))=2 then col2 else col5 end", PARSER);
		assertThat(f.entityNames(), containsInAnyOrder("col1", "col_longer_name3", "col2", "col5", "col_9", "col10"));
		System.out.println(f.sql(GENERIC_SQL));
		assertEquals("CASE WHEN ((col1 / (col_longer_name3 + (col_9 / col10))) = 2) THEN col2 ELSE col5 END", f.sql(GENERIC_SQL));
	}
}

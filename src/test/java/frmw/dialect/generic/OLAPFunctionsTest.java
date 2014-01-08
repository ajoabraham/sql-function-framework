package frmw.dialect.generic;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static frmw.model.fun.olap.support.Order.ASC;
import static frmw.model.fun.olap.support.Order.DESC;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class OLAPFunctionsTest {

	@Test
	public void runningAvg() {
		Formula f = new Formula("runningAvg(col1)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void runningCount() {
		Formula f = new Formula("runningCount(col1)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void runningSum() {
		Formula f = new Formula("runningSum(col1)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("sum(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void movingAvg() {
		Formula f = new Formula("movingAvg(col1, 100_000)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( ROWS 100000 PRECEDING)", sql);
	}

	@Test
	public void movingCount() {
		Formula f = new Formula("movingCount(col1, 100000)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(col1) OVER ( ROWS 100000 PRECEDING)", sql);
	}

	@Test
	public void movingSum() {
		Formula f = new Formula("movingSum(col1, 100_000)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("sum(col1) OVER ( ROWS 100000 PRECEDING)", sql);
	}

	@Test
	public void customWindowToAll() {
		Formula f = new Formula("customWindow(min(col1), 100_000, all)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(col1) OVER ( ROWS BETWEEN 100000 PRECEDING AND UNBOUNDED FOLLOWING)", sql);
	}

	@Test
	public void customWindowFromAll() {
		Formula f = new Formula("customWindow(max(col1), all, 100_000)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("max(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND 100000 FOLLOWING)", sql);
	}

	@Test
	public void customWindowFromCurrentRow() {
		Formula f = new Formula("customWindow(count(col1), current  row, 100_000)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(col1) OVER ( ROWS BETWEEN CURRENT ROW AND 100000 FOLLOWING)", sql);
	}

	@Test
	public void customWindowToCurrentRow() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowDistinct() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(DISTINCT col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderBy() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).group("col2", DESC);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( GROUP BY col2 DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderBySeveral() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).group("col2", DESC).group("col3", ASC).group("col4", DESC);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( GROUP BY col2 DESC, col3 ASC, col4 DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowPartitionBy() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).partition("col2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col2 ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowPartitionBySeveral() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).partition("col2").partition("col3").partition("col4");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col2, col3, col4 ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderByWithPartitionBy() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).group("col2", DESC).partition("col3");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col3 GROUP BY col2 DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderByReset() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).group("col2", DESC).group("col3", ASC).group("col2", ASC);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( GROUP BY col3 ASC, col2 ASC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowPartitionByReset() {
		Formula f = new Formula("customWindow(avg(col1), all, current  row)", PARSER);
		f.windowParameters().get(0).partition("col2").partition("col3").partition("col2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col3, col2 ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}
}

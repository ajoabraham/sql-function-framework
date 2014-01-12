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
		Formula f = PARSER.parse("runningAvg(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void runningCount() {
		Formula f = PARSER.parse("runningCount(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void runningSum() {
		Formula f = PARSER.parse("runningSum(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("sum(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void movingAvg() {
		Formula f = PARSER.parse("movingAvg(col1, 100_000)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( ROWS 100000 PRECEDING)", sql);
	}

	@Test
	public void movingCount() {
		Formula f = PARSER.parse("movingCount(col1, 100000)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(col1) OVER ( ROWS 100000 PRECEDING)", sql);
	}

	@Test
	public void movingSum() {
		Formula f = PARSER.parse("movingSum(col1, 100_000)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("sum(col1) OVER ( ROWS 100000 PRECEDING)", sql);
	}

	@Test
	public void customWindowToAll() {
		Formula f = PARSER.parse("customWindow(min(col1), 100_000, all)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("min(col1) OVER ( ROWS BETWEEN 100000 PRECEDING AND UNBOUNDED FOLLOWING)", sql);
	}

	@Test
	public void customWindowFromAll() {
		Formula f = PARSER.parse("customWindow(max(col1), all, 100_000)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("max(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND 100000 FOLLOWING)", sql);
	}

	@Test
	public void customWindowFromCurrentRow() {
		Formula f = PARSER.parse("customWindow(count(col1), current  row, 100_000)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("count(col1) OVER ( ROWS BETWEEN CURRENT ROW AND 100000 FOLLOWING)", sql);
	}

	@Test
	public void customWindowToCurrentRow() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowDistinct() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(DISTINCT col1) OVER ( ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderBy() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).group("col2", DESC);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( GROUP BY col2 DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderBySeveral() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).group("col2", DESC).group("col3", ASC).group("col4", DESC);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( GROUP BY col2 DESC, col3 ASC, col4 DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowPartitionBy() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).partition("col2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col2 ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowPartitionBySeveral() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).partition("col2").partition("col3").partition("col4");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col2, col3, col4 ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderByWithPartitionBy() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).group("col2", DESC).partition("col3");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col3 GROUP BY col2 DESC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowOrderByReset() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).group("col2", DESC).group("col3", ASC).group("col2", ASC);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( GROUP BY col3 ASC, col2 ASC ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void customWindowPartitionByReset() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		f.windowParameters().get(0).partition("col2").partition("col3").partition("col2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("avg(col1) OVER ( PARTITION BY col3, col2 ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", sql);
	}

	@Test
	public void rank() {
		Formula f = PARSER.parse("rank(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals(1, f.rankParameters().size());
		assertEquals(0, f.aggregationParameters().size());
		assertEquals(0, f.windowParameters().size());
		assertEquals("RANK() OVER (ORDER BY col1 ASC)", sql);
	}

	@Test
	public void rankWithAggregation() {
		Formula f = PARSER.parse("rank(min(col1))");
		String sql = f.sql(GENERIC_SQL);
		assertEquals(1, f.rankParameters().size());
		assertEquals(1, f.aggregationParameters().size());
		assertEquals(0, f.windowParameters().size());
		assertEquals("RANK() OVER (ORDER BY min(col1) ASC)", sql);
	}

	@Test
	public void rankWithParams() {
		Formula f = PARSER.parse("rank(min(col1))");
		f.aggregationParameters().get(0).distinct(true);
		f.rankParameters().get(0).order(DESC).partition("col2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("RANK() OVER (PARTITION BY col2 ORDER BY min(DISTINCT col1) DESC)", sql);
	}

	@Test
	public void rankWithParamsSeveralPartitionBy() {
		Formula f = PARSER.parse("rank(col1)");
		f.rankParameters().get(0).order(DESC).partition("col2").partition("col3").partition("col4");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("RANK() OVER (PARTITION BY col2, col3, col4 ORDER BY col1 DESC)", sql);
	}
}

package frmw.model;

import org.junit.Test;

import static frmw.TestSupport.PARSER;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;

/**
 * @author Alexey Paramonov
 */
public class FormulaTest {

	@Test
	public void hasAggregationSimple() {
		Formula f = PARSER.parse("avg(\"name\")");
		assertTrue(f.hasAggregation());
	}

	@Test
	public void hasNotAggregationSimple() {
		Formula f = PARSER.parse("abs(\"name\")");
		assertFalse(f.hasAggregation());
	}

	@Test
	public void hasAggregationCase() {
		Formula f = PARSER.parse("case when col1 = (case when sum(col2) = 1 then 2 else 5 end) then 2 else 5 end");
		assertTrue(f.hasAggregation());
	}

	@Test
	public void entityNamesCase() {
		Formula f = PARSER.parse("case when col1 = (case when sum(col2) = 1 then 2 when sum(col1) = 2 then 11 else 5 end) then 2 else 5 end");
		assertThat(f.entityNames(), contains("col1", "col2"));
	}

	@Test
	public void noEntityNames() {
		Formula f = PARSER.parse("avg(abs(12) + zeroifnull('123'))");
		assertThat(f.entityNames(), empty());
	}

	@Test
	public void customWindowParameters() {
		Formula f = PARSER.parse("customWindow(avg(col1), all, current  row)");
		assertEquals(0, f.aggregationParameters().size());
		assertEquals(1, f.windowParameters().size());
	}

	@Test
	public void aggregationParameters() {
		Formula f = PARSER.parse("avg(col1)");
		assertEquals(1, f.aggregationParameters().size());
		assertEquals(0, f.windowParameters().size());
	}
}

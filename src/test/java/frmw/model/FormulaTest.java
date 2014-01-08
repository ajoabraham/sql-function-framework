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
		Formula f = new Formula("avg(\"name\")", PARSER);
		assertTrue(f.hasAggregation());
	}

	@Test
	public void hasNotAggregationSimple() {
		Formula f = new Formula("abs(\"name\")", PARSER);
		assertFalse(f.hasAggregation());
	}

	@Test
	public void hasAggregationCase() {
		Formula f = new Formula("case when col1 = (case when sum(col2) = 1 then 2 else 5 end) then 2 else 5 end", PARSER);
		assertTrue(f.hasAggregation());
	}

	@Test
	public void entityNamesCase() {
		Formula f = new Formula("case when col1 = (case when sum(col2) = 1 then 2 when sum(col1) = 2 then 11 else 5 end) then 2 else 5 end", PARSER);
		assertThat(f.entityNames(), contains("col1", "col2"));
	}

	@Test
	public void noEntityNames() {
		Formula f = new Formula("avg(abs(12) + zeroifnull('123'))", PARSER);
		assertThat(f.entityNames(), empty());
	}
}

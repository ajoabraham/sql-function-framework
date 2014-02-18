package frmw.dialect.generic;

import frmw.model.Formula;
import frmw.model.position.Position;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Alexey Paramonov
 */
public class GenericTest {

	@Test
	public void avg() {
		Formula f = PARSER.parse("avg(\"name\")");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("avg(\"name\")", sql);
	}

	@Test
	public void avg_distinct() {
		Formula f = PARSER.parse("avg(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("avg(DISTINCT \"name\")", sql);
	}

	@Test
	public void avg_caseInsensitivity() {
		Formula f = PARSER.parse("AvG(\"name\")");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("avg(\"name\")", sql);
	}

	@Test
	public void min() {
		Formula f = PARSER.parse("min(\"name\")");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("min(\"name\")", sql);
	}

	@Test
	public void min_distinct() {
		Formula f = PARSER.parse("min(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("min(DISTINCT \"name\")", sql);
	}

	@Test
	public void max() {
		Formula f = PARSER.parse("max(\"name\")");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("max(\"name\")", sql);
	}

	@Test
	public void max_distinct() {
		Formula f = PARSER.parse("max(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 11), f.aggregationParameters().get(0).position());
		assertEquals("max(DISTINCT \"name\")", sql);
	}

	@Test
	public void count() {
		Formula f = PARSER.parse("count(\"name\")");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 13), f.aggregationParameters().get(0).position());
		assertEquals("count(\"name\")", sql);
	}

	@Test
	public void count_distinct() {
		Formula f = PARSER.parse("count(\"name\")");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(0, 13), f.aggregationParameters().get(0).position());
		assertEquals("count(DISTINCT \"name\")", sql);
	}

	@Test
	public void avgWithWhitespaces() {
		Formula f = PARSER.parse(" \t avg ( \" name \n\" ) \n\r");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(new Position(3, 17), f.aggregationParameters().get(0).position());
		assertEquals("avg(\"name\")", sql);
	}

	@Test
	public void trim() {
		Formula f = PARSER.parse("trim(\"name\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(\"name\")", sql);
	}

	@Test
	public void concat() {
		Formula f = PARSER.parse("\"col1\" || \"col2\"");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(\"col1\" || \"col2\")", sql);
	}

	@Test
	public void concatWithSpace() {
		Formula f = PARSER.parse("\"col1\" || ' ' || \"col2\"");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((\"col1\" || ' ') || \"col2\")", sql);
	}

	@Test
	public void concatWithPaddedWord() {
		Formula f = PARSER.parse("count(trim(\"col1\" || '  hello   ' || \"col2\"))");
		String sql = f.sql(GENERIC_SQL);

		assertThat(f.entityNames(), containsInAnyOrder("col1", "col2"));
		assertEquals("count(trim(((\"col1\" || '  hello   ') || \"col2\")))", sql);
	}

	@Test
	public void concatOperatorHasLowestPriorityThanArithmeticalOperators() {
		Formula f = PARSER.parse("col1 + col2 - col3 || col4 - col5");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(0, f.aggregationParameters().size());
		assertEquals(0, f.windowParameters().size());
		assertEquals(0, f.rankParameters().size());
		assertThat(f.entityNames(), containsInAnyOrder("col1", "col2", "col3", "col4", "col5"));
		assertEquals("(((\"col1\" + \"col2\") - \"col3\") || (\"col4\" - \"col5\"))", sql);
	}

	@Test
	public void currentDate() {
		Formula f = PARSER.parse("currentDate()");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(0, f.aggregationParameters().size());
		assertEquals(0, f.windowParameters().size());
		assertEquals(0, f.rankParameters().size());
		assertEquals("CURRENT_DATE", sql);
	}

	@Test
	public void currentTimestamp() {
		Formula f = PARSER.parse("currentTimestamp()");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("CURRENT_TIMESTAMP", sql);
	}

	@Test
	public void upper() {
		Formula f = PARSER.parse("upper(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("upper(\"col1\")", sql);
	}

	@Test
	public void lower() {
		Formula f = PARSER.parse("lower(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("lower(\"col1\")", sql);
	}

	@Test
	public void avgWithSpaceInColumnName() {
		Formula f = PARSER.parse("avg(\"column name\")");
		String sql = f.sql(GENERIC_SQL);

		assertEquals(1, f.aggregationParameters().size());
		assertEquals(0, f.windowParameters().size());
		assertEquals(0, f.rankParameters().size());
		assertEquals(new Position(0, 18), f.aggregationParameters().get(0).position());
		assertEquals("avg(\"column name\")", sql);
	}

	@Test
	public void sumWithArithmetic() {
		Formula f = PARSER.parse("sum(\"column name\"+2-4*col1/3)");

		assertEquals(new Position(0, 29), f.aggregationParameters().get(0).position());
		assertEquals("sum(((\"column name\" + 2) - ((4 * \"col1\") / 3)))", f.sql(GENERIC_SQL));
	}

	@Test
	public void stdDevS() {
		Formula f = PARSER.parse("stdDevS(name)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("stddev_samp(\"name\")", sql);
	}

	@Test
	public void stdDevS_distinct() {
		Formula f = PARSER.parse("stdDevS(name)");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("stddev_samp(DISTINCT \"name\")", sql);
	}

	@Test
	public void stdDevP() {
		Formula f = PARSER.parse("stdDevP(name)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("stddev_pop(\"name\")", sql);
	}

	@Test
	public void stdDevP_distinct() {
		Formula f = PARSER.parse("stdDevP(name)");
		f.aggregationParameters().get(0).distinct(true);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("stddev_pop(DISTINCT \"name\")", sql);
	}

	@Test
	public void leftTrim() {
		Formula f = PARSER.parse("leftTrim(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(Leading ' ' From \"col1\")", sql);
	}

	@Test
	public void leftTrimWithTrimmedSymbol() {
		Formula f = PARSER.parse("leftTrim(\"col1\", 'x')");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(Leading 'x' From \"col1\")", sql);
	}

	@Test
	public void rightTrim() {
		Formula f = PARSER.parse("rightTrim(\"col1\")");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(Trailing ' ' From \"col1\")", sql);
	}

	@Test
	public void rightTrimWithTrimmedSymbol() {
		Formula f = PARSER.parse("rightTrim(\"col1\", 'x')");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("trim(Trailing 'x' From \"col1\")", sql);
	}

	@Test
	public void year() {
		Formula f = PARSER.parse("year(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Year from \"col1\")", sql);
	}

	@Test
	public void month() {
		Formula f = PARSER.parse("month(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Month from \"col1\")", sql);
	}

	@Test
	public void day() {
		Formula f = PARSER.parse("day(currentDate())");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Day from CURRENT_DATE)", sql);
	}

	@Test
	public void week() {
		Formula f = PARSER.parse("week(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Week from \"col1\")", sql);
	}

	@Test
	public void hour() {
		Formula f = PARSER.parse("hour(currentTimestamp())");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Hour from CURRENT_TIMESTAMP)", sql);
	}

	@Test
	public void minute() {
		Formula f = PARSER.parse("minute(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Minute from \"col1\")", sql);
	}

	@Test
	public void second() {
		Formula f = PARSER.parse("second(col1)");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("extract(Second from \"col1\")", sql);
	}
}

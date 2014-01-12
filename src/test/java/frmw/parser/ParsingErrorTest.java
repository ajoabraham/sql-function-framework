package frmw.parser;

import frmw.model.Formula;
import frmw.model.exception.ParsingException;
import frmw.model.exception.UnsupportedFunctionException;
import frmw.model.exception.WrongFunctionNameException;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static frmw.parser.FunctionType.AGGREGATION;
import static frmw.parser.FunctionType.OLAP;
import static frmw.parser.FunctionType.SCALAR;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Alexey Paramonov
 */
public class ParsingErrorTest {

	@Test
	public void notExistedFunction() {
		try {
			Formula f = PARSER.parse("  ranl(\"name\")");
			fail(f.sql(GENERIC_SQL));
		} catch (WrongFunctionNameException e) {
			assertEquals("ranl", e.function);
			assertEquals(2, e.index());
			assertEquals(4, e.length());
			assertThat(e.expectedTypes, containsInAnyOrder(SCALAR, AGGREGATION, OLAP));
			assertThat(e.expectedFunctions, hasItems("Min", "Ln", "RunningAvg"));
			assertThat(e.closestFunctions, containsInAnyOrder("Rank"));
		}
	}

	@Test
	public void scalarFunctionsOnlyAppliedForAggregation() {
		try {
			Formula f = PARSER.parse("avg (min (\"name\"))");
			fail(f.sql(GENERIC_SQL));
		} catch (WrongFunctionNameException e) {
			assertEquals("min", e.function);
			assertEquals(5, e.index());
			assertEquals(3, e.length());
			assertThat(e.expectedTypes, contains(SCALAR));
			assertThat(e.expectedFunctions, hasItem("Ln"));
			assertThat(e.expectedFunctions, not(hasItems("Count", "RunningAvg")));
			assertThat(e.closestFunctions, containsInAnyOrder("Sin"));
		}
	}

	@Test
	public void customWindowWithNotAggregationFunction() {
		try {
			Formula f = PARSER.parse("customWindow(ln(col1), all, current  row)");
			fail(f.sql(GENERIC_SQL));
		} catch (WrongFunctionNameException e) {
			assertEquals("ln", e.function);
			assertEquals(13, e.index());
			assertEquals(2, e.length());
			assertThat(e.expectedTypes, contains(AGGREGATION));
			assertThat(e.expectedFunctions, hasItems("Count", "Min"));
			assertThat(e.expectedFunctions, not(hasItems("Abs", "Ln", "RunningAvg")));
		}
	}

	@Test
	public void plainColumnNameWithBlank() {
		try {
			Formula f = PARSER.parse("  column name  ");
			fail(f.sql(GENERIC_SQL));
		} catch (ParsingException e) {
			assertEquals(9, e.index());
			assertEquals(-1, e.length());
			assertThat(e.expected, hasItems("*", "+", "||"));
		}
	}

	@Test
	public void unsupportedOperation() {
		try {
			Formula f = PARSER.parse("abs(\"name\")");
			fail(f.sql(GENERIC_SQL));
		} catch (UnsupportedFunctionException e) {
			assertEquals("Abs", e.function);
			assertEquals("GenericSQL", e.dialect);
			assertEquals(0, e.index());
			assertEquals(11, e.length());
		}
	}

	@Test
	public void unsupportedOperationEnhanced() {
		try {
			Formula f = PARSER.parse("avg(ln(\"name\") + abs(col1))");
			fail(f.sql(GENERIC_SQL));
		} catch (UnsupportedFunctionException e) {
			assertEquals("Ln", e.function);
			assertEquals("GenericSQL", e.dialect);
			assertEquals(4, e.index());
			assertEquals(11, e.length());
		}
	}
}

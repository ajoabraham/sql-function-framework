package frmw.parser;

import frmw.model.Formula;
import frmw.model.exception.ParsingException;
import frmw.model.exception.UnsupportedFunctionException;
import frmw.model.exception.WrongFunctionNameException;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Alexey Paramonov
 */
public class ParsingErrorTest {

	@Test
	public void notExistedFunction() {
		try {
			Formula f = new Formula("  ranl(\"name\")", PARSER);
			fail(f.sql(GENERIC_SQL));
		} catch (WrongFunctionNameException e) {
			assertEquals("ranl", e.function);
			assertEquals(2, e.index());
			assertEquals(4, e.length());
			assertThat(e.expectedFunctions, hasItems("Min", "Ln"));
			assertThat(e.closestFunctions, containsInAnyOrder("Rank"));
		}
	}

	@Test
	public void scalarFunctionsOnlyAppliedForAggregation() {
		try {
			Formula f = new Formula("avg (min (\"name\"))", PARSER);
			fail(f.sql(GENERIC_SQL));
		} catch (WrongFunctionNameException e) {
			assertEquals("min", e.function);
			assertEquals(5, e.index());
			assertEquals(3, e.length());
			assertThat(e.expectedFunctions, hasItem("Ln"));
			assertThat(e.expectedFunctions, not(hasItem("Count")));
			assertThat(e.closestFunctions, containsInAnyOrder("Sin"));
		}
	}

	@Test
	public void plainColumnNameWithBlank() {
		try {
			Formula f = new Formula("  column name  ", PARSER);
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
			Formula f = new Formula("rank(\"name\")", PARSER);
			fail(f.sql(GENERIC_SQL));
		} catch (UnsupportedFunctionException e) {
			assertEquals("Rank", e.function);
			assertEquals("GenericSQL", e.dialect);
			assertEquals(0, e.index());
			assertEquals(12, e.length());
		}
	}

	@Test
	public void unsupportedOperationEnhanced() {
		try {
			Formula f = new Formula("avg(ln(\"name\") + abs(col1))", PARSER);
			fail(f.sql(GENERIC_SQL));
		} catch (UnsupportedFunctionException e) {
			assertEquals("Ln", e.function);
			assertEquals("GenericSQL", e.dialect);
			assertEquals(4, e.index());
			assertEquals(11, e.length());
		}
	}
}

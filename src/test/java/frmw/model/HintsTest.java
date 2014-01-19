package frmw.model;

import frmw.model.hint.ArgumentHint;
import frmw.parser.Hints;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import static frmw.TestSupport.PARSER;
import static frmw.TestSupport.names;
import static java.text.MessageFormat.format;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * @author Alexey Paramonov
 */
@SuppressWarnings("unchecked")
public class HintsTest {

	static Matcher<ArgumentHint> arg(final String name, final int index) {
		return new BaseMatcher<ArgumentHint>() {
			@Override
			public boolean matches(Object item) {
				ArgumentHint hint = (ArgumentHint) item;
				return hint.function.name.equalsIgnoreCase(name) && hint.argumentIndex == index;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(format("name={0}, index={1}", name, index));
			}
		};
	}

	@Test
	public void cursorInTheEnd() {
		Hints hints = new Hints("func", 4, PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void cursorDefault() {
		Hints hints = new Hints("func", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void cursorInStart() {
		Hints hints = new Hints("func", 0, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void cursorInMiddle() {
		Hints hints = new Hints("func", 2, PARSER);
		assertTrue(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cursorNegative() {
		new Hints("func", -1, PARSER);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cursorLongerThanFormula() {
		new Hints("func", 5, PARSER);
	}

	@Test
	public void doubleQuoteOpened() {
		Hints hints = new Hints("\"func", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void doubleQuoteOpened_middle() {
		Hints hints = new Hints("\"func", 2, PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void singleQuoteOpened() {
		Hints hints = new Hints("'func", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void singleQuoteOpened_middle() {
		Hints hints = new Hints("'func", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void onlyOpenedSingleQuote() {
		Hints hints = new Hints("'", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void onlyOpenedDoubleQuote() {
		Hints hints = new Hints("\"", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void doubleQuoteInSingleQuoted() {
		Hints hints = new Hints("'fu\"nc' + bar", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void doubleQuoteInSingleQuoted_middle() {
		Hints hints = new Hints("'fu\"nc' + bar", 11, PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void escapedQuotesInStringLiteral() {
		Hints hints = new Hints("'fu''nc' + bar", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void singleQuoteInDoubleQuoted() {
		Hints hints = new Hints("\"fu'nc\" + bar", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void cursorAfterWhitespace() {
		Hints hints = new Hints("func ", PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void cursorAfterWhitespace_middle() {
		Hints hints = new Hints("func func2 ", 5, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void startsWithMoving() {
		Hints hints = new Hints("moving", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount"));
	}

	@Test
	public void startsWithMo() {
		Hints hints = new Hints("moving", 2, PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount", "Mod", "Month"));
	}

	@Test
	public void plusBeforeName() {
		Hints hints = new Hints("col1 +yea", PARSER);
		assertThat(names(hints.functions()), containsInAnyOrder("Year"));
	}

	@Test
	public void whitespaceBeforeName() {
		Hints hints = new Hints("sum( ran", PARSER);
		assertThat(hints.arguments(), contains(arg("sum", 0)));
		assertThat(names(hints.functions()), containsInAnyOrder("Random"));
	}

	@Test
	public void noInputAfterSpecialSymbol() {
		Hints hints = new Hints("col1+col2", 5, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void userCannotInsertAggregationAndOLAPInAggregation() {
		Hints hints = new Hints("min(c", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("Cos", "CosH", "CurrentDate", "CurrentTimestamp"));
	}

	@Test
	public void userCanInsertOnlyAggregationInOLAP() {
		Hints hints = new Hints("customWindow(co", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), contains("Count"));
	}

	@Test
	public void mistakeInFunctionNameBeforeCursorPosition() {
		Hints hints = new Hints("customWindow(customWind(co", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("Cos", "CosH", "Count"));
	}

	@Test
	public void oneParameter() {
		Hints hints = new Hints("min(", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0)));
	}

	@Test
	public void severalParameters() {
		Hints hints = new Hints("min(avg(sin(", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0), arg("avg", 0), arg("sin", 0)));
	}

	@Test
	public void oneFunctionIsClosed() {
		Hints hints = new Hints("min(avg(sin() + 123", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0), arg("avg", 0)));
	}

	@Test
	public void lotsOfParenthesis() {
		Hints hints = new Hints("abs() + min((avg((sin() + 123) + (123 * (34)) + 14", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0), arg("avg", 0)));
	}

	@Test
	public void secondArg() {
		Hints hints = new Hints("random(12, col1", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 1)));

		ArgumentHint hint = hints.arguments().get(0);
		assertEquals("upper_bound", hint.function.arguments.get(hint.argumentIndex));
	}

	@Test
	public void lotsOfArgs() {
		Hints hints = new Hints("random(12, col1, 12, 12, 12", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 4)));
	}

	@Test
	public void argsComplicated1() {
		Hints hints = new Hints("customWindow(min(12 + random(2, 12)), random(2, 13), random(2, 12)", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("customWindow", 2)));

		ArgumentHint hint = hints.arguments().get(0);
		assertEquals("following_row", hint.function.arguments.get(hint.argumentIndex));
	}

	@Test
	public void argsComplicated2() {
		Hints hints = new Hints("customWindow(min(12 + random(2, 12)), random(2, 13), random(2, 12", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("customWindow", 2), arg("random", 1)));
	}
}

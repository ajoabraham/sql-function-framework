package frmw.model;

import frmw.model.hint.ArgumentHint;
import frmw.parser.Hints;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
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

	static Matcher<ArgumentHint> arg(final String name, final int index, final int position) {
		return new BaseMatcher<ArgumentHint>() {
			@Override
			public boolean matches(Object item) {
				ArgumentHint hint = (ArgumentHint) item;
				return hint.function.name().equalsIgnoreCase(name) && hint.index == index && hint.position == position;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(format("name={0}, index={1}, position={2}", name, index, position));
			}
		};
	}

	@Test
	public void cursorInTheEnd() {
		Hints hints = Hints.select("func", 4, PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void cursorDefault() {
		Hints hints = Hints.select("func", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void cursorInStart() {
		Hints hints = Hints.select("func", 0, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void cursorInMiddle() {
		Hints hints = Hints.select("func", 2, PARSER);
		assertTrue(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cursorNegative() {
		Hints.select("func", -1, PARSER);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cursorLongerThanFormula() {
		Hints.select("func", 5, PARSER);
	}

	@Test
	public void doubleQuoteOpened() {
		Hints hints = Hints.select("\"func", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void doubleQuoteOpened_middle() {
		Hints hints = Hints.select("\"func", 2, PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void singleQuoteOpened() {
		Hints hints = Hints.select("'func", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void singleQuoteOpened_middle() {
		Hints hints = Hints.select("'func", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void onlyOpenedSingleQuote() {
		Hints hints = Hints.select("'", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void onlyOpenedDoubleQuote() {
		Hints hints = Hints.select("\"", PARSER);
		assertFalse(hints.functionHint());
		assertFalse(hints.anyFunction());
	}

	@Test
	public void doubleQuoteInSingleQuoted() {
		Hints hints = Hints.select("'fu\"nc' + bar", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void doubleQuoteInSingleQuoted_middle() {
		Hints hints = Hints.select("'fu\"nc' + bar", 11, PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void escapedQuotesInStringLiteral() {
		Hints hints = Hints.select("'fu''nc' + bar", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void singleQuoteInDoubleQuoted() {
		Hints hints = Hints.select("\"fu'nc\" + bar", PARSER);
		assertTrue(hints.functionHint());
	}

	@Test
	public void cursorAfterWhitespace() {
		Hints hints = Hints.select("func ", PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void cursorAfterWhitespace_middle() {
		Hints hints = Hints.select("func func2 ", 5, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void startsWithMoving() {
		Hints hints = Hints.select("moving", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount"));
	}

	@Test
	public void startsWithMo() {
		Hints hints = Hints.select("moving", 2, PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount", "Mod", "Month"));
	}

	@Test
	public void startsWithMo_filterByDialect() {
		Hints hints = Hints.select("moving", 2, PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions(GENERIC_SQL)), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount", "Month"));
	}

	@Test
	public void plusBeforeName() {
		Hints hints = Hints.select("col1 +yea", PARSER);
		assertThat(names(hints.functions()), containsInAnyOrder("Year"));
	}

	@Test
	public void whitespaceBeforeName() {
		Hints hints = Hints.select("sum( ran", PARSER);
		assertThat(hints.arguments(), contains(arg("sum", 0, 5)));
		assertThat(names(hints.functions()), containsInAnyOrder("Random"));
	}

	@Test
	public void noInputAfterSpecialSymbol() {
		Hints hints = Hints.select("col1+col2", 5, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}

	@Test
	public void userCannotInsertAggregationAndOLAPInAggregation() {
		Hints hints = Hints.select("min(c", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("Cos", "CosH", "CurrentDate", "CurrentTimestamp", "Custom"));
	}

	@Test
	public void userCanInsertOnlyAggregationInOLAP() {
		Hints hints = Hints.select("customWindow(co", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), contains("Count"));
	}

	@Test
	public void mistakeInFunctionNameBeforeCursorPosition() {
		Hints hints = Hints.select("customWindow(customWind(co", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("Cos", "CosH", "Count"));
	}

	@Test
	public void oneParameter() {
		Hints hints = Hints.select("min(", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0, 4)));
	}

	@Test
	public void severalParameters() {
		Hints hints = Hints.select(" min(avg(sin(", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0, 5), arg("avg", 0, 9), arg("sin", 0, 13)));
	}

	@Test
	public void oneFunctionIsClosed() {
		Hints hints = Hints.select("min(avg(sin() + 123", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0, 4), arg("avg", 0, 8)));
	}

	@Test
	public void lotsOfParenthesis() {
		Hints hints = Hints.select("abs() + min((avg((sin() + 123) + (123 * (34)) + 14", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("min", 0, 12), arg("avg", 0, 17)));
	}

	@Test
	public void secondArg() {
		Hints hints = Hints.select("random(12, col1", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 1, 11)));

		ArgumentHint hint = hints.arguments().get(0);
		assertEquals("upper_bound", hint.function.arguments().get(hint.index));
	}

	@Test
	public void thirdArgBlank() {
		Hints hints = Hints.select("random(12, col1,", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 2, 16)));
	}

	@Test
	public void thirdArgBlankWithWhitespace() {
		Hints hints = Hints.select("random(12, col1, \n\t\r ", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 2, 21)));
	}

	@Test
	public void lotsOfArgs() {
		Hints hints = Hints.select("random(12, col1, 12, 12, 12", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 4, 25)));
	}

	@Test
	public void argsComplicated1() {
		Hints hints = Hints.select("customWindow(min(12 + random(2, 12)), random(2, 13), random(2, 12)", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("customWindow", 2, 53)));

		ArgumentHint hint = hints.arguments().get(0);
		assertEquals("following_row", hint.function.arguments().get(hint.index));
	}

	@Test
	public void argsComplicated2() {
		Hints hints = Hints.select("customWindow(min(12 + random(2, 12)), random(2, 13), random(2, 12", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("customWindow", 2, 53), arg("random", 1, 63)));
	}

	@Test
	public void oneOfTheArgsIsDoubleQuotedColumnWithComma() {
		Hints hints = Hints.select("random(12, col1, \"col1,col,col\", 12, 12", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 4, 37)));
	}

	@Test
	public void oneOfTheArgsIsDoubleQuotedColumnWithComma_opened() {
		Hints hints = Hints.select("random(12, col1, 12, 12, \"12,13", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 4, 25)));
	}

	@Test
	public void oneOfTheArgsIsSingleQuotedColumnWithComma() {
		Hints hints = Hints.select("random(12, col1, \'col1,''col\",col\', 12, 12", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 4, 40)));
	}

	@Test
	public void oneOfTheArgsIsSingleQuotedColumnWithComma_opened() {
		Hints hints = Hints.select("random(12, col1, 12, 12, \'12,13", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(), contains(arg("random", 4, 25)));
	}

	@Test
	public void severalParameters_filterByDialect() {
		Hints hints = Hints.select("min(trimLeft(avg(sin(", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(GENERIC_SQL), contains(arg("min", 0, 4), arg("avg", 0, 17)));
	}

	@Test
	public void join_fun() {
		Hints hints = Hints.join("T1.col1 = m", PARSER);
		assertTrue(hints.functionHint());
		assertThat(names(hints.functions()), containsInAnyOrder("Mod", "Month", "Minute"));
	}

	@Test
	public void join_arg() {
		Hints hints = Hints.join("12 >= min(trimLeft(avg(sin(", PARSER);
		assertTrue(hints.argumentHint());
		assertThat(hints.arguments(GENERIC_SQL), contains(arg("min", 0, 10), arg("avg", 0, 23)));
	}
}

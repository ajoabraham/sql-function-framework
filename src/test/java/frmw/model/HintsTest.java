package frmw.model;

import org.junit.Test;

import static frmw.TestSupport.PARSER;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * @author Alexey Paramonov
 */
public class HintsTest {

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
		assertThat(hints.functions(), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount"));
	}

	@Test
	public void startsWithMo() {
		Hints hints = new Hints("moving", 2, PARSER);
		assertTrue(hints.functionHint());
		assertThat(hints.functions(), containsInAnyOrder("MovingAvg", "MovingSum", "MovingCount", "Mod", "Month"));
	}

	@Test
	public void plusBeforeName() {
		Hints hints = new Hints("col1 +yea", PARSER);
		assertThat(hints.functions(), containsInAnyOrder("Year"));
	}

	@Test
	public void whitespaceBeforeName() {
		Hints hints = new Hints("sum( ran", PARSER);
		assertThat(hints.functions(), containsInAnyOrder("Rank", "Random"));
	}

	@Test
	public void noInputAfterSpecialSymbol() {
		Hints hints = new Hints("col1+col2", 5, PARSER);
		assertFalse(hints.functionHint());
		assertTrue(hints.anyFunction());
	}
}

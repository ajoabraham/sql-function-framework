package frmw.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static frmw.parser.Common.COLUMN_CHARS;
import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.reverse;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/**
 * Providing hints to user input.
 *
 * @author Alexey Paramonov
 */
public class Hints {

	private final boolean anyFunction;
	private final boolean function;
	private final boolean parameter;

	private final List<String> functions;

	/**
	 * Hints will be decided as if user place cursor to the end of the input.
	 *
	 * @param formula taped formula
	 * @param parser  parser
	 */
	public Hints(String formula, Parsing parser) {
		this(formula, formula.length(), parser);
	}

	/**
	 * @param formula taped formula
	 * @param cursor  cursor position in the formula
	 * @param parser  parser
	 * @throws IllegalArgumentException if input is invalid
	 */
	public Hints(String formula, int cursor, Parsing parser) {
		if (cursor == 0) {
			anyFunction = true;
			function = false;
			parameter = false;
			functions = emptyList();
			return;
		}

		if (cursor < 0 || cursor > formula.length()) {
			throw new IllegalArgumentException(format("" +
					"cursorPosition should be in the bound of the formula string, " +
					"but found formula \"{0}\" length {1}, cursor position {2}",
					formula, formula.length(), cursor));
		}

		anyFunction = lastCharIsSpecialCharacter(formula, cursor);
		function = !anyFunction && allQuotesAreClosed(formula, cursor);
		parameter = false;

		functions = function ? decideSuitableFunctions(formula, cursor, parser) : Collections.<String>emptyList();
	}

	private List<String> decideSuitableFunctions(String formula, int cursor, Parsing parser) {
		List<String> result = new ArrayList<String>();
		String functionName = extractFunction(formula, cursor);

		for (String name : parser.functions()) {
			if (startsWithIgnoreCase(name, functionName)) {
				result.add(name);
			}
		}

		return result;
	}

	private String extractFunction(String formula, int cursor) {
		StringBuilder sb = new StringBuilder();

		for (int i = cursor - 1; i >= 0; i--) {
			char c = formula.charAt(i);
			if (!COLUMN_CHARS.isChar(c)) {
				break;
			}

			sb.append(c);
		}

		return reverse(sb.toString());
	}

	private boolean lastCharIsSpecialCharacter(String formula, int cursorPosition) {
		char last = formula.charAt(cursorPosition - 1);
		return !COLUMN_CHARS.isChar(last);
	}

	private boolean allQuotesAreClosed(String formula, int cursorPosition) {
		boolean single = false;
		boolean doubleQ = false;

		for (int i = 0; i < cursorPosition; i++) {
			char c = formula.charAt(i);

			if (c == '"' && !single) {
				doubleQ = !doubleQ;
			}

			if (c == '\'' && !doubleQ) {
				single = !single;
			}
		}

		return !single && !doubleQ;
	}

	/**
	 * Always returns false if {@link #anyFunction()} returns true
	 *
	 * @return true if user started taping function name
	 */
	public boolean functionHint() {
		return function;
	}

	/**
	 * Always returns false if {@link #functionHint()} returns true
	 *
	 * @return true if any function can be placed in the cursor position
	 */
	public boolean anyFunction() {
		return anyFunction;
	}

	/**
	 * @return function hint
	 */
	public List<String> functions() {
		return functions;
	}

	public boolean parameter() {
		return parameter;
	}

	public List<String> parameters() {
		return emptyList();
	}
}

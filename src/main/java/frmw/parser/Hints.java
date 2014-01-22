package frmw.parser;

import frmw.model.exception.WrongFunctionNameException;
import frmw.model.hint.ArgumentHint;
import frmw.model.hint.FunctionSpec;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static frmw.parser.Common.COLUMN_CHARS;
import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.reverse;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/**
 * Providing hints for user input.
 *
 * @author Alexey Paramonov
 */
public class Hints {

	private static final List<FunctionSpec> EMPTY_SPEC = emptyList();

	private final boolean anyFunction;
	private final boolean function;

	private final List<FunctionSpec> functions;
	private final List<ArgumentHint> arguments;

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
			functions = EMPTY_SPEC;
			arguments = emptyList();
			return;
		}

		if (cursor < 0 || cursor > formula.length()) {
			throw new IllegalArgumentException(format("" +
					"cursorPosition should be in the bound of the formula string, " +
					"but found formula \"{0}\" length {1}, cursor position {2}",
					formula, formula.length(), cursor));
		}

		anyFunction = lastCharIsSpecialCharacter(formula, cursor);
		if (!anyFunction) {
			boolean res[] = quotesStatistic(formula, cursor);
			function = !res[0] && !res[1];
		} else {
			function = false;
		}

		functions = function ? decideSuitableFunctions(formula, cursor, parser) : EMPTY_SPEC;
		arguments = decideParameters(formula, cursor, parser);
	}

	private List<ArgumentHint> decideParameters(String formula, int cursor, Parsing parser) {
		LinkedList<ArgumentHint> functionStack = new LinkedList<ArgumentHint>();
		StringBuilder currentFunction = new StringBuilder();

		boolean quotes[] = quotesStatistic(formula, cursor);
		boolean single = quotes[0];
		boolean doubleQ = quotes[1];

		int openedAndClosed = 0;
		int argIndex = 0;
		boolean followingIsFunctionName = false;

		for (int i = cursor - 1; i >= 0; i--) {
			char ch = formula.charAt(i);

			if (ch == '"' && !single) {
				doubleQ = !doubleQ;
			} else if (ch == '\'' && !doubleQ) {
				single = !single;
			}

			if (single || doubleQ) {
				continue;
			}

			if (COLUMN_CHARS.isChar(ch) && followingIsFunctionName) {
				currentFunction.append(ch);
				continue;
			} else if (ch == '(') {
				followingIsFunctionName = false;
				if (tryToCollectFunction(functionStack, currentFunction, parser, argIndex)) {
					argIndex = 0;
				}

				if (openedAndClosed > 0) {
					openedAndClosed--;
				} else {
					followingIsFunctionName = true;
				}

				continue;
			} else if (ch == ')') {
				openedAndClosed++;
			} else if (ch == ',' && openedAndClosed == 0) {
				argIndex++;
			}

			followingIsFunctionName = false;
			if (tryToCollectFunction(functionStack, currentFunction, parser, argIndex)) {
				argIndex = 0;
			}
		}

		tryToCollectFunction(functionStack, currentFunction, parser, argIndex);
		return functionStack;
	}

	private boolean tryToCollectFunction(LinkedList<ArgumentHint> functionStack, StringBuilder currentFunction, Parsing parser, int index) {
		if (currentFunction.length() == 0) {
			return false;
		}

		String reversed = reverse(currentFunction.toString());
		FunctionSpec spec = parser.byName(reversed);
		if (spec != null) {
			functionStack.addFirst(new ArgumentHint(spec, index));
		}
		currentFunction.delete(0, currentFunction.length());
		return true;
	}

	private List<FunctionSpec> decideSuitableFunctions(String formula, int cursor, Parsing parser) {
		String taped = extractFunction(formula, cursor);
		String fakeFormula = formula.substring(0, cursor) + "(";

		try {
			parser.parse(fakeFormula);
		} catch (WrongFunctionNameException ex) {
			if (ex.index() + ex.length() != cursor) {
				// formula has errors that are not related to taped function name
				return matchedFunctions(taped, parser.functions());
			} else {
				return matchedFunctions(taped, ex.expectedFunctions);
			}
		} catch (Exception e) {
			// formula has errors that are not related to function names
			return matchedFunctions(taped, parser.functions());
		}

		return matchedFunctions(taped, parser.functions());
	}

	private List<FunctionSpec> matchedFunctions(String taped, Iterable<FunctionSpec> functions) {
		List<FunctionSpec> result = new ArrayList<FunctionSpec>();

		for (FunctionSpec spec : functions) {
			if (startsWithIgnoreCase(spec.name(), taped)) {
				result.add(spec);
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

	private boolean[] quotesStatistic(String formula, int cursorPosition) {
		boolean single = false;
		boolean doubleQ = false;

		for (int i = 0; i < cursorPosition; i++) {
			char c = formula.charAt(i);

			if (c == '"' && !single) {
				doubleQ = !doubleQ;
			}else if (c == '\'' && !doubleQ) {
				single = !single;
			}
		}

		return new boolean[] {single, doubleQ};
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
	 * @return list of suitable function names in cursor positions that user may want to tape
	 */
	public List<FunctionSpec> functions() {
		return functions;
	}

	public boolean argumentHint() {
		return !arguments.isEmpty();
	}

	/**
	 * @return argument hint stack
	 */
	public List<ArgumentHint> arguments() {
		return arguments;
	}
}

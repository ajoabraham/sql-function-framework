package frmw.parser;

import com.google.common.base.Predicate;
import frmw.dialect.Dialect;
import frmw.model.exception.WrongFunctionNameException;
import frmw.model.fun.FunctionSpec;
import frmw.model.hint.ArgumentHint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
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

	private final Parsing parser;

	/**
	 * Hints for select formula {@link frmw.model.Formula}.
	 * Hints will be decided as if user place cursor to the end of the input.
	 *
	 * @param formula taped formula
	 * @param parser  parser
	 */
	public static Hints select(String formula, Parsing parser) {
		return select(formula, formula.length(), parser);
	}

	/**
	 * Hints for select formula {@link frmw.model.Formula}.
	 *
	 * @param formula taped formula
	 * @param cursor  cursor position in the formula
	 * @param parser  parser
	 * @throws IllegalArgumentException if input is invalid
	 */
	public static Hints select(String formula, int cursor, Parsing parser) {
		return new Hints(formula, cursor, parser, HintType.SELECT);
	}

	/**
	 * Hints for join formula {@link frmw.model.Join}.
	 * Hints will be decided as if user place cursor to the end of the input.
	 *
	 * @param formula taped formula
	 * @param parser  parser
	 */
	public static Hints join(String formula, Parsing parser) {
		return join(formula, formula.length(), parser);
	}

	/**
	 * Hints for join formula {@link frmw.model.Join}.
	 *
	 * @param formula taped formula
	 * @param cursor  cursor position in the formula
	 * @param parser  parser
	 * @throws IllegalArgumentException if input is invalid
	 */
	public static Hints join(String formula, int cursor, Parsing parser) {
		return new Hints(formula, cursor, parser, HintType.JOIN);
	}

	/**
	 * @deprecated use {@link #select(String, Parsing)} instead
	 */
	@Deprecated
	public Hints(String formula, Parsing parser) {
		this(formula, formula.length(), parser, HintType.SELECT);
	}

	/**
	 * @deprecated use {@link #select(String, Parsing)} instead
	 */
	@Deprecated
	public Hints(String formula, int cursor, Parsing parser) {
		this(formula, cursor, parser, HintType.SELECT);
	}

	private Hints(String formula, int cursor, Parsing parser, HintType type) {
		this.parser = parser;
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

		functions = function ? decideSuitableFunctions(formula, cursor, type) : EMPTY_SPEC;
		arguments = decideParameters(formula, cursor);
	}

	private List<ArgumentHint> decideParameters(String formula, int cursor) {
		LinkedList<ArgumentHint> functionStack = new LinkedList<ArgumentHint>();
		StringBuilder currentFunction = new StringBuilder();

		boolean quotes[] = quotesStatistic(formula, cursor);
		boolean single = quotes[0];
		boolean doubleQ = quotes[1];

		int openedAndClosed = 0;
		int argIndex = 0;
		int argPosition = cursor - 1;
		boolean followingIsFunctionName = false;

		for (int i = argPosition; i >= 0; i--) {
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
				if (tryToCollectFunction(functionStack, currentFunction, argIndex, argPosition)) {
					argIndex = 0;
				}

				if (openedAndClosed > 0) {
					openedAndClosed--;
				} else {
					followingIsFunctionName = true;
					if (argIndex == 0) {
						argPosition = updatePosition(formula, i);
					}
				}

				continue;
			} else if (ch == ')') {
				openedAndClosed++;
			} else if (ch == ',' && openedAndClosed == 0) {
				if (argIndex == 0) {
					argPosition = updatePosition(formula, i);
				}

				argIndex++;
			}

			followingIsFunctionName = false;
			if (tryToCollectFunction(functionStack, currentFunction, argIndex, argPosition)) {
				argIndex = 0;
			}
		}

		tryToCollectFunction(functionStack, currentFunction, argIndex, argPosition);
		return functionStack;
	}

	private int updatePosition(String formula, int i) {
		i++; // ignore '(' or ','
		return i + amountOfWhitespacesOnPosition(formula, i);
	}

	private int amountOfWhitespacesOnPosition(String formula, int position) {
		int result = 0;

		for (int i = position; i < formula.length(); i++) {
			char ch = formula.charAt(i);
			if (Character.isWhitespace(ch)) {
				result++;
			} else {
				break;
			}
		}

		return result;
	}

	private boolean tryToCollectFunction(LinkedList<ArgumentHint> functionStack, StringBuilder currentFunction, int index, int position) {
		if (currentFunction.length() == 0) {
			return false;
		}

		String reversed = reverse(currentFunction.toString());
		FunctionSpec spec = parser.registry().byName(reversed);
		if (spec != null) {
			functionStack.addFirst(new ArgumentHint(spec, index, position));
		}
		currentFunction.delete(0, currentFunction.length());
		return true;
	}

	private List<FunctionSpec> decideSuitableFunctions(String formula, int cursor, HintType type) {
		String taped = extractFunction(formula, cursor);
		String fakeFormula = formula.substring(0, cursor) + "(";

		try {
			type.parse(parser, fakeFormula);
		} catch (WrongFunctionNameException ex) {
			if (ex.index() + ex.length() != cursor) {
				// formula has errors that are not related to taped function name
				return matchedFunctions(taped, parser.registry().all());
			} else {
				return matchedFunctions(taped, ex.expectedFunctions);
			}
		} catch (Exception e) {
			// formula has errors that are not related to function names
			return matchedFunctions(taped, parser.registry().all());
		}

		return matchedFunctions(taped, parser.registry().all());
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
			} else if (c == '\'' && !doubleQ) {
				single = !single;
			}
		}

		return new boolean[]{single, doubleQ};
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

	/**
	 * @return list of suitable function names in cursor positions that user may want to tape
	 * include filtering by supporting by dialect
	 */
	public List<FunctionSpec> functions(Dialect dialect) {
		final Set<FunctionSpec> allSupported = parser.registry().all(dialect);
		return newArrayList(filter(functions, new Predicate<FunctionSpec>() {
			@Override
			public boolean apply(FunctionSpec input) {
				return allSupported.contains(input);
			}
		}));
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

	/**
	 * @return argument hint stack including filtering by supporting by dialect
	 */
	public List<ArgumentHint> arguments(Dialect dialect) {
		final Set<FunctionSpec> allSupported = parser.registry().all(dialect);
		return newArrayList(filter(arguments, new Predicate<ArgumentHint>() {
			@Override
			public boolean apply(ArgumentHint input) {
				return allSupported.contains(input.function);
			}
		}));
	}
}

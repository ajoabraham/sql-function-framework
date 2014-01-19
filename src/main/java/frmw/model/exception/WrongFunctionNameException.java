package frmw.model.exception;

import frmw.model.hint.FunctionSpec;
import frmw.parser.FunctionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.getLevenshteinDistance;

/**
 * @author Alexey Paramonov
 */
public class WrongFunctionNameException extends SQLFrameworkException {

	/**
	 * Wrong function name in the formula.
	 */
	public final String function;

	/**
	 * Expected function types in the {@link #index} location.
	 */
	public final Set<FunctionType> expectedTypes;

	/**
	 * Expected function names in the {@link #index} location.
	 */
	public final List<FunctionSpec> expectedFunctions;

	/**
	 * The most closest function names to the user input at {@link #index}.
	 */
	public final List<FunctionSpec> closestFunctions;

	public WrongFunctionNameException(int errorAt, String function, Set<FunctionType> types, List<FunctionSpec> expected) {
		super(format("Function name \"{0}\", but expected one of the {1}", function, expected));
		this.function = function;
		this.expectedTypes = types;
		this.expectedFunctions = expected;
		this.closestFunctions = findClosest(function, expected);
		position(errorAt, function.length());
	}

	private static List<FunctionSpec> findClosest(String wrongName, List<FunctionSpec> expected) {
		wrongName = wrongName.toLowerCase();
		List<FunctionSpec> result = new ArrayList<FunctionSpec>();

		int maxDistance = wrongName.length() / 3;
		if (maxDistance == 0) {
			maxDistance = 1;
		}

		for (FunctionSpec s : expected) {
			int distance = getLevenshteinDistance(wrongName, s.name.toLowerCase(), maxDistance);
			if (distance >= 0) {
				result.add(s);
			}
		}

		return result;
	}
}

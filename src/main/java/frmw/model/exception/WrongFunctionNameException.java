package frmw.model.exception;

import frmw.parser.FunctionType;
import frmw.parser.Parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static frmw.parser.FunctionType.AGGREGATION;
import static frmw.parser.FunctionType.SCALAR;
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
	public final List<String> expectedFunctions;

	/**
	 * Most closest function names in the {@link #index} location.
	 */
	public final List<String> closestFunctions;

	public WrongFunctionNameException(int errorAt, String function, Set<FunctionType> types, Parsing p) {
		super();
		this.function = function;
		this.expectedTypes = types;

		List<String> expected = new ArrayList<String>();
		if (types.contains(AGGREGATION)) {
			expected.addAll(p.aggregationFunctions());
		}

		if (types.contains(SCALAR)) {
			expected.addAll(p.scalarFunctions());
		}

		this.expectedFunctions = expected;
		this.closestFunctions = findClosest(function, expected);
		position(errorAt, function.length());
	}

	private static List<String> findClosest(String wrongName, List<String> expected) {
		wrongName = wrongName.toLowerCase();
		List<String> result = new ArrayList<String>();

		int maxDistance = wrongName.length() / 3;
		if (maxDistance == 0) {
			maxDistance = 1;
		}

		for (String s : expected) {
			int distance = getLevenshteinDistance(wrongName, s.toLowerCase(), maxDistance);
			if (distance >= 0) {
				result.add(s);
			}
		}

		return result;
	}
}

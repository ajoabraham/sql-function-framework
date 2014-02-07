package frmw.model.operator;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public final class ConditionalOperators {

	private ConditionalOperators() {
	}

	public static BinaryOperator eq(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "=");
	}

	public static BinaryOperator ne(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "!=");
	}

	public static BinaryOperator gt(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, ">");
	}

	public static BinaryOperator lt(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "<");
	}

	public static BinaryOperator ge(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, ">=");
	}

	public static BinaryOperator le(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "<=");
	}

	public static BetweenOperator between(FormulaElement tested, FormulaElement left, FormulaElement right) {
		return new BetweenOperator(tested, left, right);
	}
}

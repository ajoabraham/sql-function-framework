package frmw.model.operator;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public final class ArithmeticOperators {

	private ArithmeticOperators() {
	}

	public static BinaryOperator plus(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "+");
	}

	public static BinaryOperator minus(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "-");
	}

	public static BinaryOperator multiple(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "*");
	}

	public static BinaryOperator divide(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "/");
	}
}

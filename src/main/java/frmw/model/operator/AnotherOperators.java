package frmw.model.operator;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public final class AnotherOperators {

	private AnotherOperators() {
	}

	public static BinaryOperator concat(FormulaElement left, FormulaElement right) {
		return new BinaryOperator(left, right, "||");
	}
}

package frmw.model.operator;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class And extends BinaryOperator {

	public static final String OP = "AND";

	public And(FormulaElement left, FormulaElement right) {
		super(left, right, OP);
	}
}

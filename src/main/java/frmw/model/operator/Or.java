package frmw.model.operator;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Or extends BinaryOperator {

	public static final String OP = "OR";

	public Or(FormulaElement left, FormulaElement right) {
		super(left, right, OP);
	}
}

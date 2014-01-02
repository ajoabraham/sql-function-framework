package frmw.model.fun.math.operator;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class BinaryOperator implements FormulaElement {

	private final FormulaElement left;

	private final FormulaElement right;

	private final String op;

	public BinaryOperator(FormulaElement left, FormulaElement right, String op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		sb.append('(');
		left.sql(dialect, sb);
		sb.append(' ').append(op).append(' ');
		right.sql(dialect, sb);
		sb.append(')');
	}

	@Override
	public boolean hasAggregation() {
		return left.hasAggregation() || right.hasAggregation();
	}
}

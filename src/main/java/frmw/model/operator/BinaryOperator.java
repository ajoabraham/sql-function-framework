package frmw.model.operator;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class BinaryOperator implements FormulaElement {

	public final FormulaElement left;

	public final FormulaElement right;

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

	@Override
	public void collectEntities(Set<String> set) {
		left.collectEntities(set);
		right.collectEntities(set);
	}

	@Override
	public String toString() {
		return "(" + left + ' ' + op + ' ' + right + ')';
	}
}

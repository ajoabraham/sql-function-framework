package frmw.model.operator;

import frmw.dialect.Dialect;
import frmw.model.traverse.ColumnTraversal;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
class BinaryOperator implements FormulaElement {

	public final FormulaElement left;

	public final FormulaElement right;

	public final String op;

	BinaryOperator(FormulaElement left, FormulaElement right, String op) {
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
	public void traverseColumns(ColumnTraversal traversal) {
		left.traverseColumns(traversal);
		right.traverseColumns(traversal);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		left.collectWindowParams(list);
		right.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		left.collectAggregationParams(list);
		right.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		left.collectRankParams(list);
		right.collectRankParams(list);
	}

	@Override
	public String toString() {
		return "(" + left + ' ' + op + ' ' + right + ')';
	}
}

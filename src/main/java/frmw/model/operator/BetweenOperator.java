package frmw.model.operator;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.traverse.ColumnTraversal;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
class BetweenOperator implements FormulaElement {

	public final FormulaElement tested;

	public final FormulaElement left;

	public final FormulaElement right;

	BetweenOperator(FormulaElement tested, FormulaElement left, FormulaElement right) {
		this.tested = tested;
		this.left = left;
		this.right = right;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		tested.sql(dialect, sb);
		sb.append(" BETWEEN ");
		left.sql(dialect, sb);
		sb.append(" AND ");
		right.sql(dialect, sb);
	}

	@Override
	public boolean hasAggregation() {
		return tested.hasAggregation() || left.hasAggregation() || right.hasAggregation();
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		tested.traverseColumns(traversal);
		left.traverseColumns(traversal);
		right.traverseColumns(traversal);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		tested.collectWindowParams(list);
		left.collectWindowParams(list);
		right.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		tested.collectAggregationParams(list);
		left.collectAggregationParams(list);
		right.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		tested.collectRankParams(list);
		left.collectRankParams(list);
		right.collectRankParams(list);
	}

	@Override
	public String toString() {
		return tested + " BETWEEN " + left + " AND " + right;
	}
}

package frmw.model;

import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.traverse.ColumnTraversal;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public abstract class Scalar2 implements FormulaElement {

	protected final FormulaElement arg1;
	protected final FormulaElement arg2;

	protected Scalar2(FormulaElement arg1, FormulaElement arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public boolean hasAggregation() {
		return arg1.hasAggregation() || arg2.hasAggregation();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg1 + ", " + arg2 + ')';
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		arg1.traverseColumns(traversal);
		arg2.traverseColumns(traversal);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		arg1.collectWindowParams(list);
		arg2.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		arg1.collectAggregationParams(list);
		arg2.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		arg1.collectRankParams(list);
		arg2.collectRankParams(list);
	}
}

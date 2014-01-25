package frmw.model;

import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.traverse.ColumnTraversal;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public abstract class Scalar implements FormulaElement {

	protected final FormulaElement arg;

	protected Scalar(FormulaElement arg) {
		this.arg = arg;
	}

	@Override
	public boolean hasAggregation() {
		return arg.hasAggregation();
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		arg.traverseColumns(traversal);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		arg.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		arg.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		arg.collectRankParams(list);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg + ')';
	}
}

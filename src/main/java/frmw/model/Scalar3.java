package frmw.model;

import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public abstract class Scalar3 implements FormulaElement {

	protected final FormulaElement arg1;
	protected final FormulaElement arg2;
	protected final FormulaElement arg3;

	protected Scalar3(FormulaElement arg1, FormulaElement arg2, FormulaElement arg3) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
	}

	@Override
	public boolean hasAggregation() {
		return arg1.hasAggregation() || arg2.hasAggregation() || arg3.hasAggregation();
	}

	@Override
	public void collectEntities(Set<String> set) {
		arg1.collectEntities(set);
		arg2.collectEntities(set);
		arg3.collectEntities(set);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		arg1.collectWindowParams(list);
		arg2.collectWindowParams(list);
		arg3.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		arg1.collectAggregationParams(list);
		arg2.collectAggregationParams(list);
		arg3.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		arg1.collectRankParams(list);
		arg2.collectRankParams(list);
		arg3.collectRankParams(list);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg1 + ", " + arg2 + ", " + arg3 + ')';
	}
}

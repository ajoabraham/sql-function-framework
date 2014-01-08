package frmw.model;

import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

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
	public void collectEntities(Set<String> set) {
		arg1.collectEntities(set);
		arg2.collectEntities(set);
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
}

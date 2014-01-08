package frmw.model.fun.aggregation;

import frmw.model.FormulaElement;
import frmw.model.PositionAware;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.fun.olap.support.Position;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public abstract class Aggregation implements FormulaElement, PositionAware {

	protected final FormulaElement column;

	private final AggregationParameters params;

	public boolean distinct;

	protected Aggregation(FormulaElement column) {
		this.column = column;
		this.params = new AggregationParameters(this);
	}

	@Override
	public boolean hasAggregation() {
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + column + ')';
	}

	@Override
	public void collectEntities(Set<String> set) {
		column.collectEntities(set);
	}

	@Override
	public void position(int index, int length) {
		params.position(new Position(index, length));
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		list.add(params);
	}
}
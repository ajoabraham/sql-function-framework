package frmw.model.fun.aggregation;

import frmw.model.traverse.ColumnTraversal;
import frmw.model.FormulaElement;
import frmw.model.position.PositionAware;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.position.Position;

import java.util.List;

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
	public void traverseColumns(ColumnTraversal traversal) {
		column.traverseColumns(traversal);
	}

	@Override
	public void position(int index, int length) {
		params.position(new Position(index, length));
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		column.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		column.collectAggregationParams(list);
		list.add(params);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		column.collectRankParams(list);
	}
}

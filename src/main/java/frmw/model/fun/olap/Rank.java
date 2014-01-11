package frmw.model.fun.olap;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.PositionAware;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.position.Position;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class Rank implements FormulaElement, PositionAware {

	private final FormulaElement orderBy;

	private final RankParameters params = new RankParameters();

	public Rank(FormulaElement orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.rank(sb, params, orderBy);
	}

	@Override
	public boolean hasAggregation() {
		return true;
	}

	@Override
	public void collectEntities(Set<String> set) {
		orderBy.collectEntities(set);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		orderBy.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		orderBy.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		list.add(params);
	}

	@Override
	public void position(int index, int length) {
		params.position(new Position(index, length));
	}
}

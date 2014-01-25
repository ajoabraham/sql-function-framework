package frmw.model.fun.dateTime;

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
public class CurrentDate implements FormulaElement {

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.currentDate(sb);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}

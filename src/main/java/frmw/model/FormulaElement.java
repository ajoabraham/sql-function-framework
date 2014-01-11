package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public interface FormulaElement {

	void sql(Dialect dialect, StringBuilder sb);

	/**
	 * @return is element contains aggregation or OLAP functions?
	 */
	boolean hasAggregation();

	void collectEntities(Set<String> set);

	void collectWindowParams(List<WindowParameters> list);

	void collectAggregationParams(List<AggregationParameters> list);

	void collectRankParams(List<RankParameters> list);
}

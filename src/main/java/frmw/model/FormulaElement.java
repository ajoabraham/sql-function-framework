package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

/**
 * Inner representation of each formula element in the parsed tree.
 * Each implementation has to have at least one public constructor.
 * Each constructor must not do any operations with passed data, should only set internal fields.
 *
 * @author Alexey Paramonov
 */
public interface FormulaElement {

	/**
	 * Appends sql clauses to the collector.
	 *
	 * @param dialect dialect that applied to the formula
	 * @param sb      collector
	 */
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

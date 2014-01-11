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
public class Column implements FormulaElement {

	private final String name;

	private final boolean quoted;

	public Column(String name, boolean quoted) {
		this.name = name.trim();
		this.quoted = quoted;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		if (quoted) {
			sb.append('"').append(name).append('"');
		} else {
			sb.append(name);
		}
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public void collectEntities(Set<String> set) {
		set.add(name);
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
		return name;
	}
}

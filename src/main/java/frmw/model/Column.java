package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class Column implements FormulaElement {

	private final String name;

	public Column(String name) {
		this.name = name.trim();
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.validateColumnName(name);
		sb.append(name);
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
	public String toString() {
		return name;
	}
}

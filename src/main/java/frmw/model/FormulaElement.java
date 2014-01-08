package frmw.model;

import frmw.dialect.Dialect;

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
}

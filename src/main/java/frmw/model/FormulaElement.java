package frmw.model;

import frmw.dialect.Dialect;

/**
 * @author Alexey Paramonov
 */
public interface FormulaElement {

	void sql(Dialect dialect, StringBuilder sb);

	/**
	 * @return is element contains aggregation or OLAP functions?
	 */
	boolean hasAggregation();
}

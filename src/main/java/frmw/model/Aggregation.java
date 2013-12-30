package frmw.model;

/**
 * @author Alexey Paramonov
 */
public abstract class Aggregation implements FormulaElement {

	@Override
	public boolean hasAggregation() {
		return true;
	}
}

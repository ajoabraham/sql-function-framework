package frmw.model;

/**
 * @author Alexey Paramonov
 */
public abstract class Aggregation implements FormulaElement {

	protected final FormulaElement column;

	protected Aggregation(FormulaElement column) {
		this.column = column;
	}

	@Override
	public boolean hasAggregation() {
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + column + ')';
	}
}

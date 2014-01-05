package frmw.model;

/**
 * @author Alexey Paramonov
 */
public abstract class Scalar2 implements FormulaElement {

	protected final FormulaElement arg1;
	protected final FormulaElement arg2;

	protected Scalar2(FormulaElement arg1, FormulaElement arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public boolean hasAggregation() {
		return arg1.hasAggregation() || arg2.hasAggregation();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg1 + ", " + arg2 + ')';
	}
}

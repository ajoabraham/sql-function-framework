package frmw.model;

/**
 * @author Alexey Paramonov
 */
public abstract class Scalar implements FormulaElement {

	protected final FormulaElement arg;

	protected Scalar(FormulaElement arg) {
		this.arg = arg;
	}

	@Override
	public boolean hasAggregation() {
		return arg.hasAggregation();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg + ')';
	}
}

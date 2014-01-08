package frmw.model;

import java.util.Set;

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
	public void collectEntities(Set<String> set) {
		arg.collectEntities(set);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg + ')';
	}
}

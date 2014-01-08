package frmw.model;

import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public abstract class Scalar3 implements FormulaElement {

	protected final FormulaElement arg1;
	protected final FormulaElement arg2;
	protected final FormulaElement arg3;

	protected Scalar3(FormulaElement arg1, FormulaElement arg2, FormulaElement arg3) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
	}

	@Override
	public boolean hasAggregation() {
		return arg1.hasAggregation() || arg2.hasAggregation() || arg3.hasAggregation();
	}

	@Override
	public void collectEntities(Set<String> set) {
		arg1.collectEntities(set);
		arg2.collectEntities(set);
		arg3.collectEntities(set);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + arg1 + ", " + arg2 + ", " + arg3 + ')';
	}
}

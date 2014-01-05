package frmw.model.fun.math;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Random implements FormulaElement {

	private final FormulaElement lower;
	private final FormulaElement upper;

	public Random(FormulaElement lower, FormulaElement upper) {
		this.lower = lower;
		this.upper = upper;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.random(sb, lower, upper);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + lower + ", " + upper + ')';
	}
}

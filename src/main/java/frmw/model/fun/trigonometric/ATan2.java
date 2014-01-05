package frmw.model.fun.trigonometric;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class ATan2 implements FormulaElement {

	private final FormulaElement x;
	private final FormulaElement y;

	public ATan2(FormulaElement x, FormulaElement y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.aTan2(sb, x, y);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ", " + y + ')';
	}
}

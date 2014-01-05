package frmw.model.fun.dateTime;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class AddMonths implements FormulaElement {

	private final FormulaElement date;
	private final FormulaElement number;

	public AddMonths(FormulaElement date, FormulaElement number) {
		this.date = date;
		this.number = number;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.addMonths(sb, date, number);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + date + ", " + number + ')';
	}
}

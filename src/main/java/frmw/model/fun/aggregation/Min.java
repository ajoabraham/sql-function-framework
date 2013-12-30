package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Min extends Aggregation {

	private final FormulaElement column;

	public Min(FormulaElement column) {
		this.column = column;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.min(sb, column);
	}
}

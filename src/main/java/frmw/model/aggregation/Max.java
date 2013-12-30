package frmw.model.aggregation;

import frmw.dialect.Dialect;
import frmw.model.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Max extends Aggregation {

	private final FormulaElement column;

	public Max(FormulaElement column) {
		this.column = column;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.max(sb, column);
	}
}

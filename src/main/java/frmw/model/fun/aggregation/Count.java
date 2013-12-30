package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Count extends Aggregation {

	private final FormulaElement column;

	public Count(FormulaElement column) {
		this.column = column;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.count(sb, column);
	}
}

package frmw.model.aggregation;

import frmw.dialect.Dialect;
import frmw.model.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Avg extends Aggregation {

	private final FormulaElement column;

	public Avg(FormulaElement column) {
		this.column = column;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.avg(sb, column);
	}
}

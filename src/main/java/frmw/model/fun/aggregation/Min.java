package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Min extends Aggregation {

	public Min(FormulaElement column) {
		super(column);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.min(sb, column, distinct);
	}
}

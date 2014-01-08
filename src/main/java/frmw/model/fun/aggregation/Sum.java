package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Sum extends Aggregation {

	public Sum(FormulaElement column) {
		super(column);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.sum(sb, column, distinct);
	}
}

package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class StdDevP extends Aggregation {

	public StdDevP(FormulaElement column) {
		super(column);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.stdDevP(sb, column, distinct);
	}
}

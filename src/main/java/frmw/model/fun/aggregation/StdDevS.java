package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class StdDevS extends Aggregation {

	public StdDevS(FormulaElement column) {
		super(column);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.stdDevS(sb, column, distinct);
	}
}

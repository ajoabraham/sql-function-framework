package frmw.model.fun.aggregation;

import frmw.dialect.Dialect;
import frmw.model.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Avg extends Aggregation {

	public Avg(FormulaElement column) {
		super(column);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.avg(sb, column);
	}
}

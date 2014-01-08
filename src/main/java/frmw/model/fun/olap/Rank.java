package frmw.model.fun.olap;

import frmw.dialect.Dialect;
import frmw.model.fun.aggregation.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Rank extends Aggregation {

	public Rank(FormulaElement elem) {
		super(elem);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.rank(sb);
	}
}

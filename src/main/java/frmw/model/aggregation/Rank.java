package frmw.model.aggregation;

import frmw.dialect.Dialect;
import frmw.model.Aggregation;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class Rank extends Aggregation {

	public Rank(FormulaElement elem) {

	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.rank(sb);
	}
}

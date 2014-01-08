package frmw.model.fun.olap;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.Avg;

import static frmw.model.fun.olap.support.Rows.ALL;
import static frmw.model.fun.olap.support.Rows.CURRENT_ROW;

/**
 * @author Alexey Paramonov
 */
public class RunningAvg extends CustomWindow {

	public RunningAvg(FormulaElement elem) {
		super(new Avg(elem), ALL, CURRENT_ROW);
	}
}

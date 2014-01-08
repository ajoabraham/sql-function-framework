package frmw.model.fun.olap;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.Sum;

import static frmw.model.fun.olap.support.Rows.ALL;
import static frmw.model.fun.olap.support.Rows.CURRENT_ROW;

/**
 * @author Alexey Paramonov
 */
public class RunningSum extends CustomWindow {

	public RunningSum(FormulaElement elem) {
		super(new Sum(elem), ALL, CURRENT_ROW);
	}
}

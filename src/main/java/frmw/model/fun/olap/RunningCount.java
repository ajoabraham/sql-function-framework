package frmw.model.fun.olap;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.Count;

import static frmw.model.fun.olap.support.Rows.ALL;
import static frmw.model.fun.olap.support.Rows.CURRENT_ROW;

/**
 * @author Alexey Paramonov
 */
public class RunningCount extends CustomWindow {

	public RunningCount(FormulaElement elem) {
		super(new Count(elem), ALL, CURRENT_ROW);
	}
}

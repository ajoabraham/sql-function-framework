package frmw.model.fun.olap;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.Avg;
import frmw.model.fun.olap.support.Rows;

import java.math.BigInteger;

/**
 * @author Alexey Paramonov
 */
public class MovingAvg extends CustomWindow {

	public MovingAvg(FormulaElement elem, BigInteger rowNum) {
		super(new Avg(elem), new Rows(rowNum), null);
	}
}

package frmw.model.fun.olap;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.Count;
import frmw.model.fun.olap.support.Rows;

import java.math.BigInteger;

/**
 * @author Alexey Paramonov
 */
public class MovingCount extends CustomWindow {

	public MovingCount(FormulaElement elem, BigInteger rowNum) {
		super(new Count(elem), new Rows(rowNum), null);
	}
}

package frmw.model.fun.olap;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.Sum;
import frmw.model.fun.olap.support.Rows;

import java.math.BigInteger;

/**
 * @author Alexey Paramonov
 */
public class MovingSum extends CustomWindow {

	public MovingSum(FormulaElement elem, BigInteger rowNum) {
		super(new Sum(elem), new Rows(rowNum), null);
	}
}

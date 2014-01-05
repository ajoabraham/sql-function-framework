package frmw.model.fun.math;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar2;

/**
 * @author Alexey Paramonov
 */
public class Random extends Scalar2 {

	public Random(FormulaElement lower, FormulaElement upper) {
		super(lower, upper);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.random(sb, arg1, arg2);
	}
}

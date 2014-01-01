package frmw.model.fun.math;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class Sqrt extends Scalar {

	public Sqrt(FormulaElement arg) {
		super(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.sqrt(sb, arg);
	}
}

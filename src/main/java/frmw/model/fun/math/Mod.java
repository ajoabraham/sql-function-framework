package frmw.model.fun.math;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class Mod extends Scalar {

	public Mod(FormulaElement arg) {
		super(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.mod(sb, arg);
	}
}

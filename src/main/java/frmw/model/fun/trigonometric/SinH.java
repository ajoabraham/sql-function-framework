package frmw.model.fun.trigonometric;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class SinH extends Scalar {

	public SinH(FormulaElement arg) {
		super(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.sinH(sb, arg);
	}
}

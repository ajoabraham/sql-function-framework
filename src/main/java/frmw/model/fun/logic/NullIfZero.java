package frmw.model.fun.logic;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class NullIfZero extends Scalar {

	public NullIfZero(FormulaElement arg) {
		super(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.nullIfZero(sb, arg);
	}
}

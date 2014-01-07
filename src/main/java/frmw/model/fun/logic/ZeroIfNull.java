package frmw.model.fun.logic;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class ZeroIfNull extends Scalar {

	public ZeroIfNull(FormulaElement arg) {
		super(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.zeroIfNull(sb, arg);
	}
}

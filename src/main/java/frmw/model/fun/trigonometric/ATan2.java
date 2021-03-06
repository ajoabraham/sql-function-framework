package frmw.model.fun.trigonometric;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar2;

/**
 * @author Alexey Paramonov
 */
public class ATan2 extends Scalar2 {

	public ATan2(FormulaElement x, FormulaElement y) {
		super(x, y);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.aTan2(sb, arg1, arg2);
	}
}

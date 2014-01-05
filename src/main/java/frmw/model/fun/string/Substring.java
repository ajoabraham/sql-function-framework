package frmw.model.fun.string;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar3;

/**
 * @author Alexey Paramonov
 */
public class Substring extends Scalar3 {

	public Substring(FormulaElement str, FormulaElement start, FormulaElement length) {
		super(str, start, length);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.substring(sb, arg1, arg2, arg3);
	}
}

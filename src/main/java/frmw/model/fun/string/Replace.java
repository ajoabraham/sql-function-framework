package frmw.model.fun.string;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar3;

/**
 * @author Alexey Paramonov
 */
public class Replace extends Scalar3 {

	public Replace(FormulaElement str, FormulaElement search, FormulaElement replace) {
		super(str, search, replace);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.replace(sb, arg1, arg2, arg3);
	}
}

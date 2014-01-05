package frmw.model.fun.string;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar2;
import frmw.model.constant.StringConstant;

/**
 * @author Alexey Paramonov
 */
public class LeftTrim extends Scalar2 {

	public LeftTrim(FormulaElement str) {
		super(str, new StringConstant(" "));
	}

	public LeftTrim(FormulaElement str, FormulaElement trimmed) {
		super(str, trimmed);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.leftTrim(sb, arg1, arg2);
	}
}

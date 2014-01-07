package frmw.model.fun.logic;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar2;

/**
 * @author Alexey Paramonov
 */
public class NullIf extends Scalar2 {

	public NullIf(FormulaElement arg, FormulaElement nullable) {
		super(arg, nullable);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.nullIf(sb, arg1, arg2);
	}
}

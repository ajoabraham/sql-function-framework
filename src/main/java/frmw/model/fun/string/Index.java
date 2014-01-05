package frmw.model.fun.string;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar2;

/**
 * @author Alexey Paramonov
 */
public class Index extends Scalar2 {

	public Index(FormulaElement str, FormulaElement searched) {
		super(str, searched);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.index(sb, arg1, arg2);
	}
}

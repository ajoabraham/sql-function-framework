package frmw.model.fun.dateTime;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar2;

/**
 * @author Alexey Paramonov
 */
public class AddMonths extends Scalar2 {

	public AddMonths(FormulaElement date, FormulaElement number) {
		super(date, number);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.addMonths(sb, arg1, arg2);
	}
}

package frmw.model.fun.dateTime;

import frmw.dialect.DateTimeElement;
import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class Week extends Scalar {

	public Week(FormulaElement arg) {
		super(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.extractDateTime(sb, DateTimeElement.WEEK, arg);
	}
}

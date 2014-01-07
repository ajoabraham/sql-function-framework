package frmw.model.operator;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class Null extends Scalar {

	private final boolean isNull;

	public Null(FormulaElement arg, boolean isNull) {
		super(arg);
		this.isNull = isNull;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		arg.sql(dialect, sb);
		sb.append(" IS ");
		if (!isNull) {
			sb.append("NOT ");
		}
		sb.append("NULL");
	}
}

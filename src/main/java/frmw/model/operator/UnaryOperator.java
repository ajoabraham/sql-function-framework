package frmw.model.operator;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class UnaryOperator extends Scalar {

	private final String op;

	public UnaryOperator(FormulaElement arg, String op) {
		super(arg);
		this.op = op;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		sb.append(op);
		arg.sql(dialect, sb);
	}
}

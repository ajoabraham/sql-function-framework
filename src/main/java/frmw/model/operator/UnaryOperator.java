package frmw.model.operator;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.Scalar;

/**
 * @author Alexey Paramonov
 */
public class UnaryOperator extends Scalar {

	private final String op;

	private UnaryOperator(FormulaElement arg, String op) {
		super(arg);
		this.op = op;
	}

	public static UnaryOperator plus(FormulaElement arg) {
		return new UnaryOperator(arg, "");
	}

	public static UnaryOperator minus(FormulaElement arg) {
		return new UnaryOperator(arg, "-");
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		sb.append(op);
		arg.sql(dialect, sb);
	}

	@Override
	public String toString() {
		return op + arg;
	}
}

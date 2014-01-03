package frmw.model.constant;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class NumericConstant implements FormulaElement {

	private final String constant;

	public NumericConstant(String constant) {
		this.constant = constant;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		// do not check correctness of the input - just lets the database raise error
		sb.append(constant);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}
}

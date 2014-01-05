package frmw.model.constant;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

/**
 * User: aparamonov
 * Date: 03.01.14
 */
public class StringConstant implements FormulaElement {

	private final String constant;

	public StringConstant(String constant) {
		this.constant = constant;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		sb.append('\'').append(constant).append('\'');
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public String toString() {
		return "'" + constant + "'";
	}
}

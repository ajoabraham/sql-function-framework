package frmw.model.constant;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

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
	public void collectEntities(Set<String> set) {
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
	}

	@Override
	public String toString() {
		return "'" + constant + "'";
	}
}

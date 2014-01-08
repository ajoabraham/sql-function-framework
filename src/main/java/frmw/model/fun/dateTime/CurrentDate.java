package frmw.model.fun.dateTime;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class CurrentDate implements FormulaElement {

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.currentDate(sb);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public void collectEntities(Set<String> set) {
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}

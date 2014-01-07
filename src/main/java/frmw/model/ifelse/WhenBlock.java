package frmw.model.ifelse;

import frmw.model.FormulaElement;

/**
 * @author Alexey Paramonov
 */
public class WhenBlock {

	public final FormulaElement when;

	public final FormulaElement then;

	public WhenBlock(FormulaElement when, FormulaElement then) {
		this.when = when;
		this.then = then;
	}

	public boolean hasAggregation() {
		return when.hasAggregation() || then.hasAggregation();
	}
}

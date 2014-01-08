package frmw.model.ifelse;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class Case implements FormulaElement {

	public final List<WhenBlock> when;

	/**
	 * Nullable.
	 */
	public final FormulaElement elseBlock;

	public Case(List<WhenBlock> when, FormulaElement elseBlock) {
		this.when = when;
		this.elseBlock = elseBlock;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.searchedCase(sb, this);
	}

	@Override
	public boolean hasAggregation() {
		return whenHasAggregation() || (elseBlock != null && elseBlock.hasAggregation());
	}

	@Override
	public void collectEntities(Set<String> set) {
		for (WhenBlock block : when) {
			block.when.collectEntities(set);
			block.then.collectEntities(set);
		}

		if (elseBlock != null) {
			elseBlock.collectEntities(set);
		}
	}

	private boolean whenHasAggregation() {
		for (WhenBlock block : when) {
			if (block.hasAggregation()) {
				return true;
			}
		}

		return false;
	}
}

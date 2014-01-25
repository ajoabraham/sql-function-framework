package frmw.model.ifelse;

import frmw.dialect.Dialect;
import frmw.model.traverse.ColumnTraversal;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;

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
	public void traverseColumns(ColumnTraversal traversal) {
		for (WhenBlock block : when) {
			block.when.traverseColumns(traversal);
			block.then.traverseColumns(traversal);
		}

		if (elseBlock != null) {
			elseBlock.traverseColumns(traversal);
		}
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		for (WhenBlock block : when) {
			block.when.collectWindowParams(list);
			block.then.collectWindowParams(list);
		}

		if (elseBlock != null) {
			elseBlock.collectWindowParams(list);
		}
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		for (WhenBlock block : when) {
			block.when.collectAggregationParams(list);
			block.then.collectAggregationParams(list);
		}

		if (elseBlock != null) {
			elseBlock.collectAggregationParams(list);
		}
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		for (WhenBlock block : when) {
			block.when.collectRankParams(list);
			block.then.collectRankParams(list);
		}

		if (elseBlock != null) {
			elseBlock.collectRankParams(list);
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

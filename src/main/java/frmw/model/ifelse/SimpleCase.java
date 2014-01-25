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
public class SimpleCase extends Case {

	public final FormulaElement caseBlock;

	public SimpleCase(FormulaElement caseBlock, List<WhenBlock> when, FormulaElement elseBlock) {
		super(when, elseBlock);
		this.caseBlock = caseBlock;
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		super.traverseColumns(traversal);
		caseBlock.traverseColumns(traversal);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		super.collectWindowParams(list);
		caseBlock.collectWindowParams(list);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		super.collectAggregationParams(list);
		caseBlock.collectAggregationParams(list);
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		super.collectRankParams(list);
		caseBlock.collectRankParams(list);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.simpleCase(sb, this);
	}
}

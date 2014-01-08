package frmw.model.ifelse;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;
import java.util.Set;

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
	public void collectEntities(Set<String> set) {
		super.collectEntities(set);
		caseBlock.collectEntities(set);
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
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.simpleCase(sb, this);
	}
}

package frmw.model.ifelse;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

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
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.simpleCase(sb, this);
	}
}

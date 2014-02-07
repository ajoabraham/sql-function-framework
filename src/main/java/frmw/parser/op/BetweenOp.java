package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.ConditionalOperators;
import org.codehaus.jparsec.functors.Map5;

/**
 * @author Alexey Paramonov
 */
public class BetweenOp implements Map5<FormulaElement, Void, FormulaElement, Void, FormulaElement, FormulaElement> {

	public static final BetweenOp INST = new BetweenOp();

	@Override
	public FormulaElement map(FormulaElement o0, Void o, FormulaElement o2, Void o3, FormulaElement o4) {
		return ConditionalOperators.between(o0, o2, o4);
	}
}

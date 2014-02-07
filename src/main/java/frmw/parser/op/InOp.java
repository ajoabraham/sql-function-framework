package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.InOperator;
import org.codehaus.jparsec.functors.Map3;

import java.util.List;

/**
* @author Alexey Paramonov
*/
public class InOp implements Map3<FormulaElement, Void, List<FormulaElement>, FormulaElement> {

	public static final InOp INST = new InOp();

	@Override
	public FormulaElement map(FormulaElement tested, Void aVoid, List<FormulaElement> in) {
		return new InOperator(tested, in);
	}
}

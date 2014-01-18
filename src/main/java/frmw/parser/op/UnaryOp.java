package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.UnaryOperator;
import org.codehaus.jparsec.functors.Unary;

/**
* @author Alexey Paramonov
*/
public enum UnaryOp implements Unary<FormulaElement> {
	NEG {
		public FormulaElement map(FormulaElement n) {
			return UnaryOperator.minus(n);
		}
	},
	PLUS {
		public FormulaElement map(FormulaElement n) {
			return UnaryOperator.plus(n);
		}
	}
}

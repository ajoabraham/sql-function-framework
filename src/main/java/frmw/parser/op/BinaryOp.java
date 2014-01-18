package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.And;
import frmw.model.operator.AnotherOperators;
import frmw.model.operator.ArithmeticOperators;
import frmw.model.operator.Or;
import org.codehaus.jparsec.functors.Binary;

/**
* @author Alexey Paramonov
*/
public enum BinaryOp implements Binary<FormulaElement> {
	PLUS {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return ArithmeticOperators.plus(a, b);
		}
	},
	MINUS {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return ArithmeticOperators.minus(a, b);
		}
	},
	MUL {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return ArithmeticOperators.multiple(a, b);
		}
	},
	DIV {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return ArithmeticOperators.divide(a, b);
		}
	},
	CONCAT {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return AnotherOperators.concat(a, b);
		}
	},
	AND {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return new And(a, b);
		}
	},
	OR {
		public FormulaElement map(FormulaElement a, FormulaElement b) {
			return new Or(a, b);
		}
	}
}

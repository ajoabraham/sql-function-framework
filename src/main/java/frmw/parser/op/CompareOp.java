package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.CompareOperations;
import org.codehaus.jparsec.functors.Map3;

/**
* @author Alexey Paramonov
*/
public enum CompareOp implements Map3<FormulaElement, Void, FormulaElement, FormulaElement> {
	EQUAL {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return CompareOperations.eq(a, b);
		}
	},
	GREAT {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return CompareOperations.gt(a, b);
		}
	},
	LESS {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return CompareOperations.lt(a, b);
		}
	},
	EQUAL_GREAT {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return CompareOperations.ge(a, b);
		}
	},
	EQUAL_LESS {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return CompareOperations.le(a, b);
		}
	},
	NOT_EQUAL {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return CompareOperations.ne(a, b);
		}
	}
}

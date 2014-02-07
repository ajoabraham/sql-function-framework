package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.ConditionalOperators;
import org.codehaus.jparsec.functors.Map3;

/**
* @author Alexey Paramonov
*/
public enum CompareOp implements Map3<FormulaElement, Void, FormulaElement, FormulaElement> {
	EQUAL {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return ConditionalOperators.eq(a, b);
		}
	},
	GREAT {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return ConditionalOperators.gt(a, b);
		}
	},
	LESS {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return ConditionalOperators.lt(a, b);
		}
	},
	EQUAL_GREAT {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return ConditionalOperators.ge(a, b);
		}
	},
	EQUAL_LESS {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return ConditionalOperators.le(a, b);
		}
	},
	NOT_EQUAL {
		public FormulaElement map(FormulaElement a, Void v, FormulaElement b) {
			return ConditionalOperators.ne(a, b);
		}
	}
}

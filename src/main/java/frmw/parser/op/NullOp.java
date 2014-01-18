package frmw.parser.op;

import frmw.model.FormulaElement;
import frmw.model.operator.Null;
import org.codehaus.jparsec.functors.Map2;

/**
* @author Alexey Paramonov
*/
public enum NullOp implements Map2<FormulaElement, Void, FormulaElement> {

	NULL {
		public FormulaElement map(FormulaElement n, Void v) {
			return new Null(n, true);
		}
	},
	NOT_NULL {
		public FormulaElement map(FormulaElement n, Void v) {
			return new Null(n, false);
		}
	}
}

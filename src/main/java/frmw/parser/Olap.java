package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.olap.*;
import frmw.model.fun.olap.support.Rows;
import org.codehaus.jparsec.Parser;

import static frmw.parser.Common.INTEGER_PARSER;
import static frmw.parser.Common.withOperators;
import static org.codehaus.jparsec.Parsers.or;

/**
 * @author Alexey Paramonov
 */
class Olap extends FunctionBuilder {

	Olap(Parser<FormulaElement> aggregations, Parser<FormulaElement> scalar, Parser<FormulaElement> common) {
		Parser<FormulaElement> withScalars = withOperators(or(scalar, common));
		Parser<FormulaElement> all = withOperators(or(aggregations, scalar, common));

		f(Rank.class, all);
		f(MovingAvg.class, withScalars, INTEGER_PARSER);
		f(MovingCount.class, withScalars, INTEGER_PARSER);
		f(MovingSum.class, withScalars, INTEGER_PARSER);
		f(RunningAvg.class, withScalars);
		f(RunningCount.class, withScalars);
		f(RunningSum.class, withScalars);
		customWindow(aggregations);
	}

	private void customWindow(Parser<FormulaElement> aggregations) {
		Parser<Rows> rows = Common.rows();
		f(CustomWindow.class, aggregations, rows, rows);
	}
}

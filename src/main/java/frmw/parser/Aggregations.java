package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.*;
import org.codehaus.jparsec.Parser;

import static frmw.parser.Common.withOperators;
import static org.codehaus.jparsec.Parsers.or;

/**
 * @author Alexey Paramonov
 */
class Aggregations extends FunctionBuilder {

	Aggregations(Parser<FormulaElement> scalar, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = withOperators(or(scalar, common));

		f(Avg.class, all);
		f(Sum.class, all);
		f(Count.class, all);
		f(Min.class, all);
		f(Max.class, all);
		f(StdDevP.class, all);
		f(StdDevS.class, all);
	}
}

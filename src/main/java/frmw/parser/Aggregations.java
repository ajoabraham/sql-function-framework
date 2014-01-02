package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.*;
import frmw.model.fun.olap.Rank;
import org.codehaus.jparsec.Parser;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.*;
import static org.codehaus.jparsec.Parsers.or;

/**
 * @author Alexey Paramonov
 */
class Aggregations {

	public final List<String> names = new ArrayList<String>();

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	Aggregations(Parser<FormulaElement> scalar, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = withOperators(or(scalar, common));

		aggregation(all);
		olap(all);
	}

	private void olap(Parser<FormulaElement> all) {
		f(Rank.class, all);
	}

	private void aggregation(Parser<FormulaElement> all) {
		f(Avg.class, all);
		f(Sum.class, all);
		f(Count.class, all);
		f(Min.class, all);
		f(Max.class, all);
		f(StdDevP.class, all);
		f(StdDevS.class, all);
	}

	private void f(Class<? extends FormulaElement> clazz, Parser<?> arg) {
		Parser<FormulaElement> result = fun(clazz, arg);
		parsers.add(result);
		names.add(funName(clazz));
	}
}

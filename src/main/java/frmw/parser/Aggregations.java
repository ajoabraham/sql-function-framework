package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.*;
import frmw.model.fun.olap.*;
import frmw.model.fun.olap.support.Rows;
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

	private final List<Parser<FormulaElement>> simpleAggregations = new ArrayList<Parser<FormulaElement>>();

	Aggregations(Parser<FormulaElement> scalar, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = withOperators(or(scalar, common));

		aggregation(all);
		olap(all);
	}

	private void olap(Parser<FormulaElement> all) {
		f(Rank.class, all);
		f(MovingAvg.class, all, INTEGER_PARSER);
		f(MovingCount.class, all, INTEGER_PARSER);
		f(MovingSum.class, all, INTEGER_PARSER);
		f(RunningAvg.class, all);
		f(RunningCount.class, all);
		f(RunningSum.class, all);
		customWindow(all);
	}

	private void customWindow(Parser<FormulaElement> all) {
		Parser<Rows> rows = Common.rows();
		f(CustomWindow.class, or(simpleAggregations), rows, rows);
	}

	private void aggregation(Parser<FormulaElement> all) {
		aggr(Avg.class, all);
		aggr(Sum.class, all);
		aggr(Count.class, all);
		aggr(Min.class, all);
		aggr(Max.class, all);
		aggr(StdDevP.class, all);
		aggr(StdDevS.class, all);
	}

	private void aggr(Class<? extends FormulaElement> clazz, Parser<?> ...args) {
		Parser<FormulaElement> res = f(clazz, args);
		simpleAggregations.add(res);
	}

	private Parser<FormulaElement> f(Class<? extends FormulaElement> clazz, Parser<?> ...args) {
		Parser<FormulaElement> result = fun(clazz, args);
		parsers.add(result);
		names.add(funName(clazz));
		return result;
	}
}

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

	public List<String> names = new ArrayList<String>();

	public List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	Aggregations(Parser<FormulaElement> scalar) {
		f(Avg.class, or(COLUMN, scalar));
		f(Sum.class, or(COLUMN, scalar));
		f(Count.class, or(COLUMN, scalar));
		f(Min.class, or(COLUMN, scalar));
		f(Max.class, or(COLUMN, scalar));
		f(Rank.class, or(COLUMN, scalar));
	}

	private void f(Class<? extends FormulaElement> clazz, Parser<?> arg) {
		Parser<FormulaElement> result = fun(clazz, arg);
		parsers.add(result);
		names.add(funName(clazz));
	}
}

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
		f(Avg.class, or(scalar, common));
		f(Sum.class, or(scalar, common));
		f(Count.class, or(scalar, common));
		f(Min.class, or(scalar, common));
		f(Max.class, or(scalar, common));
		f(Rank.class, or(scalar, common));
	}

	private void f(Class<? extends FormulaElement> clazz, Parser<?> arg) {
		Parser<FormulaElement> result = fun(clazz, arg);
		parsers.add(result);
		names.add(funName(clazz));
	}
}

package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.math.*;
import org.codehaus.jparsec.Parser;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.withOperators;
import static frmw.parser.Common.fun;
import static frmw.parser.Common.funName;
import static org.codehaus.jparsec.Parsers.or;

/**
 * @author Alexey Paramonov
 */
class Scalars {

	public final List<String> names = new ArrayList<String>();

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	Scalars(Parser<FormulaElement> scalar, Parser<FormulaElement> aggregation, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = withOperators(or(aggregation, scalar, common));
		math(all);
		trigonometric(all);
	}

	private void trigonometric(Parser<FormulaElement> all) {

	}

	private void math(Parser<FormulaElement> all) {
		f(Abs.class, all);
		f(Exp.class, all);
		f(Ln.class, all);
		f(Log.class, all);
		f(Mod.class, all);
		f(Pow.class, all);
		f(Round.class, all);
		f(Sqrt.class, all);
	}

	private void f(Class<? extends FormulaElement> clazz, Parser<?> arg) {
		Parser<FormulaElement> result = fun(clazz, arg);
		parsers.add(result);
		names.add(funName(clazz));
	}
}

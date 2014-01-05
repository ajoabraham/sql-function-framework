package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.dateTime.*;
import frmw.model.fun.math.*;
import frmw.model.fun.string.*;
import frmw.model.fun.trigonometric.*;
import org.codehaus.jparsec.Parser;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.*;
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
		string(all);
		dateTime(all);
	}

	private void dateTime(Parser<FormulaElement> all) {
		f(Year.class, all);
		f(Month.class, all);
		f(Day.class, all);
		f(Week.class, all);
		f(Hour.class, all);
		f(Minute.class, all);
		f(Second.class, all);
		f(CurrentDate.class);
		f(CurrentTimestamp.class);
		f(AddMonths.class, all, all);
	}

	private void string(Parser<FormulaElement> all) {
		f(Trim.class, all);
		f(LeftTrim.class, all);
		f(LeftTrim.class, all, all);
		f(RightTrim.class, all);
		f(RightTrim.class, all, all);
		f(Upper.class, all);
		f(Lower.class, all);
		f(Index.class, all, all);
		f(Substring.class, all, all, all);
		f(Replace.class, all, all, all);
	}

	private void trigonometric(Parser<FormulaElement> all) {
		f(Sin.class, all);
		f(Cos.class, all);
		f(Tan.class, all);

		f(SinH.class, all);
		f(CosH.class, all);
		f(TanH.class, all);

		f(ASin.class, all);
		f(ACos.class, all);
		f(ATan.class, all);
		f(ATan2.class, all, all);

		f(ASinH.class, all);
		f(ACosH.class, all);
		f(ATanH.class, all);
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
		f(Random.class, all, all);
	}

	private void f(Class<? extends FormulaElement> clazz, Parser<?> ...args) {
		Parser<FormulaElement> result = fun(clazz, args);
		parsers.add(result);
		names.add(funName(clazz));
	}
}

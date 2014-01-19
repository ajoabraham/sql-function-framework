package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.dateTime.*;
import frmw.model.fun.logic.NullIf;
import frmw.model.fun.logic.NullIfZero;
import frmw.model.fun.logic.ZeroIfNull;
import frmw.model.fun.math.*;
import frmw.model.fun.string.*;
import frmw.model.fun.trigonometric.*;
import org.codehaus.jparsec.Parser;

import static frmw.parser.Common.withOperators;
import static org.codehaus.jparsec.Parsers.or;

/**
 * @author Alexey Paramonov
 */
class Scalars extends FunctionBuilder {

	Scalars(Parser<FormulaElement> scalar, Parser<FormulaElement> aggregation, Parser<FormulaElement> olap, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = withOperators(or(aggregation, scalar, common, olap));

		math(all);
		trigonometric(all);
		string(all);
		dateTime(all);
		logic(all);
	}

	private void logic(Parser<FormulaElement> all) {
		f(NullIf.class, all, arg(all, EXPR));
		f(NullIfZero.class, all);
		f(ZeroIfNull.class, all);
	}

	private void dateTime(Parser<FormulaElement> all) {
		Arg date = arg(all, "date_expression");

		f(Year.class, date);
		f(Month.class, date);
		f(Day.class, date);
		f(Week.class, date);
		f(Hour.class, date);
		f(Minute.class, date);
		f(Second.class, date);
		f(CurrentDate.class);
		f(CurrentTimestamp.class);
		f(AddMonths.class, date, arg(all, "number_of_months"));
	}

	private void string(Parser<FormulaElement> all) {
		f(Trim.class, all);
		f(LeftTrim.class, all);
		f(LeftTrim.class, all, arg(all, "trim_character"));
		f(RightTrim.class, all);
		f(RightTrim.class, all, arg(all, "trim_character"));
		f(Upper.class, all);
		f(Lower.class, all);
		f(Index.class, all, arg(all, "substring"));
		f(Substring.class, all, arg(all, "starting_position"), arg(all, "length"));
		f(Replace.class, arg(all, "source_expression"), arg(all, "search_expression"), arg(all, "replace_expression"));
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
		f(ATan2.class, arg(all, "x"), arg(all, "y"));

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
		f(Random.class, arg(all, "lower_bound"), arg(all, "upper_bound"));
	}
}

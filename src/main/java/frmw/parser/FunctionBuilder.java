package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.hint.FunctionSpec;
import org.codehaus.jparsec.Parser;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.fun;

/**
 * @author Alexey Paramonov
 */
class FunctionBuilder {

	public static final String EXPR = "expression";

	public final List<FunctionSpec> specs = new ArrayList<FunctionSpec>();

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	Parser<FormulaElement> f(Class<? extends FormulaElement> clazz) {
		return f(clazz, (Arg) null);
	}

	Parser<FormulaElement> f(Class<? extends FormulaElement> clazz, Parser<?> expr, Arg ...args) {
		return f(clazz, arg(expr, EXPR), args);
	}

	Parser<FormulaElement> f(Class<? extends FormulaElement> clazz, Arg arg1, Arg ...args) {
		List<Parser<?>> parserArg = new ArrayList<Parser<?>>();
		if (arg1 != null) {
			parserArg.add(arg1.arg);
			for (Arg arg : args) {
				parserArg.add(arg.arg);
			}
		}

		FunctionSpec spec = spec(clazz, arg1, args);
		Parser<FormulaElement> result = fun(spec, parserArg);
		parsers.add(result);
		specs.add(spec);
		return result;
	}

	private FunctionSpec spec(Class<? extends FormulaElement> clazz, Arg arg1, Arg ...args) {
		List<String> hints = new ArrayList<String>();
		if (arg1 != null) {
			hints.add(arg1.hint);
			for (Arg arg : args) {
				hints.add(arg.hint);
			}
		}

		return new FunctionSpec(clazz, hints);
	}

	static Arg arg(Parser<?> arg, String hint) {
		return new Arg(arg, hint);
	}

	static final class Arg {

		public final String hint;

		public final Parser<?> arg;

		Arg(Parser<?> arg, String hint) {
			this.hint = hint;
			this.arg = arg;
		}
	}
}

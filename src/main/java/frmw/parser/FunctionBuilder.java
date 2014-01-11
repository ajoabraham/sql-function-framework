package frmw.parser;

import frmw.model.FormulaElement;
import org.codehaus.jparsec.Parser;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.fun;
import static frmw.parser.Common.funName;

/**
 * @author Alexey Paramonov
 */
class FunctionBuilder {

	public final List<String> names = new ArrayList<String>();

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	Parser<FormulaElement> f(Class<? extends FormulaElement> clazz, Parser<?> ...args) {
		Parser<FormulaElement> result = fun(clazz, args);
		parsers.add(result);
		names.add(funName(clazz));
		return result;
	}
}

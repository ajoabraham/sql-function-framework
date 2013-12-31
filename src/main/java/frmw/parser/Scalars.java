package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.fun.math.Abs;
import org.codehaus.jparsec.Parser;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.*;
import static org.codehaus.jparsec.Parsers.or;

/**
 * @author Alexey Paramonov
 */
class Scalars {

	public List<String> names = new ArrayList<String>();

	public List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	Scalars(Parser<FormulaElement> scalar, Parser<FormulaElement> aggregation) {
		f(Abs.class, or(scalar, aggregation, COLUMN));
	}

	private void f(Class<? extends FormulaElement> clazz, Parser<?> arg) {
		Parser<FormulaElement> result = fun(clazz, arg);
		parsers.add(result);
		names.add(funName(clazz));
	}
}

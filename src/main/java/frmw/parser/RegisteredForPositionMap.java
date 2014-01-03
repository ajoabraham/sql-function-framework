package frmw.parser;

import frmw.model.FormulaElement;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.functors.Map;

import static frmw.model.position.ProvidingPositionsOnExceptionAspect.currentFormula;

/**
 * @author Alexey Paramonov
 */
abstract class RegisteredForPositionMap <T extends FormulaElement> implements Map<Token, T> {

	@SuppressWarnings("unchecked")
	@Override
	public T map(Token t) {
		FormulaElement res = build(t.value());
		currentFormula.get().register(res, t.index(), t.length());
		return (T) res;
	}

	protected abstract FormulaElement build(Object value);
}

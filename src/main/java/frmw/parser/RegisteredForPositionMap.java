package frmw.parser;

import frmw.model.FormulaElement;
import frmw.model.PositionAware;
import frmw.model.exception.SQLFrameworkException;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.functors.Map;

import static frmw.model.exception.SQLFrameworkInternalException.wrap;
import static frmw.model.position.ProvidingPositionsOnExceptionAspect.currentFormula;

/**
 * @author Alexey Paramonov
 */
abstract class RegisteredForPositionMap<R extends FormulaElement, A> implements Map<Token, R> {

	@SuppressWarnings("unchecked")
	@Override
	public R map(Token t) {
		try {
			FormulaElement res = build((A) t.value());
			currentFormula.get().register(res, t.index(), t.length());
			if (res instanceof PositionAware) {
				((PositionAware) res).position(t.index(), t.length());
			}

			return (R) res;
		} catch (Exception e) {
			SQLFrameworkException ex = wrap(e);
			ex.position(t.index(), t.length());
			throw ex;
		}
	}

	protected abstract FormulaElement build(A result) throws Exception;
}

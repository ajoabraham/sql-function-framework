package frmw.model.position;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.PositionProvider;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.exception.UnsupportedFunctionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import static frmw.model.exception.SQLFrameworkException.wrap;

/**
 * @author Alexey Paramonov
 */
@Aspect
public class ProvidingPositionsOnExceptionAspect {

	public final static ThreadLocal<PositionMap> currentFormulaPositions = new ThreadLocal<PositionMap>();

	@Pointcut("execution(* frmw.model.PositionProvider+.elementPositions(..))")
	public void elementPositions() {
	}

	@Pointcut("execution(* frmw.model.PositionProvider+.*(..))")
	public void positionProvider() {
	}

	@Pointcut("execution(* frmw.model.FormulaElement+.*(..))")
	public void formulaElement() {
	}

	@Pointcut("call(* frmw.dialect.Dialect+.*(..))")
	public void dialect() {
	}

	@Around("cflow(positionProvider()) && formulaElement() && this(e)")
	public Object providePosition(ProceedingJoinPoint pjp, FormulaElement e) {
		try {
			return pjp.proceed();
		} catch (Throwable t) {
			if (t instanceof Error) {
				throw (Error) t;
			}

			SQLFrameworkException ex = wrap(t);
			savePosition(e, ex);
			throw ex;
		}
	}

	private void savePosition(FormulaElement e, SQLFrameworkException ex) {
		Position pos = currentFormulaPositions.get().find(e);
		if (pos != null && !ex.positionSet()) {
			ex.position(pos.index, pos.length);
		}
	}

	@Before("positionProvider() && !elementPositions() && this(p)")
	public void onFormulaCall(PositionProvider p) {
		currentFormulaPositions.set(p.elementPositions());
	}

	@Around("dialect() && this(e) && target(d)")
	public Object convertUnsupportedOperation(ProceedingJoinPoint pjp, FormulaElement e, Dialect d) {
		try {
			return pjp.proceed();
		} catch (Throwable t) {
			if (t instanceof Error) {
				throw (Error) t;
			}

			if (t instanceof UnsupportedOperationException) {
				String function = e.getClass().getSimpleName();
				String dialect = d.getClass().getSimpleName();
				throw new UnsupportedFunctionException(function, dialect);
			}

			throw wrap(t);
		}
	}
}

package frmw.model.position;

import frmw.dialect.Dialect;
import frmw.model.Formula;
import frmw.model.FormulaElement;
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

	private final static ThreadLocal<Formula> currentFormula = new ThreadLocal<Formula>();

	@Pointcut("execution(* frmw.model.Formula+.*(..))")
	public void formula() {
	}

	@Pointcut("execution(* frmw.model.FormulaElement+.*(..))")
	public void formulaElement() {
	}

	@Pointcut("call(* frmw.dialect.Dialect+.*(..))")
	public void dialect() {
	}

	@Around("cflow(formula()) && formulaElement() && this(e)")
	public Object providePosition(ProceedingJoinPoint pjp, FormulaElement e) {
		try {
			return pjp.proceed();
		} catch (Throwable t) {
			if (t instanceof Error) {
				throw (Error) t;
			}

			SQLFrameworkException ex = wrap(t);
			PositionMap.Position pos = PositionHolder.INST.position(currentFormula.get(), e);
			if (pos != null && !ex.positionSet()) {
				ex.position(pos.index, pos.length);
			}
			throw ex;
		}
	}

	@Before("formula() && this(f)")
	public void onFormulaCall(Formula f) {
		currentFormula.set(f);
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

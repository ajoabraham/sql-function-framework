package org.codehaus.jparsec;

import java.util.List;

/**
 * If we do not use this, {@link frmw.parser.Common#column()} parser ignores possible function names in error output.
 *
 * @author Alexey Paramonov
 */
public class ProcessIllegalFunctionNameHandling<T> extends Parser<T> {

	private final Parser<T> p;

	ProcessIllegalFunctionNameHandling(Parser<T> p) {
		this.p = p;
	}

	public static <T> Parser<T> suppress(Parser<T> p) {
		return new ProcessIllegalFunctionNameHandling<T>(p);
	}

	@Override
	boolean apply(ParseContext ctxt) {
		ParseContext fake = new ScannerState(ctxt.module, ctxt.source, ctxt.at, ctxt.locator);
		fake.step = ctxt.step;

		boolean r = p.run(fake);
		List<Object> errors = fake.errors();
		if (!errors.isEmpty() && "(".equals(fake.getEncountered())) {
			ctxt.at = ctxt.errorIndex();
			for (Object error : errors) {
				ctxt.raise(fake.errorType(), error);
			}
		} else {
			ctxt.set(fake.step, fake.at, fake.result);
		}

		return r;
	}

	@Override
	public String toString() {
		return p.toString();
	}
}

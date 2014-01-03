package org.codehaus.jparsec;

/**
 * @author Alexey Paramonov
 */
public final class StringLiteralScanner extends Parser<Void> {

	private final String name;

	StringLiteralScanner(String name) {
		this.name = name;
	}

	public static Parser<Void> literal(String name) {
		return new StringLiteralScanner(name);
	}

	@Override
	boolean apply(ParseContext ctxt) {
		if (ctxt.isEof()) {
			ctxt.expected(name);
			return false;
		}

		char c = ctxt.peekChar();
		if (c != '\'') {
			ctxt.next();
			ctxt.result = null;
			return true;
		}

		ctxt.next();
		if (!ctxt.isEof()) {
			char nextC = ctxt.peekChar();
			if (nextC == '\'') {
				// escaped
				ctxt.next();
				ctxt.result = null;
				return true;
			} else {
				reset(ctxt);
			}
		} else {
			reset(ctxt);
		}

		ctxt.expected(name);
		return false;
	}

	private void reset(ParseContext ctxt) {
		ctxt.step--;
		ctxt.at--;
	}

	@Override
	public String toString() {
		return name;
	}
}
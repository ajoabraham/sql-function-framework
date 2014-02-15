package org.codehaus.jparsec;

/**
 * @author Alexey Paramonov
 */
public final class StringLiteralScanner extends Parser<String> {

	private final String name;

	StringLiteralScanner(String name) {
		this.name = name;
	}

	public static Parser<String> literal(String name) {
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
			ctxt.expected(name);
			return false;
		}

		StringBuilder result = new StringBuilder();

		ctxt.next();
		while (!ctxt.isEof()) {
			c = ctxt.peekChar();
			ctxt.next();

			if (c == '\'') {
				if (nextCharIsSingleQuote(ctxt)) {
					// escaped
					ctxt.next();
					result.append("''");
					continue;
				}

				ctxt.result = result.toString();
				return true;
			} else {
				result.append(c);
			}
		}

		ctxt.expected(name);
		return false;
	}

	private boolean nextCharIsSingleQuote(ParseContext ctxt) {
		return !ctxt.isEof() && ctxt.peekChar() == '\'';
	}

	@Override
	public String toString() {
		return name;
	}
}
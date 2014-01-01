package frmw.parser;

import frmw.model.Column;
import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.pattern.CharPredicate;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.codehaus.jparsec.Parsers.EOF;
import static org.codehaus.jparsec.Parsers.or;
import static org.codehaus.jparsec.Scanners.*;

/**
 * @author Alexey Paramonov
 */
public class Common {

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	public static final Parser<Void> TRAILED = WHITESPACES.skipAtLeast(0);

	public static final Parser<?> COMMA = trailed(isChar(','));
	public static final Parser<Void> OPENED = trailed(isChar('('));
	public static final Parser<Void> CLOSED = trailed(isChar(')'));

	public static final Parser<Void> DQ = trailed(isChar('"'));
	public static final Parser<Void> SQ = trailed(isChar('\''));

	public static final String COLUMN_NAME_ID = "COLUMN_NAME";

	public Common() {
		parsers.add(column());
	}

	/**
	 * This parser should be last parser in any sequence of parsers.
	 */
	private Parser<FormulaElement> column() {
		Parser<String> quoted = isChar(new CharPredicate() {
			@Override
			public boolean isChar(char c) {
				return c != '"';
			}
		}, COLUMN_NAME_ID).many1().source().between(DQ, DQ);

		Parser<String> plain = or(EOF.cast(), isChar(new CharPredicate() {
			@Override
			public boolean isChar(char c) {
				return c != '(' && c != ')' && c != ',' &&
						c != '/' && c != '+' && c != '-';
			}
		}, COLUMN_NAME_ID)).many1().source();

		return or(quoted, plain).token().map(new RegisteredForPositionMap<FormulaElement>() {
			@Override
			protected FormulaElement build(Object value) {
				return new Column((String) value);
			}
		});
	}

	public static <T> Parser<T> trailed(Parser<T> parser) {
		return parser.between(TRAILED, TRAILED);
	}

	public static <T extends FormulaElement> Parser<T> fun(final Class<? extends T> clazz, Parser<?> arg) {
		Parser<?> body = arg.between(OPENED, CLOSED);
		Parser<Void> functionName = trailed(stringCaseInsensitive(funName(clazz)));
		return functionName.next(body).token().map(new RegisteredForPositionMap<T>() {
			@Override
			protected FormulaElement build(Object value) {
				Constructor<?> constructor = clazz.getConstructors()[0];
				try {
					return (FormulaElement) constructor.newInstance(value);
				} catch (Exception e) {
					throw new SQLFrameworkException(e);
				}
			}
		});
	}

	public static <T extends FormulaElement> String funName(Class<T> clazz) {
		return clazz.getSimpleName();
	}
}

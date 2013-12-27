package frmw.parser;

import frmw.model.Column;
import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.pattern.CharPredicate;

import java.lang.reflect.Constructor;

import static org.codehaus.jparsec.Scanners.*;

/**
 * @author Alexey Paramonov
 */
class Common {

	public static final Parser<Void> TRAILED = WHITESPACES.skipAtLeast(0);

	public static final Parser<?> COMMA = trailed(isChar(','));
	public static final Parser<Void> OPENED = trailed(isChar('('));
	public static final Parser<Void> CLOSED = trailed(isChar(')'));

	public static final Parser<Void> DQ = trailed(isChar('"'));
	public static final Parser<Void> SQ = trailed(isChar('\''));

	public static final Parser<String> COLUMN_NAME = isChar(new CharPredicate() {
		@Override
		public boolean isChar(char c) {
			return c != '"';
		}
	}, "word").many().source();

	public static final Parser<FormulaElement> COLUMN = COLUMN_NAME.between(DQ, DQ).token().map(new RegisteredForPositionMap() {
		@Override
		protected FormulaElement build(Object value) {
			return new Column((String) value);
		}
	});

	public static <T> Parser<T> trailed(Parser<T> parser) {
		return parser.between(TRAILED, TRAILED);
	}

	public static Parser<FormulaElement> fun(final Class<? extends FormulaElement> clazz, Parser<?> arg) {
		Parser<?> body = arg.between(OPENED, CLOSED);
		Parser<Void> functionName = trailed(stringCaseInsensitive(clazz.getSimpleName()));
		return functionName.next(body).token().map(new RegisteredForPositionMap() {
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

	public static Parser<Column> scalar() {
		return Parsers.always();
	}
}

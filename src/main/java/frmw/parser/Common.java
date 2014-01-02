package frmw.parser;

import frmw.model.Column;
import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.fun.math.operator.BinaryOperator;
import frmw.model.fun.math.operator.UnaryOperator;
import org.codehaus.jparsec.OperatorTable;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.functors.Binary;
import org.codehaus.jparsec.functors.Unary;
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
class Common {

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	public static final Parser<Void> TRAILED = WHITESPACES.skipAtLeast(0);

	public static final Parser<?> COMMA = trailed(isChar(','));
	public static final Parser<Void> OPENED = trailed(isChar('('));
	public static final Parser<Void> CLOSED = trailed(isChar(')'));

	public static final Parser<Void> DQ = trailed(isChar('"'));
	public static final Parser<Void> SQ = trailed(isChar('\''));

	public static final Parser<Void> PLUS = trailed(isChar('+'));
	public static final Parser<Void> MINUS = trailed(isChar('-'));
	public static final Parser<Void> MUL = trailed(isChar('*'));
	public static final Parser<Void> DIV = trailed(isChar('/'));

	public static final String COLUMN_NAME_ID = "COLUMN_NAME";

	public Common(Parser<FormulaElement> scalar, Parser<FormulaElement> aggregation, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = or(scalar, aggregation, column());

		parsers.add(column());
	}

	public static Parser<FormulaElement> withOperators(Parser<FormulaElement> all) {
		Parser.Reference<FormulaElement> ref = Parser.newReference();
		Parser<FormulaElement> unit = ref.lazy().between(OPENED, CLOSED).or(all);
		Parser<FormulaElement> parser = new OperatorTable<FormulaElement>()
				.infixl(op(PLUS, BinaryOp.PLUS), 10)
				.infixl(op(MINUS, BinaryOp.MINUS), 10)
				.infixl(op(MUL, BinaryOp.MUL), 20)
				.infixl(op(DIV, BinaryOp.DIV), 20)
				.prefix(op(MINUS, UnaryOp.NEG), 30)
				.prefix(op(PLUS, UnaryOp.PLUS), 30)
				.build(unit);
		ref.set(parser);
		return trailed(parser);
	}

	private static <T> Parser<T> op(Parser<Void> p, T value) {
		return p.retn(value);
	}

	enum BinaryOp implements Binary<FormulaElement> {
		PLUS {
			public FormulaElement map(FormulaElement a, FormulaElement b) {
				return new BinaryOperator(a, b, "+");
			}
		},
		MINUS {
			public FormulaElement map(FormulaElement a, FormulaElement b) {
				return new BinaryOperator(a, b, "-");
			}
		},
		MUL {
			public FormulaElement map(FormulaElement a, FormulaElement b) {
				return new BinaryOperator(a, b, "*");
			}
		},
		DIV {
			public FormulaElement map(FormulaElement a, FormulaElement b) {
				return new BinaryOperator(a, b, "/");
			}
		}
	}

	enum UnaryOp implements Unary<FormulaElement> {
		NEG {
			public FormulaElement map(FormulaElement n) {
				return new UnaryOperator(n, "-");
			}
		},
		PLUS {
			public FormulaElement map(FormulaElement n) {
				return new UnaryOperator(n, "");
			}
		}
	}

	/**
	 * This parser should be last parser in any sequence of parsers.
	 */
	public static Parser<FormulaElement> column() {
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
						c != '*' && c != '/' && c != '+' && c != '-';
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

package frmw.parser;

import frmw.model.Column;
import frmw.model.FormulaElement;
import frmw.model.constant.NumericConstant;
import frmw.model.constant.StringConstant;
import frmw.model.exception.SQLFrameworkInternalException;
import frmw.model.fun.olap.support.Rows;
import frmw.model.ifelse.Case;
import frmw.model.ifelse.SimpleCase;
import frmw.model.ifelse.WhenBlock;
import frmw.parser.op.BinaryOp;
import frmw.parser.op.CompareOp;
import frmw.parser.op.NullOp;
import frmw.parser.op.UnaryOp;
import org.codehaus.jparsec.OperatorTable;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.functors.*;
import org.codehaus.jparsec.misc.Mapper;
import org.codehaus.jparsec.pattern.CharPredicate;
import org.codehaus.jparsec.pattern.Pattern;
import org.codehaus.jparsec.pattern.Patterns;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static frmw.model.constant.NumericConstant.cleanUp;
import static java.lang.Character.isWhitespace;
import static java.text.MessageFormat.format;
import static org.codehaus.jparsec.Parsers.*;
import static org.codehaus.jparsec.ProcessIllegalFunctionNameHandling.suppress;
import static org.codehaus.jparsec.Scanners.*;
import static org.codehaus.jparsec.StringLiteralScanner.literal;
import static org.codehaus.jparsec.misc.Mapper.curry;
import static org.codehaus.jparsec.pattern.Patterns.among;

/**
 * @author Alexey Paramonov
 */
public class Common {

	private static final RegisteredForPositionMap<FormulaElement, String> QUOTED_COLUMN = new RegisteredForPositionMap<FormulaElement, String>() {
		@Override
		protected FormulaElement build(String value) {
			return new Column(value, true);
		}
	};
	private static final RegisteredForPositionMap<FormulaElement, String> COLUMN = new RegisteredForPositionMap<FormulaElement, String>() {
		@Override
		protected FormulaElement build(String value) {
			return new Column(value, false);
		}
	};
	public static final CharPredicate COLUMN_CHARS = new CharPredicate() {
		@Override
		public boolean isChar(char c) {
			return c != '(' && c != ')' && c != ',' && c != '|' &&
					c != '=' && c != '<' && c != '>' && c != '!' &&
					c != '*' && c != '/' && c != '+' && c != '-' &&
					!isWhitespace(c);
		}
	};
	private static final CharPredicate QUOTED_COLUMN_CHARS = new CharPredicate() {
		@Override
		public boolean isChar(char c) {
			return c != '"';
		}
	};

	public final List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

	public static final Parser<Void> TRAILED = WHITESPACES.skipAtLeast(0);

	public static final Parser<?> COMMA = trailed(isChar(','));
	public static final Parser<Void> OPENED = trailed(isChar('('));
	public static final Parser<Void> CLOSED_TRIM_LEFT = TRAILED.next(isChar(')'));
	public static final Parser<Void> CLOSED = CLOSED_TRIM_LEFT.next(TRAILED);
	public static final Parser<Void> NO_ARG = OPENED.next(CLOSED);

	public static final Parser<Void> DQ = trailed(isChar('"'));
	public static final Parser<Void> SQ = trailed(isChar('\''));

	public static final Parser<Void> CONCAT = trailed(string("||"));
	public static final Parser<Void> PLUS = trailed(isChar('+'));
	public static final Parser<Void> MINUS = trailed(isChar('-'));
	public static final Parser<Void> MUL = trailed(isChar('*'));
	public static final Parser<Void> DIV = trailed(isChar('/'));

	public static final Parser<Void> EQ = trailed(isChar('='));
	public static final Parser<Void> GT = trailed(isChar('>'));
	public static final Parser<Void> LT = trailed(isChar('<'));
	public static final Parser<Void> GE = trailed(string(">="));
	public static final Parser<Void> LE = trailed(string("<="));
	public static final Parser<Void> NE = trailed(string("!="));
	public static final Parser<Void> IS_NULL = trailed(sequence(
			stringCaseInsensitive("is"), WHITESPACES.many1(),
			stringCaseInsensitive("null")));

	public static final Parser<Void> IS_NOT_NULL = trailed(sequence(
			stringCaseInsensitive("is"), WHITESPACES.many1(),
			stringCaseInsensitive("not"), WHITESPACES.many1(),
			stringCaseInsensitive("null")));

	public static final Parser<Void> AND = trailed(stringCaseInsensitive("and"));
	public static final Parser<Void> OR = trailed(stringCaseInsensitive("or"));

	public static final Parser<?> WHEN = trailed(stringCaseInsensitive("when"));
	public static final Parser<?> THEN = trailed(stringCaseInsensitive("then"));
	public static final Parser<?> ELSE = trailed(stringCaseInsensitive("else"));

	public static final String COLUMN_NAME_ID = "COLUMN_NAME";
	public static final String STRING_LITERAL_ID = "STRING_LITERAL";
	public static final String NUMERIC_ID = "NUMERIC";
	public static final String INTEGER_ID = "INTEGER";

	public static final Pattern INT = Patterns.among("0123456789_").many();
	public static final Parser<BigInteger> INTEGER_PARSER = pattern(INT, INTEGER_ID).source()
			.map(new Map<String, BigInteger>() {
				@Override
				public BigInteger map(String str) {
					return new BigInteger(cleanUp(str));
				}
			});

	public Common(Parser<FormulaElement> scalar, Parser<FormulaElement> aggregation, Parser<FormulaElement> common) {
		Parser<FormulaElement> all = withOperators(or(scalar, aggregation, common));

		parsers.add(stringLiteral());
		parsers.add(number());
		parsers.add(caseStatement(all));
		parsers.add(column());
	}

	public static Parser<Rows> rows() {
		Parser<Rows> all = trailed(stringCaseInsensitive("all")).retn(Rows.ALL);
		Parser<Rows> current = trailed(sequence(
				stringCaseInsensitive("current"),
				WHITESPACES.many1(),
				stringCaseInsensitive("row"))).retn(Rows.CURRENT_ROW);
		Parser<Rows> exact = INTEGER_PARSER.map(new Map<BigInteger, Rows>() {
			@Override
			public Rows map(BigInteger val) {
				return new Rows(val);
			}
		});

		return or(all, current, exact);
	}

	private Parser<FormulaElement> caseStatement(Parser<FormulaElement> all) {
		Parser<?> CASE = trailed(stringCaseInsensitive("case"));
		Parser<?> END = trailed(stringCaseInsensitive("end"));

		Parser<FormulaElement> searched = searchedCase(all);
		Parser<FormulaElement> simple = simpleCase(all);

		return searched.or(simple).between(CASE, END);
	}

	private Parser<FormulaElement> searchedCase(Parser<FormulaElement> all) {
		Parser<FormulaElement> conditionals = withAndOr(or(
				sequence(all, EQ, all, CompareOp.EQUAL),
				sequence(all, NE, all, CompareOp.NOT_EQUAL),
				sequence(all, LT, all, CompareOp.LESS),
				sequence(all, GT, all, CompareOp.GREAT),
				sequence(all, LE, all, CompareOp.EQUAL_LESS),
				sequence(all, GE, all, CompareOp.EQUAL_GREAT),
				sequence(all, IS_NULL, NullOp.NULL),
				sequence(all, IS_NOT_NULL, NullOp.NOT_NULL)));

		Parser<WhenBlock> when = trailed(curry(WhenBlock.class).sequence(WHEN.next(conditionals), THEN.next(all)));
		Parser<FormulaElement> elseBlock = ELSE.next(all).optional();
		return Mapper.<FormulaElement>curry(Case.class).sequence(when.many1(), elseBlock);
	}

	private Parser<FormulaElement> simpleCase(Parser<FormulaElement> all) {
		Parser<WhenBlock> when = trailed(curry(WhenBlock.class).sequence(WHEN.next(all), THEN.next(all)));
		Parser<FormulaElement> elseBlock = ELSE.next(all).optional();
		return Mapper.<FormulaElement>curry(SimpleCase.class).sequence(all, when.many1(), elseBlock);
	}

	private static Parser<FormulaElement> number() {
		Pattern strict = Patterns.sequence(Patterns.INTEGER, INT, Patterns.isChar('.').next(INT).optional());
		Pattern fraction = Patterns.isChar('.').next(INT);
		Pattern decimal = strict.or(fraction);
		Pattern scientific = Patterns.sequence(decimal, among("eE"), among("+-").optional(), INT);

		return trailed(pattern(scientific.or(decimal), NUMERIC_ID)).source().token()
				.map(new RegisteredForPositionMap<FormulaElement, String>() {
					@Override
					protected FormulaElement build(String value) {
						return new NumericConstant(value);
					}
				});
	}

	private static Parser<FormulaElement> stringLiteral() {
		return literal(STRING_LITERAL_ID).many1().source().between(SQ, SQ).token().map(
				new RegisteredForPositionMap<FormulaElement, String>() {
					@Override
					protected FormulaElement build(String value) {
						return new StringConstant(value);
					}
				});
	}

	/**
	 * @param orig original parser
	 * @return original parser with possibility to combine elements of grammar
	 * with operators (such as '+', '/', '||')
	 */
	public static Parser<FormulaElement> withOperators(Parser<FormulaElement> orig) {
		Parser.Reference<FormulaElement> ref = Parser.newReference();
		Parser<FormulaElement> unit = ref.lazy().between(OPENED, CLOSED).or(orig);
		Parser<FormulaElement> parser = new OperatorTable<FormulaElement>()
				.infixl(op(CONCAT, BinaryOp.CONCAT), 5)
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

	public static Parser<FormulaElement> withAndOr(Parser<FormulaElement> orig) {
		Parser.Reference<FormulaElement> ref = Parser.newReference();
		Parser<FormulaElement> unit = ref.lazy().between(OPENED, CLOSED).or(orig);
		Parser<FormulaElement> parser = new OperatorTable<FormulaElement>()
				.infixr(op(AND, BinaryOp.AND), 20)
				.infixr(op(OR, BinaryOp.OR), 10)
				.build(unit);
		ref.set(parser);
		return trailed(parser);
	}

	private static <T> Parser<T> op(Parser<Void> p, T value) {
		return p.retn(value);
	}

	/**
	 * This parser should be the last parser in any sequence of parsers.
	 */
	public static Parser<FormulaElement> column() {
		Parser<FormulaElement> quoted = isChar(QUOTED_COLUMN_CHARS, COLUMN_NAME_ID).
				many1().source().between(DQ, DQ).token().map(QUOTED_COLUMN);
		Parser<FormulaElement> plain = suppress(isChar(COLUMN_CHARS, COLUMN_NAME_ID).many1()
				.source().token().map(COLUMN)
				.followedBy(WHITESPACES.many().<FormulaElement>cast()));

		return trailed(or(quoted, plain));
	}

	public static <T> Parser<T> trailed(Parser<T> parser) {
		return parser.between(TRAILED, TRAILED);
	}

	public static <T extends FormulaElement> Parser<T> fun(final Class<? extends T> clazz, final Parser<?>... args) {
		Parser<?> body = args.length == 0 ? NO_ARG : buildArgumentParser(args).between(OPENED, CLOSED_TRIM_LEFT);
		Parser<Void> functionName = trailed(stringCaseInsensitive(funName(clazz)));
		return functionName.next(body).token().map(new RegisteredForPositionMap<T, List<Object>>() {
			@Override
			protected FormulaElement build(List<Object> result) throws Exception {
				return args.length == 0 ? clazz.newInstance() : newInstance(clazz, result);
			}
		}).followedBy(TRAILED);
	}

	private static Parser<?> buildArgumentParser(Parser<?>[] args) {
		List<Parser<?>> result = new ArrayList<Parser<?>>(args.length * 2);
		result.add(args[0]);
		for (int i = 1; i < args.length; i++) {
			result.add(COMMA);
			result.add(args[i]);
		}

		return list(result);
	}

	private static FormulaElement newInstance(Class<?> clazz, List<Object> values) throws Exception {
		List<Object> notNull = filterNotNull(values);
		Constructor<?> constructor = appropriate(clazz, notNull.size());
		if (constructor == null) {
			throw new SQLFrameworkInternalException(format(
					"Function {0} - constructor not found for arguments {1}",
					funName(clazz), notNull));
		}

		Object[] args = notNull.toArray(new Object[notNull.size()]);
		return (FormulaElement) constructor.newInstance(args);
	}

	private static List<Object> filterNotNull(List<Object> values) {
		List<Object> result = new ArrayList<Object>();

		for (Object value : values) {
			if (value != null) {
				result.add(value);
			}
		}

		return result;
	}

	private static Constructor<?> appropriate(Class<?> clazz, int argNumber) {
		for (Constructor<?> c : clazz.getConstructors()) {
			if (c.getParameterTypes().length == argNumber) {
				return c;
			}
		}

		return null;
	}

	public static String funName(Class<?> clazz) {
		return clazz.getSimpleName();
	}
}

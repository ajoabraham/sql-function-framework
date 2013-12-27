import org.codehaus.jparsec.*;
import org.codehaus.jparsec.functors.Binary;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Unary;

import static org.codehaus.jparsec.Scanners.isChar;
import static org.codehaus.jparsec.Scanners.string;

/**
 * @author Alexey Paramonov
 */
public class SampleTest {

	enum BinaryOperator implements Binary<Double> {
		PLUS {
			public Double map(Double a, Double b) {
				return a + b;
			}
		},
		MINUS {
			public Double map(Double a, Double b) {
				return a - b;
			}
		},
		MUL {
			public Double map(Double a, Double b) {
				return a * b;
			}
		},
		DIV {
			public Double map(Double a, Double b) {
				return a / b;
			}
		}
	}

	enum UnaryOperator implements Unary<Double> {
		NEG {
			public Double map(Double n) {
				return -n;
			}
		}
	}

	static final Parser<Double> NUMBER = Terminals.DecimalLiteral.PARSER.map(new Map<String, Double>() {
		public Double map(String s) {
			return Double.valueOf(s);
		}
	});

	private static final Terminals OPERATORS = Terminals.operators("(", ")", ",", ";", "+", "-", "*", "/");

	static final Parser<Void> IGNORED = Scanners.WHITESPACES;

	static final Parser<?> TOKENIZER =
			Parsers.or((Parser<?>) Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());

	static Parser<?> term(String... names) {
		return OPERATORS.token(names);
	}

	static final Parser<BinaryOperator> WHITESPACE_MUL =
			term("+", "-", "*", "/").not().retn(BinaryOperator.MUL);

	static <T> Parser<T> op(String name, T value) {
		return term(name).retn(value);
	}

	static Parser<Double> calculator(Parser<Double> atom) {
		Parser.Reference<Double> ref = Parser.newReference();
		Parser<Double> unit = ref.lazy().between(term("("), term(")")).or(atom);
		Parser<Double> parser = new OperatorTable<Double>()
				.infixl(op("+", BinaryOperator.PLUS), 10)
				.infixl(op("-", BinaryOperator.MINUS), 10)
				.infixl(op("*", BinaryOperator.MUL).or(WHITESPACE_MUL), 20)
				.infixl(op("/", BinaryOperator.DIV), 20)
				.prefix(op("-", UnaryOperator.NEG), 30)
				.build(unit);
		ref.set(parser);
		return parser;
	}

	public static final Parser<Double> CALCULATOR = calculator(NUMBER).from(TOKENIZER, IGNORED);

	private static Parser<FieldNode> fieldNodeParser = Parsers.sequence(Terminals.fragment(Tokens.Tag.IDENTIFIER).map(new Map<String, FieldNode>() {
		@Override
		public FieldNode map(String from) {
			return new FieldNode(from);
		}
	})).cast();

	static final Parser<?> QUOTED_STRING_TOKENIZER = Terminals.StringLiteral.SINGLE_QUOTE_TOKENIZER.or(Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER);
	static final Terminals TERMINALS = Terminals.caseSensitive(new String[]{"=", "(", ")", "[", "]", ",", "<>"}, new String[]{"OR", "AND", "NOT", "IN"});
	static final Parser<?> TOKENIZER1 = Parsers.or(TERMINALS.tokenizer(), QUOTED_STRING_TOKENIZER);

	public static Parser<FieldNode> parser = fieldNodeParser.from(TOKENIZER1, Scanners.WHITESPACES).between(term("("), term(")"));

	private static class FieldNode {
		final String text;

		public FieldNode(String text) {

			this.text = text;
		}
	}

//	public static void main(String[] args) {
//		System.out.println(parser.parse("main"));
//	}

	private static final Terminals OPERATORS1 = Terminals.operators("(", ")");

	static final Parser<?> NUMBER1 = Terminals.DecimalLiteral.TOKENIZER.map(
			new Map<Tokens.Fragment, Double>() {
				public Double map(Tokens.Fragment s) {
					return Double.valueOf(s.text());
				}
			});

	static final Parser<Void> NUMBER1_VOID = Terminals.DecimalLiteral.TOKENIZER.map(
			new Map<Tokens.Fragment, Void>() {
				public Void map(Tokens.Fragment s) {
					return null;
				}
			});

	public static void main(String[] args) {
		Parser<Void> name = string("main").or(string("maat")).or(NUMBER1_VOID);
		Parser<?> result = name.next(NUMBER1.between(isChar('('), isChar(')')));
		System.out.println(result.parse("m1ain(12)"));
	}
}
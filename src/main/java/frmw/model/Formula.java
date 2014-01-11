package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.exception.ParsingException;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.exception.WrongFunctionNameException;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.position.Position;
import frmw.model.position.PositionMap;
import frmw.parser.FunctionType;
import frmw.parser.Parsing;
import org.codehaus.jparsec.error.ParseErrorDetails;
import org.codehaus.jparsec.error.ParserException;

import java.util.*;

import static java.lang.Character.isWhitespace;

/**
 * This class is not thread safe.
 *
 * @author Alexey Paramonov
 */
public class Formula {

	private final FormulaElement root;

	private final PositionMap positions = new PositionMap();

	/**
	 * @throws WrongFunctionNameException if framework decides that user misspelled a function name
	 * @throws ParsingException on any other error during parsing
	 */
	public Formula(String formula, Parsing parser) throws ParsingException, WrongFunctionNameException {
		try {
			this.root = parser.parse(formula);
		} catch (ParserException e) {
			ParseErrorDetails details = e.getErrorDetails();
			List<String> expected = details.getExpected();

			Set<FunctionType> types = EnumSet.noneOf(FunctionType.class);
			List<String> expectedFuncs = new ArrayList<String>();
			for (FunctionType type : EnumSet.allOf(FunctionType.class)) {
				if (expected.contains(type.name())) {
					types.add(type);
					expectedFuncs.addAll(type.functions(parser));
				}
			}

			int errorAt = details.getIndex();
			if (types.isEmpty()) {
				throw new ParsingException(errorAt, expected);
			} else {
				String wrongName = findFunctionName(formula, errorAt);
				throw new WrongFunctionNameException(errorAt, wrongName, types, expectedFuncs);
			}
		}
	}

	private static String findFunctionName(String formula, int errorAt) {
		int last = errorAt;
		while (last < formula.length()) {
			char c = formula.charAt(last);
			if (isWhitespace(c) || c == '(') {
				break;
			}
			last++;
		}

		return formula.substring(errorAt, last);
	}

	/**
	 * @return sql to appropriate database dialect
	 * @throws SQLFrameworkException on any error
	 */
	public String sql(Dialect dialect) throws SQLFrameworkException {
		StringBuilder sb = new StringBuilder();
		root.sql(dialect, sb);
		return sb.toString();
	}

	/**
	 * Validates formula for errors.
	 *
	 * @throws SQLFrameworkException if validation was failed
	 */
	public void validate(Dialect dialect) throws SQLFrameworkException {
		sql(dialect);
	}

	/**
	 * @return is formula contains aggregate or OLAP function
	 */
	public boolean hasAggregation() {
		return root.hasAggregation();
	}

	/**
	 * @return set of columns that formula contains
	 */
	public Set<String> entityNames() {
		Set<String> result = new HashSet<String>();
		root.collectEntities(result);
		return result;
	}

	public List<WindowParameters> windowParameters() {
		List<WindowParameters> result = new ArrayList<WindowParameters>();
		root.collectWindowParams(result);
		return result;
	}

	public List<AggregationParameters> aggregationParameters() {
		List<AggregationParameters> result = new ArrayList<AggregationParameters>();
		root.collectAggregationParams(result);
		return result;
	}

	public List<RankParameters> rankParameters() {
		List<RankParameters> result = new ArrayList<RankParameters>();
		root.collectRankParams(result);
		return result;
	}

	public void register(FormulaElement res, int index, int length) {
		positions.add(res, index, length);
	}

	public Position position(FormulaElement e) {
		return positions.find(e);
	}
}

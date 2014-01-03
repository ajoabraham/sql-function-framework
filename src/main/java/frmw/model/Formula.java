package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.position.PositionMap;
import frmw.parser.Parsing;
import org.codehaus.jparsec.error.ParserException;

/**
 * This class not thread safe.
 *
 * @author Alexey Paramonov
 */
public class Formula {

	private final FormulaElement root;

	private final PositionMap positions = new PositionMap();

	/**
	 * @throws SQLFrameworkException on any error during parsing
	 */
	public Formula(String formula, Parsing parser) throws SQLFrameworkException {
		try {
			this.root = parser.parse(formula);
		} catch (ParserException e) {
			throw e;
//			throw new ParsingException();
		}
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

	public void register(FormulaElement res, int index, int length) {
		positions.add(res, index, length);
	}

	public PositionMap.Position position(FormulaElement e) {
		return positions.find(e);
	}
}

package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.position.PositionMap;

/**
 * @author Alexey Paramonov
 */
public class Join {

	private final FormulaElement root;

	private final PositionMap positions;

	public Join(FormulaElement root, PositionMap map) {
		this.root = root;
		this.positions = map;
	}

	/**
	 * @return sql to appropriate database dialect
	 * @throws frmw.model.exception.SQLFrameworkException on any error
	 */
	public String sql(Dialect dialect) throws SQLFrameworkException {
		StringBuilder sb = new StringBuilder();
		root.sql(dialect, sb);
		return sb.toString();
	}
}

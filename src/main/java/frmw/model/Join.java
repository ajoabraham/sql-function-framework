package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.position.PositionMap;

/**
 * @author Alexey Paramonov
 */
public class Join extends PositionProvider {

	private final FormulaElement root;

	public Join(FormulaElement root, PositionMap map) {
		super(map);
		this.root = root;
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
}

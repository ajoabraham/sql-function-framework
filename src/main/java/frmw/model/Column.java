package frmw.model;

import frmw.dialect.Dialect;

/**
 * @author Alexey Paramonov
 */
public class Column implements FormulaElement {

	private final String name;

	public Column(String name) {
		this.name = name.trim();
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.validateColumnName(name);
		sb.append(name);
	}
}

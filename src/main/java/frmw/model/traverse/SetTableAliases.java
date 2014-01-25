package frmw.model.traverse;

import frmw.model.Column;

import java.util.Map;

/**
 * @author Alexey Paramonov
 */
public class SetTableAliases implements ColumnTraversal {

	private final Map<String, String> columnToAlias;

	public SetTableAliases(Map<String, String> columnToAlias) {
		this.columnToAlias = columnToAlias;
	}

	@Override
	public void traverse(Column column) {
		String name = column.name();
		String alias = columnToAlias.get(name);
		if (alias != null) {
			column.setTableAlias(alias);
		}
	}
}

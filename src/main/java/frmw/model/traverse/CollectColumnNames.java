package frmw.model.traverse;

import frmw.model.Column;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class CollectColumnNames implements ColumnTraversal {

	private final Set<String> names = new HashSet<String>();

	@Override
	public void traverse(Column column) {
		names.add(column.name());
	}

	public Set<String> names() {
		return names;
	}
}

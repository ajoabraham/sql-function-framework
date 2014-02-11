package frmw.model.traverse;

import frmw.model.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class CollectTableAliases implements ColumnTraversal {

	public static class Holder {

		public final Column column;

		public Holder(Column column) {
			this.column = column;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Holder)) return false;

			Holder holder = (Holder) o;
			return column.tableAlias().equals(holder.column.tableAlias());

		}

		@Override
		public int hashCode() {
			return column.tableAlias().hashCode();
		}

		@Override
		public String toString() {
			return column.tableAlias();
		}
	}

	private final List<Holder> names = new ArrayList<Holder>();

	@Override
	public void traverse(Column column) {
		names.add(new Holder(column));
	}

	public List<Holder> names() {
		return names;
	}
}

package frmw.model;

import com.google.common.collect.Lists;
import frmw.dialect.Dialect;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.exception.UnexpectedTablesAmountInJoin;
import frmw.model.position.PositionMap;
import frmw.model.traverse.CollectTableAliases;
import frmw.model.traverse.ColumnTraversal;
import frmw.model.util.AlphanumComparator;

import java.util.Collections;
import java.util.List;

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

	/**
	 * Expects that current aliases are like 'Tn'. Where 'n' is a number - 1, 2, 3, etc.
	 * Left table is always table with less 'n'.
	 *
	 * @param left  left table alias
	 * @param right right table alias
	 * @throws UnexpectedTablesAmountInJoin if join definition contains amount of table aliases not equal 2
	 */
	public void changeTableAliases(final String left, final String right) {
		CollectTableAliases collectNames = new CollectTableAliases();
		root.traverseColumns(collectNames);

		List<String> names = Lists.newArrayList(collectNames.names());
		if (names.size() != 2) {
			throw new UnexpectedTablesAmountInJoin(names);
		}

		Collections.sort(names, AlphanumComparator.INST);
		final String leftOld = names.get(0);

		root.traverseColumns(new ColumnTraversal() {
			@Override
			public void traverse(Column column) {
				if (column.tableAlias().equals(leftOld)) {
					column.setTableAlias(left);
				} else {
					column.setTableAlias(right);
				}
			}
		});
	}
}

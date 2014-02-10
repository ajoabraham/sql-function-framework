package frmw.model;

import com.google.common.collect.Lists;
import frmw.dialect.Dialect;
import frmw.model.exception.InvalidTableAlias;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.exception.UnexpectedTablesAmountInJoin;
import frmw.model.position.PositionMap;
import frmw.model.traverse.CollectTableAliases;
import frmw.model.traverse.ColumnTraversal;
import frmw.model.util.AlphanumComparator;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Representation of parsed join definition like 'T1.col1 = T2.col2'.
 *
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
	 * Validates join definition.
	 *
	 * @throws UnexpectedTablesAmountInJoin if join definition contains amount of table aliases not equal 2
	 * @throws InvalidTableAlias if table aliases doesn't have format like 'Tn'
	 */
	public void validate(Dialect dialect) {
		List<String> names = tableAliases();
		if (names.size() != 2) {
			throw new UnexpectedTablesAmountInJoin(names);
		}

		for (String name : names) {
			char first = name.charAt(0);
			String possibleNumber = name.substring(1);
			boolean restIsNumber = isNumeric(possibleNumber);
			if ((first != 't' && first != 'T') || !restIsNumber) {
				throw new InvalidTableAlias(name);
			}
		}

		sql(dialect);
	}

	/**
	 * Expects that current aliases are like 'Tn'. Where 'n' is a number - 1, 2, 3, etc.
	 * Left table is always table with less 'n'.
	 * Result of invocation of this method may be unpredictable if {@link #validate(Dialect)} is failed.
	 *
	 * @param left  left table alias
	 * @param right right table alias
	 */
	public void changeTableAliases(final String left, final String right) {
		List<String> names = tableAliases();

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

	private List<String> tableAliases() {
		CollectTableAliases collectNames = new CollectTableAliases();
		root.traverseColumns(collectNames);
		return Lists.newArrayList(collectNames.names());
	}
}

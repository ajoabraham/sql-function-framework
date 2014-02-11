package frmw.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import frmw.dialect.Dialect;
import frmw.model.exception.InvalidTableAlias;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.exception.UnexpectedTablesAmountInJoin;
import frmw.model.position.Position;
import frmw.model.position.PositionMap;
import frmw.model.traverse.CollectTableAliases;
import frmw.model.util.AlphanumComparator;
import frmw.model.util.IndexReplacer;

import java.util.*;

import static frmw.model.traverse.CollectTableAliases.Holder;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Representation of parsed join definition like 'T1.col1 = T2.col2'.
 *
 * @author Alexey Paramonov
 */
public class Join extends PositionProvider {

	private static final String T_LEFT = "tLeft";
	private static final String T_RIGHT = "tRight";

	private final String formula;

	private final FormulaElement root;

	public Join(String formula, FormulaElement root, PositionMap map) {
		super(map);
		this.formula = formula;
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
	 * @throws InvalidTableAlias            if table aliases doesn't have format like 'Tn'
	 */
	public void validate(Dialect dialect) {
		Set<Holder> holders = Sets.newHashSet(tableAliases());
		if (holders.size() != 2) {
			List<String> names = extractAliases(holders);
			throw new UnexpectedTablesAmountInJoin(names);
		}

		validateTableAliasesStartsFromT(holders);
		sql(dialect);
	}

	private void validateTableAliasesStartsFromT(Collection<Holder> holders) {
		for (Holder holder : holders) {
			String name = holder.column.tableAlias();

			char first = name.charAt(0);
			String possibleNumber = name.substring(1);
			boolean restIsNumber = isNumeric(possibleNumber);
			if ((first != 't' && first != 'T') || !restIsNumber) {
				throw InvalidTableAlias.Tn(name);
			}
		}
	}

	private void validateTableAliasesTLeftTRight(Collection<Holder> holders) {
		for (Holder holder : holders) {
			String name = holder.column.tableAlias();
			if (!T_LEFT.equalsIgnoreCase(name) && !T_RIGHT.equalsIgnoreCase(name)) {
				throw InvalidTableAlias.tLeftTRight(name);
			}
		}
	}

	private List<String> extractAliases(Set<Holder> holders) {
		List<String> res = new ArrayList<String>(holders.size());

		for (Holder holder : holders) {
			res.add(holder.column.tableAlias());
		}

		return res;
	}

	/**
	 * Expects that current aliases are like 'Tn'. Where 'n' is a number - 1, 2, 3, etc.
	 * Left table is always table with less 'n'.
	 *
	 * @return formula with rewritten left table alias to 'tLeft', right table alias to 'tRight'
	 */
	public String rewriteFormula() {
		List<Holder> holders = tableAliases();
		validateTableAliasesStartsFromT(holders);

		return rewriteFormula(holders, T_LEFT, T_RIGHT);
	}

	/**
	 * Expects that current aliases are already rewritten by {@link #rewriteFormula()}.
	 *
	 * @param left  left table alias
	 * @param right right table alias
	 */
	public String rewriteFormula(String left, String right) {
		List<Holder> holders = tableAliases();
		validateTableAliasesTLeftTRight(holders);

		return rewriteFormula(holders, left, right);
	}

	private String rewriteFormula(List<Holder> holders, String left, String right) {
		List<Holder> unique = new ArrayList<Holder>(new HashSet<Holder>(holders));

		Map<String, String> oldToNew = buildMap(unique, left, right);

		IndexReplacer replacer = new IndexReplacer(formula);
		for (Holder holder : holders) {
			Position p = elementPositions().find(holder.column);
			String column = formula.substring(p.index, p.index + p.length);

			String oldAlias = holder.column.tableAlias();
			String newAlias = oldToNew.get(oldAlias);
			String replaced = column.replaceFirst(oldAlias, newAlias);
			replacer.add(p.index, p.index + p.length, replaced);
		}

		return replacer.build();
	}

	private Map<String, String> buildMap(List<Holder> unique, String left, String right) {
		Collections.sort(unique, AlphanumComparator.INST);
		String leftOld = unique.get(0).column.tableAlias();
		String rightOld = unique.get(1).column.tableAlias();

		return ImmutableMap.of(leftOld, left, rightOld, right);
	}

	private List<Holder> tableAliases() {
		CollectTableAliases collectNames = new CollectTableAliases();
		root.traverseColumns(collectNames);
		return collectNames.names();
	}
}

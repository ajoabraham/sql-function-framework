package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.position.PositionMap;
import frmw.model.traverse.CollectColumnNames;
import frmw.model.traverse.SetTableAliases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is not thread safe.
 *
 * @author Alexey Paramonov
 */
public class Formula {

	private final FormulaElement root;

	private final PositionMap positions;

	public Formula(FormulaElement root, PositionMap map) {
		this.root = root;
		this.positions = map;
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

	/**
	 * @return is formula contains aggregate or OLAP function
	 */
	public boolean hasAggregation() {
		return root.hasAggregation();
	}

	/**
	 * @return set of columns that formula contains
	 */
	public Set<String> entityNames() {
		CollectColumnNames traversal = new CollectColumnNames();
		root.traverseColumns(traversal);
		return traversal.names();
	}

	/**
	 * Populates columns model by table aliases.
	 * Take into account that map in most cases should contains keys in case insensitive order.
	 * In these cases please use {@link java.util.TreeMap#TreeMap(java.util.Comparator)}
	 * to instantiate the map with {@link java.lang.String.CaseInsensitiveComparator}.
	 *
	 * @param columnToAlias map column name -> table alias
	 */
	public void setTableAliases(Map<String, String> columnToAlias) {
		SetTableAliases traversal = new SetTableAliases(columnToAlias);
		root.traverseColumns(traversal);
	}

	public List<WindowParameters> windowParameters() {
		List<WindowParameters> result = new ArrayList<WindowParameters>();
		root.collectWindowParams(result);
		return result;
	}

	public List<AggregationParameters> aggregationParameters() {
		List<AggregationParameters> result = new ArrayList<AggregationParameters>();
		root.collectAggregationParams(result);
		return result;
	}

	public List<RankParameters> rankParameters() {
		List<RankParameters> result = new ArrayList<RankParameters>();
		root.collectRankParams(result);
		return result;
	}

	public PositionMap elementPositions() {
		return positions;
	}
}

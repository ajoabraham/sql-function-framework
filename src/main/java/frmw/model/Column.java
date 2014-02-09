package frmw.model;

import frmw.dialect.Dialect;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.traverse.ColumnTraversal;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.trimToNull;

/**
 * @author Alexey Paramonov
 */
public class Column implements FormulaElement {

	private final String name;

	private final boolean quoted;

	private String tableAlias;

	public Column(String tableAlias, String name, boolean quoted) {
		this.tableAlias = tableAlias;
		this.name = name.trim();
		this.quoted = quoted;
	}

	public Column(String name, boolean quoted) {
		this.name = name.trim();
		this.quoted = quoted;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		if (tableAlias == null) {
			appendColumn(sb);
		} else {
			sb.append('\"').append(tableAlias).append("\".");
			appendColumn(sb);
		}
	}

	private void appendColumn(StringBuilder sb) {
		if (quoted) {
			sb.append('"').append(name).append('"');
		} else {
			sb.append(name);
		}
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		traversal.traverse(this);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
	}

	@Override
	public String toString() {
		return tableAlias == null ? name : "\"" + tableAlias + "\".\"" + name + "\"";
	}

	public void setTableAlias(String alias) {
		this.tableAlias = trimToNull(alias);
	}

	public String name() {
		return name;
	}

	public boolean quoted() {
		return quoted;
	}

	public String tableAlias() {
		return tableAlias;
	}
}

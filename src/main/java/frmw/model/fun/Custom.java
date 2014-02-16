package frmw.model.fun;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.traverse.ColumnTraversal;

import java.util.Collections;
import java.util.List;

import static java.lang.Character.isDigit;

/**
 * @author Alexey Paramonov
 */
public class Custom implements FormulaElement {

	private final String sql;

	private final int sqlIndex;

	private final List<FormulaElement> args;

	public Custom(String sql, Integer sqlIndex, List<FormulaElement> args) {
		this.sql = sql;
		this.sqlIndex = sqlIndex == null ? 0 : sqlIndex;
		this.args = args == null ? Collections.<FormulaElement>emptyList() : args;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);

			if (c == '\'' && i != sql.length() - 1) {
				char nextC = sql.charAt(i + 1);
				if (nextC == '\'') {
					i++;
				}
			}

			if (c != '$') {
				sb.append(c);
				continue;
			}

			i++;
			char nextC = sql.charAt(i);
			if (nextC == '$') {
				sb.append(nextC);
				continue;
			}

			String numStr = extractNumber(sql, i);
			int argNum = Integer.parseInt(numStr);
			validateArgNumber(i, argNum);

			FormulaElement arg = args.get(argNum);
			arg.sql(dialect, sb);

			i += numStr.length() - 1;
		}
	}

	private void validateArgNumber(int i, int argNum) {
		if (argNum >= args.size()) {
			int numberIndex = sqlIndex + i;
			if (args.isEmpty()) {
				throw new SQLFrameworkException("No arguments provided to sql", numberIndex);
			} else {
				throw new SQLFrameworkException("Argument index should begins from 0 and ends by " + args.size(), numberIndex);
			}
		}
	}

	private String extractNumber(String sql, int i) {
		StringBuilder sb = new StringBuilder();

		while (i < sql.length()) {
			char c = sql.charAt(i);
			if (!isDigit(c)) {
				break;
			}

			sb.append(c);
			i++;
		}

		if (sb.length() == 0) {
			throw new SQLFrameworkException("A number should following symbol '$' or just type '$$' to escape '$'", sqlIndex + i);
		}

		return sb.toString();
	}

	@Override
	public boolean hasAggregation() {
		for (FormulaElement e : args) {
			if (e.hasAggregation()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		for (FormulaElement e : args) {
			e.traverseColumns(traversal);
		}
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		for (FormulaElement e : args) {
			e.collectWindowParams(list);
		}
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		for (FormulaElement e : args) {
			e.collectAggregationParams(list);
		}
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		for (FormulaElement e : args) {
			e.collectRankParams(list);
		}
	}
}

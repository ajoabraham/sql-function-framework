package frmw.model.constant;

import frmw.dialect.Dialect;
import frmw.model.traverse.ColumnTraversal;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;

import java.util.List;

import static java.lang.Character.isWhitespace;

/**
 * Numbers may contain whitespaces and '_' underscores to define more readable format
 * like '1_234_222_443' instead of '1234222443'.
 *
 * @author Alexey Paramonov
 */
public class NumericConstant implements FormulaElement {

	private final String constant;

	public NumericConstant(String constant) {
		this.constant = cleanUp(constant);
	}

	public static String cleanUp(String constant) {
		StringBuilder sb = new StringBuilder(constant.length());

		for (int i = 0; i < constant.length(); i++) {
			char c = constant.charAt(i);
			if (c != '_' && !isWhitespace(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		// do not check correctness of the input - just lets the database raise error
		sb.append(constant);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
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
		return constant;
	}
}

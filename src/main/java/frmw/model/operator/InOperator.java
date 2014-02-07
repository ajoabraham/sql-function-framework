package frmw.model.operator;

import com.google.common.base.Joiner;
import frmw.dialect.Dialect;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.traverse.ColumnTraversal;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class InOperator implements FormulaElement {

	public final FormulaElement tested;

	public final List<FormulaElement> in;

	public InOperator(FormulaElement tested, List<FormulaElement> in) {
		this.tested = tested;
		this.in = in;
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		tested.sql(dialect, sb);
		sb.append(" IN (");

		Iterator<FormulaElement> it = in.iterator();
		while (it.hasNext()) {
			FormulaElement elem = it.next();
			elem.sql(dialect, sb);

			if (it.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append(')');
	}

	@Override
	public boolean hasAggregation() {
		if (tested.hasAggregation()) {
			return true;
		}

		for (FormulaElement elem : in) {
			if (elem.hasAggregation()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void traverseColumns(ColumnTraversal traversal) {
		tested.traverseColumns(traversal);
		for (FormulaElement elem : in) {
			elem.traverseColumns(traversal);
		}
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		tested.collectWindowParams(list);
		for (FormulaElement elem : in) {
			elem.collectWindowParams(list);
		}
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
		tested.collectAggregationParams(list);
		for (FormulaElement elem : in) {
			elem.collectAggregationParams(list);
		}
	}

	@Override
	public void collectRankParams(List<RankParameters> list) {
		tested.collectRankParams(list);
		for (FormulaElement elem : in) {
			elem.collectRankParams(list);
		}
	}

	@Override
	public String toString() {
		return tested + " in (" + Joiner.on(',').join(in) + ")";
	}
}

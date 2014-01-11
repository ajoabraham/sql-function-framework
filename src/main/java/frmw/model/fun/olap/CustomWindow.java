package frmw.model.fun.olap;

import frmw.dialect.Dialect;
import frmw.model.fun.aggregation.Aggregation;
import frmw.model.FormulaElement;
import frmw.model.PositionAware;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.position.Position;
import frmw.model.fun.olap.support.Rows;

import java.util.List;
import java.util.Set;

/**
 * @author Alexey Paramonov
 */
public class CustomWindow implements FormulaElement, PositionAware {

	private final Aggregation arg;

	private final Rows preceding;

	/**
	 * Nullable.
	 */
	private final Rows following;

	private final WindowParameters params;

	public CustomWindow(Aggregation arg, Rows preceding, Rows following) {
		this.arg = arg;
		this.preceding = preceding;
		this.following = following;
		this.params = new WindowParameters(arg);
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		dialect.window(sb, arg, preceding, following, params);
	}

	@Override
	public boolean hasAggregation() {
		return true;
	}

	@Override
	public void collectEntities(Set<String> set) {
		arg.collectEntities(set);
	}

	@Override
	public void collectWindowParams(List<WindowParameters> list) {
		list.add(params);
	}

	@Override
	public void collectAggregationParams(List<AggregationParameters> list) {
	}

	@Override
	public void position(int index, int length) {
		params.position(new Position(index, length));
	}
}

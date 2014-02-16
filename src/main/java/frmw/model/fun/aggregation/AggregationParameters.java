package frmw.model.fun.aggregation;

import frmw.model.FormulaElement;
import frmw.model.position.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class AggregationParameters {

	private final FormulaElement element;

	public AggregationParameters(FormulaElement element) {
		this.element = element;
	}

	private Position position;

	protected void position(Position position) {
		this.position = position;
	}

	/**
	 * @return element position in formula
	 */
	public Position position() {
		return position;
	}

	/**
	 * @return is aggregation distinct like {@code sum(distinct col1)}
	 */
	public boolean distinct() {
		AggregationParameters params = params();
		return params != null && params.distinct();
	}

	public void distinct(boolean distinct) {
		if (element instanceof Aggregation) {
			((Aggregation) element).distinct = distinct;
			return;
		}

		AggregationParameters params = params();
		if (params != null) {
			params.distinct(distinct);
		}
	}

	private AggregationParameters params() {
		List<AggregationParameters> list = new ArrayList<AggregationParameters>();
		element.collectAggregationParams(list);
		return list.isEmpty() ? null : list.get(0);
	}
}

package frmw.model.fun.aggregation;

import frmw.model.position.Position;

/**
 * @author Alexey Paramonov
 */
public class AggregationParameters {

	private final Aggregation element;

	public AggregationParameters(Aggregation element) {
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
		return element.distinct;
	}

	public void distinct(boolean distinct) {
		element.distinct = distinct;
	}
}

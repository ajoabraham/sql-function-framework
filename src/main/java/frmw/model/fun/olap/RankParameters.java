package frmw.model.fun.olap;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import frmw.model.fun.olap.support.Order;
import frmw.model.position.Position;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Class manages additional parameters that applied to {@link Rank} functions.
 *
 * @author Alexey Paramonov
 */
public class RankParameters {

	private final List<String> partitions = new ArrayList<String>();

	private Order order = Order.ASC;

	/**
	 * If expression already present, it will be removed and added to the tail.
	 *
	 * @param expr column name or another valid sql-expression, not empty
	 * @throws IllegalArgumentException on illegal arguments
	 */
	public RankParameters partition(final String expr) throws IllegalArgumentException {
		checkColumn(expr);

		Iterables.removeIf(partitions, new Predicate<String>() {
			@Override
			public boolean apply(String part) {
				return part.equalsIgnoreCase(expr);
			}
		});

		partitions.add(expr);
		return this;
	}

	private static void checkColumn(String expr) {
		if (expr == null || expr.trim().isEmpty()) {
			throw new IllegalArgumentException("Expression should not be empty");
		}
	}

	public List<String> partitions() {
		return unmodifiableList(partitions);
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
	 * @return order that applied to order by clause
	 */
	public Order order() {
		return order;
	}

	public RankParameters order(Order order) {
		this.order = order;
		return this;
	}
}

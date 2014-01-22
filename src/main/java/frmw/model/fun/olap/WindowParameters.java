package frmw.model.fun.olap;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import frmw.model.fun.aggregation.Aggregation;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.support.OrderBy;
import frmw.model.fun.olap.support.Order;
import frmw.model.position.Position;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Class manages additional parameters that applied to window functions with predefined aggregation.
 * See subclasses of the {@link CustomWindow}.
 * <p/>
 * Orders and partitions
 *
 * @author Alexey Paramonov
 */
public class WindowParameters extends AggregationParameters {

	private final List<OrderBy> orders = new ArrayList<OrderBy>();

	private final List<String> partitions = new ArrayList<String>();

	public WindowParameters(Aggregation element) {
		super(element);
	}

	/**
	 * If column already present, it will be removed and added to the tail.
	 *
	 * @param column column name, not empty
	 * @param order  order type, {@link frmw.model.fun.olap.support.Order#ASC} will be used if null
	 * @throws IllegalArgumentException on illegal arguments
	 */
	public WindowParameters order(final String column, Order order) throws IllegalArgumentException {
		checkColumn(column);

		if (order == null) {
			order = Order.ASC;
		}

		Iterables.removeIf(orders, new Predicate<OrderBy>() {
			@Override
			public boolean apply(OrderBy input) {
				return input.column.equalsIgnoreCase(column);
			}
		});

		orders.add(new OrderBy(column, order));
		return this;
	}

	/**
	 * If column already present, it will be removed and added to the tail.
	 *
	 * @param column column name, not empty
	 * @throws IllegalArgumentException on illegal arguments
	 */
	public WindowParameters partition(final String column) throws IllegalArgumentException {
		checkColumn(column);

		Iterables.removeIf(partitions, new Predicate<String>() {
			@Override
			public boolean apply(String part) {
				return part.equalsIgnoreCase(column);
			}
		});

		partitions.add(column);
		return this;
	}

	private static void checkColumn(String column) {
		if (column == null || column.trim().isEmpty()) {
			throw new IllegalArgumentException("Column should not be empty");
		}
	}

	public List<OrderBy> orders() {
		return unmodifiableList(orders);
	}

	public List<String> partitions() {
		return unmodifiableList(partitions);
	}

	protected void position(Position position) {
		super.position(position);
	}
}

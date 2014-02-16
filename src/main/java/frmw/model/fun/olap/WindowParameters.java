package frmw.model.fun.olap;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import frmw.model.FormulaElement;
import frmw.model.fun.aggregation.AggregationParameters;
import frmw.model.fun.olap.support.Order;
import frmw.model.fun.olap.support.OrderBy;
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

	public WindowParameters(FormulaElement element) {
		super(element);
	}

	/**
	 * If expression already present, it will be removed and added to the tail.
	 *
	 * @param expr  column name or another valid sql-expression, not empty
	 * @param order order type, {@link frmw.model.fun.olap.support.Order#ASC} will be used if null
	 * @throws IllegalArgumentException on illegal arguments
	 */
	public WindowParameters order(final String expr, Order order) throws IllegalArgumentException {
		checkColumn(expr);

		if (order == null) {
			order = Order.ASC;
		}

		Iterables.removeIf(orders, new Predicate<OrderBy>() {
			@Override
			public boolean apply(OrderBy input) {
				return input.column.equalsIgnoreCase(expr);
			}
		});

		orders.add(new OrderBy(expr, order));
		return this;
	}

	/**
	 * If expression already present, it will be removed and added to the tail.
	 *
	 * @param expr column name or another valid sql-expression, not empty
	 * @throws IllegalArgumentException on illegal arguments
	 */
	public WindowParameters partition(final String expr) throws IllegalArgumentException {
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

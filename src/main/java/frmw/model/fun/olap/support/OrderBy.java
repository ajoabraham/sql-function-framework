package frmw.model.fun.olap.support;

/**
 * @author Alexey Paramonov
 */
public class OrderBy {

	public final String column;

	public final Order order;

	public OrderBy(String column, Order order) {
		this.column = column;
		this.order = order;
	}
}

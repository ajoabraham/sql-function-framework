package frmw.model.fun.olap.support;

/**
 * @author Alexey Paramonov
 */
public class GroupBy {

	public final String column;

	public final Order order;

	public GroupBy(String column, Order order) {
		this.column = column;
		this.order = order;
	}
}

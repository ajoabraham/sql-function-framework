package frmw.model.exception;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class UnexpectedTablesAmountInJoin extends SQLFrameworkException {

	public final List<String> tables;

	public UnexpectedTablesAmountInJoin(List<String> tables) {
		super("Expects 2 table aliases, but found : " + tables);
		this.tables = tables;
	}
}

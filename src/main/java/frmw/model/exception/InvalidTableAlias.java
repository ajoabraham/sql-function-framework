package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class InvalidTableAlias extends SQLFrameworkException {

	private final String alias;

	public InvalidTableAlias(String alias) {
		super("Table alias should be like 'Tn' where 'n' is positive number, but found : " + alias);
		this.alias = alias;
	}

	/**
	 * @return alias that is wrong
	 */
	public String alias() {
		return alias;
	}
}

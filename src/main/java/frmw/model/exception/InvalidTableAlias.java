package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class InvalidTableAlias extends SQLFrameworkException {

	private final String alias;

	private InvalidTableAlias(String message, String alias) {
		super(message);
		this.alias = alias;
	}

	public static InvalidTableAlias tLeftTRight(String alias) {
		String message = "Table alias should be 'tLeft' or 'tRight', but found : " + alias;
		return new InvalidTableAlias(message, alias);
	}

	public static InvalidTableAlias Tn(String alias) {
		String message = "Table alias should be like 'Tn' where 'n' is positive number, but found : " + alias;
		return new InvalidTableAlias(message, alias);
	}

	/**
	 * @return alias that is wrong
	 */
	public String alias() {
		return alias;
	}
}

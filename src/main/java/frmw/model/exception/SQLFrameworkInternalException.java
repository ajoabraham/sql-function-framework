package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class SQLFrameworkInternalException extends SQLFrameworkException {

	public SQLFrameworkInternalException(String message) {
		super(message);
	}

	public static SQLFrameworkException wrap(Throwable t) {
		return t instanceof SQLFrameworkInternalException ? (SQLFrameworkInternalException) t : new SQLFrameworkException(t);
	}
}

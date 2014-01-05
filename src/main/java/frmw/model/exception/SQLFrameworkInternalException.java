package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class SQLFrameworkInternalException extends SQLFrameworkException {

	public SQLFrameworkInternalException(String source, String message) {
		super(source, message);
	}

	public static SQLFrameworkException wrap(Throwable t) {
		return t instanceof SQLFrameworkException ? (SQLFrameworkInternalException) t : new SQLFrameworkException(t);
	}
}
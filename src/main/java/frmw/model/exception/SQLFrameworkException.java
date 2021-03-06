package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class SQLFrameworkException extends RuntimeException {

	private int index = -1;

	private int length = -1;

	public SQLFrameworkException(String message) {
		super(message);
	}

	public SQLFrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLFrameworkException(Throwable t) {
		super(t);
	}

	public SQLFrameworkException(String message, int index) {
		super(message + ", position(" + index + ", 1)");
		this.index = index;
		this.length = 1;
	}

	public void position(int index, int length) {
		this.index = index;
		this.length = length;
	}

	public static SQLFrameworkException wrap(Throwable t) {
		return t instanceof SQLFrameworkException ? (SQLFrameworkException) t : new SQLFrameworkException(t);
	}

	public int index() {
		return index;
	}

	public int length() {
		return length;
	}

	public boolean positionSet() {
		return index >= 0 || length >= 0;
	}
}

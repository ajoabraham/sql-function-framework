package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class SQLFrameworkException extends RuntimeException {

	public final String source;

	private int index = -1;

	private int length = -1;

	public SQLFrameworkException(String source, String message) {
		super(message + ", source : " + source);
		this.source = source;
	}

	public SQLFrameworkException(Throwable t) {
		super(t);
		this.source = null;
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
		return index >= 0 && length >= 0;
	}
}

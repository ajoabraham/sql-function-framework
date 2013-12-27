package frmw.model.exception;

/**
 * @author Alexey Paramonov
 */
public class ParsingException extends SQLFrameworkException {

	public ParsingException(String source, String message) {
		super(source, message);
	}
}

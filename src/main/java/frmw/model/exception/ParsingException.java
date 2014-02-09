package frmw.model.exception;

import java.util.Set;

import static java.text.MessageFormat.format;

/**
 * @author Alexey Paramonov
 */
public class ParsingException extends SQLFrameworkException {

	/**
	 * Expected input in {@link #index} place.
	 */
	public final Set<String> expected;

	public ParsingException(int errorAt, Set<String> expected, Exception cause) {
		super(format("{0} expected at position {1}", expected, errorAt), cause);
		this.expected = expected;
		position(errorAt, -1);
	}
}

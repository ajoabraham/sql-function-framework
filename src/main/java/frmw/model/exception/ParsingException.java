package frmw.model.exception;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class ParsingException extends SQLFrameworkException {

	/**
	 * Expected input in {@link #index} place.
	 */
	public final List<String> expected;

	public ParsingException(int errorAt, List<String> expected) {
		this.expected = expected;
		position(errorAt, -1);
	}
}

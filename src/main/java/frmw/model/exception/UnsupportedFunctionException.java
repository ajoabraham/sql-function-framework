package frmw.model.exception;

import static java.text.MessageFormat.format;

/**
 * @author Alexey Paramonov
 */
public class UnsupportedFunctionException extends SQLFrameworkException {

	public final String function;

	public final String dialect;

	public UnsupportedFunctionException(String function, String dialect) {
		super(format("Function {0} is not supported by the {1} dialect", function, dialect));
		this.function = function;
		this.dialect = dialect;
	}
}

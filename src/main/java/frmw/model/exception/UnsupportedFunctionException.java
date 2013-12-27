package frmw.model.exception;

import static java.text.MessageFormat.format;

/**
 * @author Alexey Paramonov
 */
public class UnsupportedFunctionException extends SQLFrameworkException {

	public final String dialect;

	public UnsupportedFunctionException(String function, String dialect) {
		super(function, format("Function {0} is not supported for dialect {1}", function, dialect));
		this.dialect = dialect;
	}
}

package frmw.model.constant;

import frmw.dialect.Dialect;
import frmw.model.FormulaElement;

import java.util.Set;

import static java.lang.Character.isWhitespace;

/**
 * Numbers may contain whitespaces and '_' underscores to define more readable format
 * like '1_234_222_443' instead of '1234222443'.
 *
 * @author Alexey Paramonov
 */
public class NumericConstant implements FormulaElement {

	private final String constant;

	public NumericConstant(String constant) {
		this.constant = cleanUp(constant);
	}

	private static String cleanUp(String constant) {
		StringBuilder sb = new StringBuilder(constant.length());

		for (int i = 0; i < constant.length(); i++) {
			char c = constant.charAt(i);
			if (c != '_' && !isWhitespace(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	@Override
	public void sql(Dialect dialect, StringBuilder sb) {
		// do not check correctness of the input - just lets the database raise error
		sb.append(constant);
	}

	@Override
	public boolean hasAggregation() {
		return false;
	}

	@Override
	public void collectEntities(Set<String> set) {
	}

	@Override
	public String toString() {
		return constant;
	}
}

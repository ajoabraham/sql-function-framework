package frmw.model.hint;

/**
 * @author Alexey Paramonov
 */
public class ArgumentHint {

	public final FunctionSpec function;

	/**
	 * Argument index in provided formula, starts from 0.
	 * May be bigger that {@link FunctionSpec#arguments} size.
	 */
	public final int argumentIndex;

	public ArgumentHint(FunctionSpec function, int argumentIndex) {
		this.function = function;
		this.argumentIndex = argumentIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArgumentHint that = (ArgumentHint) o;
		return argumentIndex == that.argumentIndex && function.equals(that.function);

	}

	@Override
	public int hashCode() {
		int result = function.hashCode();
		result = 31 * result + argumentIndex;
		return result;
	}

	@Override
	public String toString() {
		return "ArgumentHint{" +
				"function=" + function +
				", argumentIndex=" + argumentIndex +
				'}';
	}
}

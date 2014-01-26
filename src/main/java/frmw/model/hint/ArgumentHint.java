package frmw.model.hint;

import frmw.model.fun.FunctionSpec;

/**
 * @author Alexey Paramonov
 */
public class ArgumentHint {

	public final FunctionSpec function;

	/**
	 * Function argument index in provided formula, starts from 0.
	 * May be bigger than {@link FunctionSpec#arguments} size.
	 */
	public final int index;

	/**
	 * Position in string representation of the formula
	 */
	public final int position;

	public ArgumentHint(FunctionSpec function, int index, int position) {
		this.function = function;
		this.index = index;
		this.position = position;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ArgumentHint that = (ArgumentHint) o;
		return index == that.index && position == that.position && function.equals(that.function);
	}

	@Override
	public int hashCode() {
		int result = function.hashCode();
		result = 31 * result + index;
		result = 31 * result + position;
		return result;
	}

	@Override
	public String toString() {
		return "ArgumentHint{" +
				"function=" + function +
				", index=" + index +
				", position=" + position +
				'}';
	}
}

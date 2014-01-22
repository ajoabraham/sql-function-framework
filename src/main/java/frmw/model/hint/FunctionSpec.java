package frmw.model.hint;

import com.google.common.collect.ImmutableList;
import frmw.model.FormulaElement;

import java.util.List;

/**
 * Function specification.
 *
 * @author Alexey Paramonov
 */
public class FunctionSpec {

	/**
	 * Function representation in the model
	 */
	private final Class<? extends FormulaElement> clazz;

	/**
	 * Description of the function arguments.
	 */
	private final List<String> arguments;

	public FunctionSpec(Class<? extends FormulaElement> clazz, List<String> arguments) {
		this.clazz = clazz;
		this.arguments = ImmutableList.copyOf(arguments);
	}

	public FunctionSpec(Class<? extends FormulaElement> clazz, String ...arguments) {
		this.clazz = clazz;
		this.arguments = ImmutableList.copyOf(arguments);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FunctionSpec that = (FunctionSpec) o;
		return clazz.equals(that.clazz) && arguments.equals(that.arguments);

	}

	@Override
	public int hashCode() {
		int result = clazz.hashCode();
		result = 31 * result + arguments.hashCode();
		return result;
	}

	public String name() {
		return clazz.getSimpleName();
	}

	public List<String> arguments() {
		return arguments;
	}

	public Class<? extends FormulaElement> representation() {
		return clazz;
	}

	@Override
	public String toString() {
		return name();
	}
}

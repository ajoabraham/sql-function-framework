package frmw.model.hint;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Function specification.
 *
 * @author Alexey Paramonov
 */
public class FunctionSpec {

	/**
	 * Function name.
	 */
	public final String name;

	/**
	 * Description of the function arguments.
	 */
	public final List<String> arguments;

	public FunctionSpec(String name, List<String> arguments) {
		this.name = name;
		this.arguments = ImmutableList.copyOf(arguments);
	}

	public FunctionSpec(String name, String ...arguments) {
		this.name = name;
		this.arguments = ImmutableList.copyOf(arguments);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FunctionSpec that = (FunctionSpec) o;
		return arguments.equals(that.arguments) && name.equals(that.name);

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + arguments.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return name;
	}
}

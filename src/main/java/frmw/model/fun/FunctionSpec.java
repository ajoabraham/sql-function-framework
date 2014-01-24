package frmw.model.fun;

import com.google.common.collect.ImmutableList;
import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkInternalException;

import java.lang.reflect.Constructor;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Function specification.
 * Contains meta information about function representation.
 * Thread safe.
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
		return clazz.equals(that.clazz);

	}

	@Override
	public int hashCode() {
		return clazz.hashCode();
	}

	public String name() {
		return clazz.getSimpleName();
	}

	public List<String> arguments() {
		return arguments;
	}

	public FormulaElement fakeInstance() {
		Constructor<?> c = clazz.getConstructors()[0];
		int params = c.getParameterTypes().length;

		try {
			Object[] args = new Object[params];
			return (FormulaElement) c.newInstance(args);
		} catch (Exception e) {
			throw SQLFrameworkInternalException.wrap(e);
		}
	}

	public FormulaElement instance(List<Object> args) {
		Constructor<?> constructor = findAppropriate(clazz, args.size());
		if (constructor == null) {
			throw new SQLFrameworkInternalException(format(
					"Function {0} - constructor not found for arguments {1}",
					name(), args));
		}

		try {
			Object[] a = args.toArray(new Object[args.size()]);
			return (FormulaElement) constructor.newInstance(a);
		} catch (Exception e) {
			throw SQLFrameworkInternalException.wrap(e);
		}
	}

	private static Constructor<?> findAppropriate(Class<?> clazz, int argNumber) {
		for (Constructor<?> c : clazz.getConstructors()) {
			if (c.getParameterTypes().length == argNumber) {
				return c;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return name();
	}
}

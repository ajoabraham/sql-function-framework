package frmw.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import frmw.dialect.Dialect;
import frmw.model.exception.UnsupportedFunctionException;
import frmw.model.fun.FunctionSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.ImmutableSet.copyOf;
import static com.google.common.collect.Iterables.filter;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * @author Alexey Paramonov
 */
public class FunctionRegistry {

	/**
	 * cache Dialect class -> all functions supported by this dialect
	 */
	private final ConcurrentHashMap<Class<?>, List<FunctionSpec>> cache = new ConcurrentHashMap<Class<?>, List<FunctionSpec>>();

	private final Set<FunctionSpec> scalarSpecs;

	private final Set<FunctionSpec> aggregationSpecs;

	private final Set<FunctionSpec> olapSpecs;

	private final Iterable<FunctionSpec> all;

	public FunctionRegistry(List<FunctionSpec> aggr, List<FunctionSpec> olap, List<FunctionSpec> scalar) {
		aggregationSpecs = copyOf(aggr);
		scalarSpecs = copyOf(scalar);
		olapSpecs = copyOf(olap);
		all = Iterables.concat(scalarSpecs, aggregationSpecs, olapSpecs);
	}

	public Iterable<FunctionSpec> all() {
		return all;
	}

	public Iterable<FunctionSpec> all(Dialect dialect) {
		Class<? extends Dialect> key = dialect.getClass();
		List<FunctionSpec> cached = cache.get(key);
		if (cached == null) {
			cached = buildFor(dialect);
			cache.putIfAbsent(key, cached);
		}

		return cached;
	}

	private List<FunctionSpec> buildFor(Dialect dialect) {
		List<FunctionSpec> result = new ArrayList<FunctionSpec>();

		for (FunctionSpec spec : all) {
			FormulaElement inst = spec.fakeInstance();

			try {
				inst.sql(dialect, null);
				result.add(spec);
			} catch (UnsupportedOperationException e) {
				// dialect doesn't support this function - just ignore it
			} catch (UnsupportedFunctionException e) {
				// dialect doesn't support this function - just ignore it
			} catch (Exception ignored) {
				// its ok because we works with fake instance
				result.add(spec);
			}
		}

		return result;
	}

	public Iterable<FunctionSpec> scalar() {
		return scalarSpecs;
	}

	public Iterable<FunctionSpec> scalar(Dialect dialect) {
		Iterable<FunctionSpec> all = all(dialect);
		return filter(all, new Predicate<FunctionSpec>() {
			@Override
			public boolean apply(FunctionSpec input) {
				return scalarSpecs.contains(input);
			}
		});
	}

	public Iterable<FunctionSpec> aggregation() {
		return aggregationSpecs;
	}

	public Iterable<FunctionSpec> aggregation(Dialect dialect) {
		Iterable<FunctionSpec> all = all(dialect);
		return filter(all, new Predicate<FunctionSpec>() {
			@Override
			public boolean apply(FunctionSpec input) {
				return aggregationSpecs.contains(input);
			}
		});
	}

	public Iterable<FunctionSpec> olap() {
		return olapSpecs;
	}

	public Iterable<FunctionSpec> olap(Dialect dialect) {
		Iterable<FunctionSpec> all = all(dialect);
		return filter(all, new Predicate<FunctionSpec>() {
			@Override
			public boolean apply(FunctionSpec input) {
				return olapSpecs.contains(input);
			}
		});
	}

	public FunctionSpec byName(String function) {
		for (FunctionSpec spec : all) {
			if (equalsIgnoreCase(spec.name(), function)) {
				return spec;
			}
		}

		return null;
	}
}

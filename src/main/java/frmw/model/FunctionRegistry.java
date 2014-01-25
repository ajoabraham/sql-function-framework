package frmw.model;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import frmw.dialect.Dialect;
import frmw.model.exception.UnsupportedFunctionException;
import frmw.model.fun.FunctionSpec;

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
	private final ConcurrentHashMap<Class<?>, Set<FunctionSpec>> cache = new ConcurrentHashMap<Class<?>, Set<FunctionSpec>>();

	private final Set<FunctionSpec> scalarSpecs;

	private final Set<FunctionSpec> aggregationSpecs;

	private final Set<FunctionSpec> olapSpecs;

	private final Set<FunctionSpec> all;

	public FunctionRegistry(List<FunctionSpec> aggr, List<FunctionSpec> olap, List<FunctionSpec> scalar) {
		aggregationSpecs = copyOf(aggr);
		scalarSpecs = copyOf(scalar);
		olapSpecs = copyOf(olap);
		all = ImmutableSet.<FunctionSpec>builder()
				.addAll(scalarSpecs)
				.addAll(aggregationSpecs)
				.addAll(olapSpecs)
				.build();
	}

	public Set<FunctionSpec> all() {
		return all;
	}

	public Set<FunctionSpec> all(Dialect dialect) {
		Class<? extends Dialect> key = dialect.getClass();
		Set<FunctionSpec> cached = cache.get(key);
		if (cached == null) {
			cached = buildFor(dialect);
			cache.putIfAbsent(key, cached);
		}

		return cached;
	}

	private Set<FunctionSpec> buildFor(Dialect dialect) {
		ImmutableSet.Builder<FunctionSpec> builder = ImmutableSet.builder();

		for (FunctionSpec spec : all) {
			FormulaElement inst = spec.fakeInstance();

			try {
				inst.sql(dialect, null);
				builder.add(spec);
			} catch (UnsupportedOperationException e) {
				// dialect doesn't support this function - just ignore it
			} catch (UnsupportedFunctionException e) {
				// dialect doesn't support this function - just ignore it
			} catch (Exception ignored) {
				// its ok because we works with fake instance
				builder.add(spec);
			}
		}

		return builder.build();
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

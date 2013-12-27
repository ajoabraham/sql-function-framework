package frmw.parser;

import com.google.common.collect.ImmutableSet;
import frmw.model.FormulaElement;
import frmw.model.aggregation.Avg;
import frmw.model.aggregation.Rank;
import frmw.model.aggregation.Sum;
import frmw.model.exception.SQLFrameworkException;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static frmw.parser.Common.*;
import static java.lang.reflect.Modifier.isStatic;

/**
 * @author Alexey Paramonov
 */
class Aggregations {

	private static final Parser<FormulaElement> AVG = fun(Avg.class, COLUMN.or(scalar()));
	private static final Parser<FormulaElement> SUM = fun(Sum.class, COLUMN.or(scalar()));
	private static final Parser<FormulaElement> RANK = fun(Rank.class, COLUMN.or(scalar()));

	public static final Set<String> AGGREGATION_NAMES;

	public static final Parser<FormulaElement> AGGREGATIONS;

	// should be always in the end of the class
	static {
		Field[] fields = Aggregations.class.getDeclaredFields();
		Set<String> functions = new HashSet<String>(fields.length);
		List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>(fields.length);

		for (Field f : fields) {
			if ("AGGREGATIONS".equalsIgnoreCase(f.getName())) {
				continue;
			}

			if (isStatic(f.getModifiers()) && f.getType().isAssignableFrom(Parser.class)) {
				functions.add(f.getName());

				try {
					parsers.add((Parser<FormulaElement>) f.get(null));
				} catch (IllegalAccessException e) {
					throw new SQLFrameworkException(e);
				}
			}
		}

		AGGREGATION_NAMES = ImmutableSet.copyOf(functions);
		AGGREGATIONS = Parsers.or(parsers);
	}
}

package frmw.parser;

import com.google.common.collect.ImmutableList;
import frmw.model.FormulaElement;
import frmw.model.aggregation.*;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;

import java.util.ArrayList;
import java.util.List;

import static frmw.parser.Common.*;

/**
 * @author Alexey Paramonov
 */
class Aggregations {

	public final List<String> names;

	public final Parser<FormulaElement> parser;

	public Aggregations() {
		Builder builder = new Builder();

		this.names = ImmutableList.copyOf(builder.names);
		this.parser = Parsers.or(builder.parsers);
	}

	private static class Builder {

		List<String> names = new ArrayList<String>();
		List<Parser<FormulaElement>> parsers = new ArrayList<Parser<FormulaElement>>();

		Builder() {
			aggr(Avg.class, COLUMN.or(scalar()));
			aggr(Sum.class, COLUMN.or(scalar()));
			aggr(Count.class, COLUMN.or(scalar()));
			aggr(Min.class, COLUMN.or(scalar()));
			aggr(Max.class, COLUMN.or(scalar()));
			aggr(Rank.class, COLUMN.or(scalar()));
		}

		private void aggr(Class<? extends FormulaElement> clazz, Parser<?> arg) {
			Parser<FormulaElement> result = fun(clazz, arg);
			parsers.add(result);
			names.add(funName(clazz));
		}
	}
}

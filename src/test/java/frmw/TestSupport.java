package frmw;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import frmw.dialect.*;
import frmw.model.fun.FunctionSpec;
import frmw.parser.Parsing;

/**
 * @author Alexey Paramonov
 */
public class TestSupport {

	public final static Dialect GENERIC_SQL = new GenericSQL();

	public final static Dialect TERADATA_SQL = new TeradataSQL();

	public final static Dialect TERADATA_14 = new Teradata14SQL();

	public final static Dialect POSTGRESQL = new PostgreSQL();

	public final static Dialect MYSQL = new MySQL();

	public final static Dialect MSSQL = new MSSQL();

	public final static Parsing PARSER = new Parsing();

	public static Iterable<String> names(Iterable<FunctionSpec> specs) {
		return Iterables.transform(specs, new Function<FunctionSpec, String>() {
			@Override
			public String apply(FunctionSpec input) {
				return input.name();
			}
		});
	}
}

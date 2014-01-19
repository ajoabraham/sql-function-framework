package frmw;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import frmw.dialect.Dialect;
import frmw.dialect.GenericSQL;
import frmw.dialect.Teradata14SQL;
import frmw.dialect.TeradataSQL;
import frmw.model.hint.FunctionSpec;
import frmw.parser.Parsing;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class TestSupport {

	public final static Dialect GENERIC_SQL = new GenericSQL();

	public final static Dialect TERADATA_SQL = new TeradataSQL();

	public final static Dialect TERADATA_14 = new Teradata14SQL();

	public final static Parsing PARSER = new Parsing();

	public static List<String> names(List<FunctionSpec> specs) {
		return Lists.transform(specs, new Function<FunctionSpec, String>() {
			@Override
			public String apply(FunctionSpec input) {
				return input.name;
			}
		});
	}
}

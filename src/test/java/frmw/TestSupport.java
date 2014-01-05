package frmw;

import frmw.dialect.Dialect;
import frmw.dialect.GenericSQL;
import frmw.dialect.Teradata14SQL;
import frmw.dialect.TeradataSQL;
import frmw.parser.Parsing;

/**
 * @author Alexey Paramonov
 */
public class TestSupport {

	public final static Dialect GENERIC_SQL = new GenericSQL();

	public final static Dialect TERADATA_SQL = new TeradataSQL();

	public final static Dialect TERADATA_14 = new Teradata14SQL();

	public final static Parsing PARSER = new Parsing();
}

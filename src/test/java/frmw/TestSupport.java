package frmw;

import frmw.dialect.GenericSQL;
import frmw.dialect.TeradataSQL;
import frmw.parser.Parsing;

/**
 * @author Alexey Paramonov
 */
public class TestSupport {

	public final static GenericSQL GENERIC_SQL = new GenericSQL();

	public final static TeradataSQL TERADATA_SQL = new TeradataSQL();

	public final static Parsing PARSER = new Parsing();
}

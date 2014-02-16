package frmw.dialect.teradata;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.PARSER;
import static frmw.TestSupport.TERADATA_14;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class Teradata14Test {

	@Test
	public void replace() {
		Formula f = PARSER.parse("replace(col1, 'old_data', 'new_data')");
		String sql = f.sql(TERADATA_14);
		assertEquals("oreplace(\"col1\" from 'old_data' for 'new_data')", sql);
	}
}

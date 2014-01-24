package frmw.model;

import frmw.model.fun.FunctionSpec;
import org.junit.Test;

import static frmw.TestSupport.*;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author Alexey Paramonov
 */
public class FunctionRegistryTest {

	@Test
	public void teradata14SupportsAllFunctions() {
		Iterable<FunctionSpec> specs = PARSER.registry().all(TERADATA_14);
		assertThat(names(specs), hasItems(
				"Replace", "Index",
				"Exp", "Cos", "Round",
				"NullIfZero",
				"Hour", "AddMonths", "CurrentDate",
				"CustomWindow", "StdDevP"));
	}

	@Test
	public void teradataNotSupportsReplace() {
		Iterable<FunctionSpec> specs = PARSER.registry().all(TERADATA_SQL);
		assertThat(names(specs), not(hasItems("Replace")));
	}

	@Test
	public void teradataSupporting() {
		Iterable<FunctionSpec> specs = PARSER.registry().all(TERADATA_SQL);
		assertThat(names(specs), hasItems(
				"Index",
				"Exp", "Cos", "Round",
				"NullIfZero",
				"Hour", "AddMonths", "CurrentDate",
				"CustomWindow", "StdDevP"));
	}

	@Test
	public void genericSQLSupporting() {
		Iterable<FunctionSpec> specs = PARSER.registry().all(GENERIC_SQL);
		Iterable<String> names = names(specs);

		assertThat(names, hasItems("CustomWindow", "Count", "MovingAvg", "RunningSum", "Trim", "CurrentDate"));
		assertThat(names, not(hasItems("Replace", "Index", "Exp", "Cos", "Round", "NullIfZero", "Hour", "AddMonths", "StdDevP")));
	}
}

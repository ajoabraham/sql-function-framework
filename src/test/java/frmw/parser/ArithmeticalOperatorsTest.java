package frmw.parser;

import frmw.model.Formula;
import org.junit.Test;

import static frmw.TestSupport.GENERIC_SQL;
import static frmw.TestSupport.PARSER;
import static frmw.TestSupport.TERADATA_SQL;
import static org.junit.Assert.assertEquals;

/**
 * @author Alexey Paramonov
 */
public class ArithmeticalOperatorsTest {

	@Test
	public void plus() {
		Formula f = PARSER.parse("name_1 + name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 + name_2)", sql);
	}

	@Test
	public void minus() {
		Formula f = PARSER.parse("name_1 - name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 - name_2)", sql);
	}

	@Test
	public void mul() {
		Formula f = PARSER.parse("name_1 * name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 * name_2)", sql);
	}

	@Test
	public void div() {
		Formula f = PARSER.parse("name_1 / name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 / name_2)", sql);
	}

	@Test
	public void divideMorePriorityThanPlus() {
		Formula f = PARSER.parse("name_0 + name_1 / name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_0 + (name_1 / name_2))", sql);
	}

	@Test
	public void plusMorePriorityThanDivide() {
		Formula f = PARSER.parse("(name_0 + name_1) / name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((name_0 + name_1) / name_2)", sql);
	}

	@Test
	public void multiplyMorePriorityThanMinus() {
		Formula f = PARSER.parse("name_0 - name_1 * name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_0 - (name_1 * name_2))", sql);
	}

	@Test
	public void minusMorePriorityThanMultiply() {
		Formula f = PARSER.parse("(name_0 - name_1) * name_2");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((name_0 - name_1) * name_2)", sql);
	}

	@Test
	public void minusMorePriorityThanMultiply_quoted() {
		Formula f = PARSER.parse("(\"name_0\" - \"name_1\") * \"name_2\"");
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((\"name_0\" - \"name_1\") * \"name_2\")", sql);
	}

	@Test
	public void withFunctions() {
		Formula f = PARSER.parse("abs(sum(name_0 * col3) - ln(avg(name_1 + col1 - col3))) * avg(name_2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs((sum((name_0 * col3)) - ln(avg(((name_1 + col1) - col3))))) * avg(name_2))", sql);
	}

	@Test
	public void plusFunctions() {
		Formula f = PARSER.parse("abs(col1) + ln(col2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) + ln(col2))", sql);
	}

	@Test
	public void minusFunctions() {
		Formula f = PARSER.parse("abs(col1) - ln(col2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) - ln(col2))", sql);
	}

	@Test
	public void multipleFunctions() {
		Formula f = PARSER.parse("abs(col1) * ln(col2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) * ln(col2))", sql);
	}

	@Test
	public void divideFunctions() {
		Formula f = PARSER.parse("abs(col1) / ln(col2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) / ln(col2))", sql);
	}

	@Test
	public void divideMorePriorityThanMinusFunctions() {
		Formula f = PARSER.parse("abs(name_0) - ln(name_1) / exp(name_2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(name_0) - (ln(name_1) / exp(name_2)))", sql);
	}

	@Test
	public void minusMorePriorityThanDivideFunctions() {
		Formula f = PARSER.parse("(abs(name_0) - ln(name_1)) / exp(name_2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("((abs(name_0) - ln(name_1)) / exp(name_2))", sql);
	}

	@Test
	public void multiplyMorePriorityThanPlusFunctions() {
		Formula f = PARSER.parse("abs(name_0) + ln(name_1) * exp(name_2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(name_0) + (ln(name_1) * exp(name_2)))", sql);
	}

	@Test
	public void plusMorePriorityThanMultiplyFunctions() {
		Formula f = PARSER.parse("(abs(name_0) + ln(name_1)) * exp(name_2)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("((abs(name_0) + ln(name_1)) * exp(name_2))", sql);
	}

	@Test
	public void minusInFunction() {
		Formula f = PARSER.parse("abs(name_0 - name_1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((name_0 - name_1))", sql);
	}

	@Test
	public void substractFunctionsInFunction() {
		Formula f = PARSER.parse("abs(abs(name_0) - abs(name_1))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((abs(name_0) - abs(name_1)))", sql);
	}

	@Test
	public void substractDifferentFunctionsInFunction() {
		Formula f = PARSER.parse("abs(abs(name_0) - avg(name_1))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((abs(name_0) - avg(name_1)))", sql);
	}

	@Test
	public void substractScalarAndFunctionInFunction() {
		Formula f = PARSER.parse("abs(name_0 - avg(name_1))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((name_0 - avg(name_1)))", sql);
	}

	@Test
	public void substractFunctionAndScalarInFunction() {
		Formula f = PARSER.parse("abs(ln(name_0) - name_1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((ln(name_0) - name_1))", sql);
	}

	@Test
	public void substractColumnsInFunction() {
		Formula f = PARSER.parse("abs(abs(abs(abs(name_0 - name_1))))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(abs(abs(abs((name_0 - name_1)))))", sql);
	}

	@Test
	public void unaryMinus_beforeColumn() {
		Formula f = PARSER.parse("-col1");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("-col1", sql);
	}

	@Test
	public void unaryPlus_beforeColumn() {
		Formula f = PARSER.parse("+col1");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("col1", sql);
	}

	@Test
	public void unaryMinus_beforeFunction() {
		Formula f = PARSER.parse("-avg(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("-avg(col1)", sql);
	}

	@Test
	public void unaryPlus_beforeFunction() {
		Formula f = PARSER.parse("+avg(col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(col1)", sql);
	}

	@Test
	public void unaryMinus_insideFunction_beforeColumn() {
		Formula f = PARSER.parse("avg(-col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(-col1)", sql);
	}

	@Test
	public void unaryPlus_insideFunction_beforeColumn() {
		Formula f = PARSER.parse("avg(+col1)");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(col1)", sql);
	}

	@Test
	public void unaryMinus_insideFunction_beforeFunction() {
		Formula f = PARSER.parse("avg(-ln(col1))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(-ln(col1))", sql);
	}

	@Test
	public void unaryPlus_insideFunction_beforeFunction() {
		Formula f = PARSER.parse("avg(+ln(col1))");
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(ln(col1))", sql);
	}
}

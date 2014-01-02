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
		Formula f = new Formula("name_1 + name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 + name_2)", sql);
	}

	@Test
	public void minus() {
		Formula f = new Formula("name_1 - name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 - name_2)", sql);
	}

	@Test
	public void mul() {
		Formula f = new Formula("name_1 * name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 * name_2)", sql);
	}

	@Test
	public void div() {
		Formula f = new Formula("name_1 / name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_1 / name_2)", sql);
	}

	@Test
	public void divideMorePriorityThanPlus() {
		Formula f = new Formula("name_0 + name_1 / name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_0 + (name_1 / name_2))", sql);
	}

	@Test
	public void plusMorePriorityThanDivide() {
		Formula f = new Formula("(name_0 + name_1) / name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((name_0 + name_1) / name_2)", sql);
	}

	@Test
	public void multiplyMorePriorityThanMinus() {
		Formula f = new Formula("name_0 - name_1 * name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("(name_0 - (name_1 * name_2))", sql);
	}

	@Test
	public void minusMorePriorityThanMultiply() {
		Formula f = new Formula("(name_0 - name_1) * name_2", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((name_0 - name_1) * name_2)", sql);
	}

	@Test
	public void minusMorePriorityThanMultiply_quoted() {
		Formula f = new Formula("(\"name_0\" - \"name_1\") * \"name_2\"", PARSER);
		String sql = f.sql(GENERIC_SQL);
		assertEquals("((name_0 - name_1) * name_2)", sql);
	}

	@Test
	public void withFunctions() {
		Formula f = new Formula("abs(sum(name_0 * col3) - ln(avg(name_1 + col1 - col3))) * avg(name_2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs((sum((name_0 * col3)) - ln(avg(((name_1 + col1) - col3))))) * avg(name_2))", sql);
	}

	@Test
	public void plusFunctions() {
		Formula f = new Formula("abs(col1) + ln(col2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) + ln(col2))", sql);
	}

	@Test
	public void minusFunctions() {
		Formula f = new Formula("abs(col1) - ln(col2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) - ln(col2))", sql);
	}

	@Test
	public void multipleFunctions() {
		Formula f = new Formula("abs(col1) * ln(col2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) * ln(col2))", sql);
	}

	@Test
	public void divideFunctions() {
		Formula f = new Formula("abs(col1) / ln(col2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(col1) / ln(col2))", sql);
	}

	@Test
	public void divideMorePriorityThanMinusFunctions() {
		Formula f = new Formula("abs(name_0) - ln(name_1) / exp(name_2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(name_0) - (ln(name_1) / exp(name_2)))", sql);
	}

	@Test
	public void minusMorePriorityThanDivideFunctions() {
		Formula f = new Formula("(abs(name_0) - ln(name_1)) / exp(name_2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("((abs(name_0) - ln(name_1)) / exp(name_2))", sql);
	}

	@Test
	public void multiplyMorePriorityThanPlusFunctions() {
		Formula f = new Formula("abs(name_0) + ln(name_1) * exp(name_2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("(abs(name_0) + (ln(name_1) * exp(name_2)))", sql);
	}

	@Test
	public void plusMorePriorityThanMultiplyFunctions() {
		Formula f = new Formula("(abs(name_0) + ln(name_1)) * exp(name_2)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("((abs(name_0) + ln(name_1)) * exp(name_2))", sql);
	}

	@Test
	public void minusInFunction() {
		Formula f = new Formula("abs(name_0 - name_1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((name_0 - name_1))", sql);
	}

	@Test
	public void substractFunctionsInFunction() {
		Formula f = new Formula("abs(abs(name_0) - abs(name_1))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((abs(name_0) - abs(name_1)))", sql);
	}

	@Test
	public void substractDifferentFunctionsInFunction() {
		Formula f = new Formula("abs(abs(name_0) - avg(name_1))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((abs(name_0) - avg(name_1)))", sql);
	}

	@Test
	public void substractScalarAndFunctionInFunction() {
		Formula f = new Formula("abs(name_0 - avg(name_1))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((name_0 - avg(name_1)))", sql);
	}

	@Test
	public void substractFunctionAndScalarInFunction() {
		Formula f = new Formula("abs(ln(name_0) - name_1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs((ln(name_0) - name_1))", sql);
	}

	@Test
	public void substractColumnsInFunction() {
		Formula f = new Formula("abs(abs(abs(abs(name_0 - name_1))))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("abs(abs(abs(abs((name_0 - name_1)))))", sql);
	}

	@Test
	public void unaryMinus_beforeColumn() {
		Formula f = new Formula("-col1", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("-col1", sql);
	}

	@Test
	public void unaryPlus_beforeColumn() {
		Formula f = new Formula("+col1", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("col1", sql);
	}

	@Test
	public void unaryMinus_beforeFunction() {
		Formula f = new Formula("-avg(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("-avg(col1)", sql);
	}

	@Test
	public void unaryPlus_beforeFunction() {
		Formula f = new Formula("+avg(col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(col1)", sql);
	}

	@Test
	public void unaryMinus_insideFunction_beforeColumn() {
		Formula f = new Formula("avg(-col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(-col1)", sql);
	}

	@Test
	public void unaryPlus_insideFunction_beforeColumn() {
		Formula f = new Formula("avg(+col1)", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(col1)", sql);
	}

	@Test
	public void unaryMinus_insideFunction_beforeFunction() {
		Formula f = new Formula("avg(-ln(col1))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(-ln(col1))", sql);
	}

	@Test
	public void unaryPlus_insideFunction_beforeFunction() {
		Formula f = new Formula("avg(+ln(col1))", PARSER);
		String sql = f.sql(TERADATA_SQL);
		assertEquals("avg(ln(col1))", sql);
	}
}

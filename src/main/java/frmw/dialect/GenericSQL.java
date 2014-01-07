package frmw.dialect;

import frmw.model.FormulaElement;
import frmw.model.exception.SQLFrameworkException;
import frmw.model.ifelse.Case;
import frmw.model.ifelse.SimpleCase;
import frmw.model.ifelse.WhenBlock;
import frmw.model.operator.And;
import frmw.model.operator.Or;

import java.util.List;

import static java.util.Arrays.asList;
import static org.codehaus.jparsec.pattern.CharPredicates.IS_ALPHA_;
import static org.codehaus.jparsec.pattern.CharPredicates.IS_ALPHA_NUMERIC_;

/**
 * @author Alexey Paramonov
 */
public class GenericSQL implements Dialect {

	@Override
	public void avg(StringBuilder sb, FormulaElement column) {
		sb.append("avg(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void sum(StringBuilder sb, FormulaElement column) {
		sb.append("sum(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void validateColumnName(String name) {
		if (name == null || name.isEmpty()) {
			throw new SQLFrameworkException(name, "Column name should contain at least one character");
		}

		char firstChar = name.charAt(0);
		boolean firstOk = IS_ALPHA_.isChar(firstChar);
		if (!firstOk) {
			throw new SQLFrameworkException(name, "The first symbol in column name should be or letter [a-zA-Z] or underscore [_], but found : '" + firstChar + "'");
		}

		for (int i = 1; i < name.length(); i++) {
			char ch = name.charAt(i);
			if (!IS_ALPHA_NUMERIC_.isChar(ch)) {
				throw new SQLFrameworkException(name, "Column name should contains numbers [0-9], letters [a-zA-Z] or underscores [_], but found : '" + ch + "'");
			}
		}
	}

	@Override
	public void rank(StringBuilder sb) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void min(StringBuilder sb, FormulaElement column) {
		sb.append("min(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void max(StringBuilder sb, FormulaElement column) {
		sb.append("max(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void count(StringBuilder sb, FormulaElement column) {
		sb.append("count(");
		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void abs(StringBuilder sb, FormulaElement column) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exp(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ln(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void log(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mod(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pow(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void round(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sqrt(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stdDevP(StringBuilder sb, FormulaElement column) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stdDevS(StringBuilder sb, FormulaElement column) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aCos(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aCosH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aSin(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aSinH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aTan(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aTan2(StringBuilder sb, FormulaElement x, FormulaElement y) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void aTanH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cos(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cosH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sin(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sinH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tan(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tanH(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void trim(StringBuilder sb, FormulaElement arg) {
		sb.append("trim(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void extractDateTime(StringBuilder sb, DateTimeElement e, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void currentDate(StringBuilder sb) {
		sb.append("CURRENT_DATE");
	}

	@Override
	public void currentTimestamp(StringBuilder sb) {
		sb.append("CURRENT_TIMESTAMP");
	}

	@Override
	public void addMonths(StringBuilder sb, FormulaElement date, FormulaElement number) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void random(StringBuilder sb, FormulaElement lower, FormulaElement upper) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replace(StringBuilder sb, FormulaElement str, FormulaElement search, FormulaElement replace) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void substring(StringBuilder sb, FormulaElement str, FormulaElement start, FormulaElement length) {
		sb.append("substring(");
		str.sql(this, sb);
		sb.append(" from ");
		start.sql(this, sb);
		sb.append(" for ");
		length.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void index(StringBuilder sb, FormulaElement str, FormulaElement searched) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rightTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void leftTrim(StringBuilder sb, FormulaElement str, FormulaElement trimmed) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void upper(StringBuilder sb, FormulaElement arg) {
		sb.append("upper(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void lower(StringBuilder sb, FormulaElement arg) {
		sb.append("lower(");
		arg.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void searchedCase(StringBuilder sb, Case inst) {
		sb.append("CASE");

		for (WhenBlock when : inst.when) {
			buildWhen(sb, when, inst.elseBlock);
		}

		buildElse(sb, inst);
		sb.append(" END");
	}

	private void buildWhen(StringBuilder sb, WhenBlock block, FormulaElement elseBlock) {
		if (block.when instanceof And) {
			And and = (And) block.when;
			List<WhenBlock> nestedWhen = asList(new WhenBlock(and.right, block.then));
			Case nested = new Case(nestedWhen, elseBlock);

			buildWhen(sb, new WhenBlock(and.left, nested), elseBlock);
		} else if (block.when instanceof Or) {
			Or or = (Or) block.when;
			buildWhen(sb, new WhenBlock(or.left, block.then), elseBlock);
			buildWhen(sb, new WhenBlock(or.right, block.then), elseBlock);
		} else {
			buildWhen(sb, block.when, block.then);
		}
	}

	@Override
	public void simpleCase(StringBuilder sb, SimpleCase inst) {
		sb.append("CASE ");
		inst.caseBlock.sql(this, sb);

		for (WhenBlock when : inst.when) {
			buildWhen(sb, when.when, when.then);
		}

		buildElse(sb, inst);
		sb.append(" END");
	}

	private void buildElse(StringBuilder sb, Case inst) {
		if (inst.elseBlock != null) {
			sb.append(" ELSE ");
			inst.elseBlock.sql(this, sb);
		}
	}

	private void buildWhen(StringBuilder sb, FormulaElement when, FormulaElement then) {
		sb.append(" WHEN ");
		when.sql(this, sb);
		sb.append(" THEN ");
		then.sql(this, sb);
	}
}

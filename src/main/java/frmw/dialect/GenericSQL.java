package frmw.dialect;

import com.google.common.base.Joiner;
import frmw.model.FormulaElement;
import frmw.model.fun.olap.RankParameters;
import frmw.model.fun.olap.WindowParameters;
import frmw.model.fun.olap.support.OrderBy;
import frmw.model.fun.olap.support.Rows;
import frmw.model.ifelse.Case;
import frmw.model.ifelse.SimpleCase;
import frmw.model.ifelse.WhenBlock;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class GenericSQL implements Dialect {

	@Override
	public void avg(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("avg(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void sum(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("sum(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void rank(StringBuilder sb, RankParameters params, FormulaElement orderBy) {
		sb.append("RANK() OVER (");

		List<String> partitions = params.partitions();
		if (!partitions.isEmpty()) {
			sb.append("PARTITION BY ");
			Joiner.on(", ").appendTo(sb, partitions);
			sb.append(' ');
		}

		sb.append("ORDER BY ");
		orderBy.sql(this, sb);
		sb.append(' ').append(params.order()).append(')');
	}

	@Override
	public void min(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("min(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void max(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("max(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void count(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("count(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

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
	public void stdDevS(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("stddev_samp(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

		column.sql(this, sb);
		sb.append(')');
	}

	@Override
	public void stdDevP(StringBuilder sb, FormulaElement column, boolean distinct) {
		sb.append("stddev_pop(");
		if (distinct) {
			sb.append("DISTINCT ");
		}

		column.sql(this, sb);
		sb.append(')');
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
		appendWhenThenElse(sb, inst);
		sb.append(" END");
	}

	@Override
	public void simpleCase(StringBuilder sb, SimpleCase inst) {
		sb.append("CASE ");
		inst.caseBlock.sql(this, sb);

		appendWhenThenElse(sb, inst);
		sb.append(" END");
	}

	private void appendWhenThenElse(StringBuilder sb, Case inst) {
		for (WhenBlock block : inst.when) {
			sb.append(" WHEN ");
			block.when.sql(this, sb);
			sb.append(" THEN ");
			block.then.sql(this, sb);
		}

		if (inst.elseBlock != null) {
			sb.append(" ELSE ");
			inst.elseBlock.sql(this, sb);
		}
	}

	@Override
	public void nullIf(StringBuilder sb, FormulaElement arg, FormulaElement nullable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void nullIfZero(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void zeroIfNull(StringBuilder sb, FormulaElement arg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void window(StringBuilder sb, FormulaElement arg, Rows preceding, Rows following, WindowParameters params) {
		arg.sql(this, sb);
		sb.append(" OVER ").append('(');

		List<String> partitions = params.partitions();
		if (!partitions.isEmpty()) {
			sb.append(" PARTITION BY ");
			Joiner.on(", ").appendTo(sb, partitions);
		}

		List<OrderBy> orders = params.orders();
		if (!orders.isEmpty()) {
			sb.append(" ORDER BY ");

			Iterator<OrderBy> it = orders.iterator();
			while (it.hasNext()) {
				OrderBy orderBy = it.next();
				sb.append(orderBy.column).append(' ').append(orderBy.order);

				if (it.hasNext()) {
					sb.append(", ");
				}
			}
		}

		if (preceding != null && following != null) {
			sb.append(" ROWS BETWEEN ");
			rows(sb, preceding, "PRECEDING");
			sb.append(" AND ");
			rows(sb, following, "FOLLOWING");
		} else if (preceding != null) {
			sb.append(" ROWS ");
			rows(sb, preceding, "PRECEDING");
		} else if (following != null) {
			throw new IllegalArgumentException("Preceding rows value does not set, but following does");
		}

		sb.append(')');
	}

	private static void rows(StringBuilder sb, Rows row, String keyWord) {
		if (row == Rows.ALL) {
			sb.append("UNBOUNDED").append(' ').append(keyWord);
		} else if (row == Rows.CURRENT_ROW) {
			sb.append("CURRENT ROW");
		} else {
			sb.append(row.rowNum).append(' ').append(keyWord);
		}
	}
}

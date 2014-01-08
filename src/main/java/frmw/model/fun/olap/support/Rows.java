package frmw.model.fun.olap.support;

import java.math.BigInteger;

import static java.math.BigInteger.ZERO;

/**
 * @author Alexey Paramonov
 */
public class Rows {

	public static final Rows ALL = new Rows(ZERO);

	public static final Rows CURRENT_ROW = new Rows(ZERO);

	public final BigInteger rowNum;

	public Rows(BigInteger rowNum) {
		this.rowNum = rowNum;
	}
}

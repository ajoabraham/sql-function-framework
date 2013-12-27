package frmw.model;

import frmw.dialect.Dialect;

/**
 * @author Alexey Paramonov
 */
public interface FormulaElement {

	void sql(Dialect dialect, StringBuilder sb);
}

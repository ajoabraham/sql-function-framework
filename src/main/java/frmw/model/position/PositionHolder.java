package frmw.model.position;

import frmw.model.Formula;
import frmw.model.FormulaElement;

import java.util.WeakHashMap;

/**
 * @author Alexey Paramonov
 */
public class PositionHolder {

	public final static PositionHolder INST = new PositionHolder();

	private WeakHashMap<Formula, PositionMap> registry = new WeakHashMap<Formula, PositionMap>();

	private final static ThreadLocal<Formula> currentFormula = new ThreadLocal<Formula>();

	public void currentFormula(Formula f) {
		currentFormula.set(f);
	}

	public void register(FormulaElement res, int index, int length) {
		Formula f = currentFormula.get();
		PositionMap map = registry.get(f);
		if (map == null) {
			map = new PositionMap();
			registry.put(f, map);
		}

		map.add(res, index, length);
	}

	public PositionMap.Position position(Formula f, FormulaElement e) {
		return registry.get(f).find(e);
	}
}

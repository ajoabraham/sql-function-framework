package frmw.model.position;

import frmw.model.FormulaElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexey Paramonov
 */
public class PositionMap {

	public static class Position {
		public final int index;
		public final int length;

		public Position(int index, int length) {
			this.index = index;
			this.length = length;
		}
	}

	private final Map<FormulaElement, Position> positions = new HashMap<FormulaElement, Position>();

	public void add(FormulaElement source, int index, int length) {
		positions.put(source, new Position(index, length));
	}

	public Position find(FormulaElement source) {
		return positions.get(source);
	}
}

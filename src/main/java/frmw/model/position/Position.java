package frmw.model.position;

/**
 * @author Alexey Paramonov
 */
public class Position {

	/**
	 * Index in formula string representation, started from 0.
	 */
	public final int index;

	/**
	 * Length of the formula element in formula string representation.
	 */
	public final int length;

	public Position(int index, int length) {
		this.index = index;
		this.length = length;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Position position = (Position) o;
		return index == position.index && length == position.length;
	}

	@Override
	public int hashCode() {
		int result = index;
		result = 31 * result + length;
		return result;
	}

	@Override
	public String toString() {
		return "Position{" +
				"index=" + index +
				", length=" + length +
				'}';
	}
}

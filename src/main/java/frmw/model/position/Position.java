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
}

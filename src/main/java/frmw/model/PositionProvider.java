package frmw.model;

import frmw.model.position.PositionMap;

/**
 * @author Alexey Paramonov
 */
public class PositionProvider {

	private final PositionMap positions;

	public PositionProvider(PositionMap positions) {
		this.positions = positions;
	}

	public PositionMap elementPositions() {
		return positions;
	}
}

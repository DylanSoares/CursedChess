package org.DevonAndDylan.Pieces;

public class Pawn extends Piece implements MovesOneSpace, MovesTwiceFirstTurn, CanPromote, CanEnPassant {

	public Pawn(Location loc) {
		super(loc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Piece promote(char letter) {
		// TODO Auto-generated method stub
		return null;
	}

}

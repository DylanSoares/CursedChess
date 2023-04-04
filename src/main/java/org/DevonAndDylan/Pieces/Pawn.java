package org.DevonAndDylan.Pieces;

public class Pawn extends Piece implements MovesOneSpace, MovesTwiceFirstTurn, CanPromote, CanEnPassant {

	public Pawn(Location loc, boolean isWhite) { //default pawn
		super(loc, isWhite);
		char[] temp = {'Q', 'R', 'B', 'N'}; // probably needs a better approach
		setPossiblePromotions(temp);
		// TODO Auto-generated constructor stub
	}
	public Pawn(Location loc, boolean isWhite, char[] promos) {
		super(loc, isWhite);
		setPossiblePromotions(promos);
	}

	@Override
	public Piece promote(char letter) {
		// TODO Auto-generated method stub
		return null;
	}

}

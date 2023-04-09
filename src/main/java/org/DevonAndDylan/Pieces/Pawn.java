package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Pawn extends Piece implements MovesOneSpace, MovesTwiceFirstTurn, MovesForward, AttacksDiagonally, CanPromote, CanEnPassant {

	public Pawn(Location loc, boolean isWhite) { //default pawn
		super(loc, isWhite);
		setLetter('P');
		char[] temp = {'Q', 'R', 'B', 'N'}; // probably needs a better approach
		setPossiblePromotions(temp);
	}
	public Pawn(Location loc, boolean isWhite, char[] promos) {
		super(loc, isWhite);
		setPossiblePromotions(promos);
	}

	@Override
	public void promote(char letter) {
		// TODO Auto-generated method stub
	}
	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		ArrayList<Location> output = new ArrayList<Location>();
		Piece[][] board = b.toPieceArray();
		
		
		// TODO Auto-generated method stub
		return output;
	}

}

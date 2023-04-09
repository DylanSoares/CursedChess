package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Queen extends Piece implements MovesCrossShaped, MovesDiagonally {

	public Queen(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('Q');
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

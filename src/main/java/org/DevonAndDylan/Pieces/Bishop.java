package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Bishop extends Piece implements MovesDiagonally {

	public Bishop(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('B');
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Rook extends Piece implements MovesCrossShaped {

	public Rook(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('R');
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

}

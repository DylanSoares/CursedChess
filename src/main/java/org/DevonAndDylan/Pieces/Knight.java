package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Knight extends Piece implements MovesLShaped {

	public Knight(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('N');
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('B');
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		Piece[][] board = b.toPieceArray();
		
		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah

		return new ArrayList<>(checkDiagonal(board, x, y));
	}

}

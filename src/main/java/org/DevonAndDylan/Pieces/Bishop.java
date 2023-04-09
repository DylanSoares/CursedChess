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
		ArrayList<Location> output = new ArrayList<Location>();
		Piece[][] board = b.toPieceArray();
		int width = b.getWidth(); //how many FILES
		int length = b.getLength(); //how many RANKS, typically 8 for both
		
		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah
		
		output.addAll(checkDiagonal(board, x, y));
		
		return output;
	}

}

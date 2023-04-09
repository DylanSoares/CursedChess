package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('Q');
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
		int[] cross = checkCross(board, x, y);
        for (int i = cross[0]; i <= cross[1]; i++) {
            if (i != y) output.add(new Location(x, i, true));
        }
        
        for (int i = cross[2]; i <= cross[3]; i++) {
            if (i != x) output.add(new Location(i, y, true));
        }
        
		return output;
	}
	
}

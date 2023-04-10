package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('N');
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		ArrayList<Location> output = new ArrayList<>();
		Piece[][] board = b.toPieceArray();

		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah

		//this looks complicated but, it's just a fancy way to check all moves where
		//you would move delta 2 and delta 1, as a knight does
		for (int i = 2; i > -3; i--) {
            for (int j = 2; j > -3; j--) {
            	if (Math.abs(i) == 2 ^ Math.abs(j) == 2) { //exclusive or. exactly one is equal to 2
            		if (i != 0 && j != 0) { //neither is equal to zero
            			try {
            				if (board[y + j][x + i] == null ||
            						board[y+j][x+i].isWhite() != this.isWhite()) {
            					output.add(new Location(x+i, y+j, true));
            				}
            			} catch (ArrayIndexOutOfBoundsException ignored) {} //avoid out of bounds

            		}
            	}
            }
		}

		return output;
	}

}

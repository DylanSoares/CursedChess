package org.DevonAndDylan.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

public class King extends Piece implements Serializable {

	public King(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('K');
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		ArrayList<Location> output = new ArrayList<>();
		Piece[][] board = b.toPieceArray();
		int width = b.getWidth(); //how many FILES
		
		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah
		
		for (int i = 1; i > -2; i--) {
            for (int j = 1; j > -2; j--) {
            	if (!(i == 0 && j == 0)) { //not moving nowhere
        			try {
        				if (board[y + j][x + i] == null ||
        						board[y+j][x+i].isWhite() != this.isWhite()) {
        					output.add(new Location(x+i, y+j, true));
        				}
        			} catch (ArrayIndexOutOfBoundsException ignored) {} //avoid out of bounds
            	}
            }
		}
		if (!this.hasMoved()) { //hardcoded castling
			if (board[y][0] instanceof Rook
					&& board[y][0].isWhite() == this.isWhite()
					&& !board[y][0].hasMoved()) {
				boolean block = false;
				for (int i = 1;i<x;i++) {
					if (board[y][i] != null) {
						block = true;
						break;
					}
				}
				if (!block) {
					output.add(new Location(x-2,y,true));
				}
			}
			if (board[y][width-1] instanceof Rook
					&& board[y][width-1].isWhite() == this.isWhite()
					&& !board[y][width-1].hasMoved()) {
				boolean block = false;
				for (int i = width-2;i>x;i--) {
					if (board[y][i] != null) {
						block = true;
						break;
					}
				}
				if (!block) {
					output.add(new Location(x+2,y,true));
				}
			}
		}
		
		return output;
	}

}

package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class King extends Piece {

	public King(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('K');
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
		
		for (int i = 1; i > -2; i--) {
            for (int j = 1; j > -2; j--) {
            	if (!(i == 0 && j == 0)) { //not moving nowhere
        			try {
        				if (!(board[y+j][x+i] instanceof Piece) ||
        						board[y+j][x+i].isWhite() != this.isWhite()) {
        					output.add(new Location(x+i, y+j, true));
        				}
        			} catch (ArrayIndexOutOfBoundsException e) { //avoid out of bounds
        				continue;
        			}
            	}
            }
		}
		
		return output;
	}

}

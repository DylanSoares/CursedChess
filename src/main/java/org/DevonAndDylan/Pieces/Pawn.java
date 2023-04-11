package org.DevonAndDylan.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

public class Pawn extends Piece implements Serializable {

	public Pawn(Location loc, boolean isWhite) { //default pawn
		super(loc, isWhite);
		setLetter('P');
	}

	public Pawn(Location loc, boolean isWhite, boolean moved) {
		super(loc, isWhite);
		setLetter('P');
		setMoved(moved);
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		ArrayList<Location> output = new ArrayList<>();
		Piece[][] board = b.toPieceArray();
		int width = b.getWidth(); //how many FILES
		int length = b.getLength(); //how many RANKS, typically 8 for both

		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah
		if (this.isWhite()) { //we're moving upwards in RANK as we are WHITE
			if (!this.hasMoved()) { //two move boost when HAS NOT MOVED
				if (board[y + 1][x] == null && board[y + 2][x] == null) { //piece in the way
					output.add(new Location(x, y+2, true)); //this isn't checking if the board is too small
					output.add(new Location(x, y+1, true));
				}
			} else {
				if (y+1 < length && board[y + 1][x] == null) {
					output.add(new Location(x, y+1, true));
				}
			}
			if (x+1 < width && y+1 < length && board[y + 1][x + 1] != null) { //capture right
				if (!board[y+1][x+1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x+1, y+1, true));
				}
			}
			if (x-1 >= 0 && y+1 < length && board[y + 1][x - 1] != null) { //capture left
				if (!board[y+1][x-1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x-1, y+1, true));
				}
			}
			//the BEAST of en passant logic. en passant must be done IMMEDIATELY
			if (b.getLastMoveEndLocation() != null) {
				if (x+1 < width && y+1 < length && board[y][x+1] instanceof Pawn) { //en passant right
					if (b.getLastMoveStartLocation().equals(new Location(x+1, y+2, true)) && 
							b.getLastMoveEndLocation().equals(new Location(x+1, y, true)) &&
							!board[y][x+1].isWhite()) {
						output.add(new Location(x+1, y+1, true));
					}
				}
				if (x-1 >= 0 && y+1 < length && board[y][x-1] instanceof Pawn) { //en passant left
					if (b.getLastMoveStartLocation().equals(new Location(x-1, y+2, true)) && 
							b.getLastMoveEndLocation().equals(new Location(x-1, y, true)) &&
							!board[y][x-1].isWhite()) {
						output.add(new Location(x-1, y+1, true));
					}
				}
			}
			
		} else { //we're moving DOWNWARD as we are BLACK
			if (!this.hasMoved()) { //two move boost when HAS NOT MOVED
				if (board[y - 1][x] == null && board[y - 2][x] == null) { //piece in the way
					output.add(new Location(x, y-2, true));
					output.add(new Location(x, y-1, true));
				}
			} else {
				if (y-1 >= 0 && board[y - 1][x] == null) {
					output.add(new Location(x, y-1, true));
				}
			}
			if (x+1 < width && y-1 >= 0 && board[y - 1][x + 1] != null) { //capture right
				if (board[y-1][x+1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x+1, y-1, true));
				}
			}
			if (x-1 >= 0 && y-1 >= 0 && board[y - 1][x - 1] != null) { //capture left
				if (board[y-1][x-1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x-1, y-1, true));
				}
			}
			//the BEAST of en passant logic. en passant must be done IMMEDIATELY
			if (b.getLastMoveEndLocation() != null) {
				if (x+1 < width && y-1 < length && board[y][x+1] instanceof Pawn) { //en passant right
					if (b.getLastMoveStartLocation().equals(new Location(x+1, y-2, true)) && 
							b.getLastMoveEndLocation().equals(new Location(x+1, y, true)) &&
							board[y][x+1].isWhite()) {
						output.add(new Location(x+1, y-1, true));
					}
				}
				if (x-1 >= 0 && y-1 < length && board[y][x-1] instanceof Pawn) { //en passant left
					if (b.getLastMoveStartLocation().equals(new Location(x-1, y-2, true)) && 
							b.getLastMoveEndLocation().equals(new Location(x-1, y, true)) &&
							board[y][x-1].isWhite()) {
						output.add(new Location(x-1, y-1, true));
					}
				}
			}
		}
		return output;
	}

}

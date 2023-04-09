package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Pawn extends Piece {

	public Pawn(Location loc, boolean isWhite) { //default pawn
		super(loc, isWhite);
		setLetter('P');
		char[] temp = {'Q', 'R', 'B', 'N'}; // probably needs a better approach
		setPossiblePromotions(temp);
	}
	public Pawn(Location loc, boolean isWhite, char[] promos) {
		super(loc, isWhite);
		setPossiblePromotions(promos);
	}

	@Override
	public void promote(char letter) {
		// TODO Auto-generated method stub
	}
	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		ArrayList<Location> output = new ArrayList<Location>();
		Piece[][] board = b.toPieceArray();
		int width = b.getWidth(); //how many FILES
		int length = b.getLength(); //how many RANKS, typically 8 for both
		
		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah
		if (this.isWhite()) { //we're moving upwards in RANK as we are WHITE
			if (!this.hasMoved()) { //two move boost when HAS NOT MOVED
				if (!(board[y+1][x] instanceof Piece) || !(board[y+2][x] instanceof Piece)) { //piece in the way
					output.add(new Location(x, y+2, true)); //this isn't checking if the board is too small
					output.add(new Location(x, y+1, true));
				}
			} else {
				if (!(board[y+1][x] instanceof Piece) && y+1 < length) {
					output.add(new Location(x, y+1, true));
				}
			}
			if (x+1 < width && y+1 < length && board[y+1][x+1] instanceof Piece) { //capture right
				if (!board[y+1][x+1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x+1, y+1, true));
				}
			}
			if (x-1 >= 0 && y+1 < length && board[y+1][x-1] instanceof Piece) { //capture left
				if (!board[y+1][x-1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x-1, y+1, true));
				}
			}
			//the BEAST of en passant logic. en passant must be done IMMEDIATELY
			if (b.getLastMoveLocation() instanceof Location) {
				if (x+1 < width && y+1 < length && board[y][x+1] instanceof Pawn) { //en passant right
					if (b.getLastMoveLocation().equals(new Location(x+1, y, true)) &&
							!board[y][x+1].isWhite()) {
						output.add(new Location(x+1, y+1, true));
					}
				}
				if (x-1 >= 0 && y+1 < length && board[y][x-1] instanceof Pawn) { //en passant left
					if (b.getLastMoveLocation().equals(new Location(x-1, y, true)) &&
							!board[y][x-1].isWhite()) {
						output.add(new Location(x-1, y+1, true));
					}
				}
			}
			
		} else { //we're moving DOWNWARD as we are BLACK
			if (!this.hasMoved()) { //two move boost when HAS NOT MOVED
				if (!(board[y-1][x] instanceof Piece) || !(board[y-2][x] instanceof Piece)) { //piece in the way
					output.add(new Location(x, y-2, true));
					output.add(new Location(x, y-1, true));
				}
			} else {
				if (!(board[y-1][x] instanceof Piece) && y-1 >= 0) {
					output.add(new Location(x, y-1, true));
				}
			}
			if (x+1 < width && y-1 >= 0 && board[y-1][x+1] instanceof Piece) { //capture right
				if (board[y-1][x+1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x+1, y-1, true));
				}
			}
			if (x-1 >= 0 && y-1 >= 0 && board[y-1][x-1] instanceof Piece) { //capture left
				if (board[y-1][x-1].isWhite()) { //don't capture your own pieces
					output.add(new Location(x-1, y-1, true));
				}
			}
			//the BEAST of en passant logic. en passant must be done IMMEDIATELY
			if (b.getLastMoveLocation() instanceof Location) {
				if (x+1 < width && y-1 < length && board[y][x+1] instanceof Pawn) { //en passant right
					if (b.getLastMoveLocation().equals(new Location(x+1, y, true)) &&
							board[y][x+1].isWhite()) {
						output.add(new Location(x+1, y-1, true));
					}
				}
				if (x-1 >= 0 && y-1 < length && board[y][x-1] instanceof Pawn) { //en passant left
					if (b.getLastMoveLocation().equals(new Location(x-1, y, true)) &&
							board[y][x-1].isWhite()) {
						output.add(new Location(x-1, y-1, true));
					}
				}
			}
		}
		return output;
	}

}

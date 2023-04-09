package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public abstract class Piece {
	private char letter;
	private int value;
	private boolean isWhite;
	private char[] possiblePromotions;
	private Location loc;
	private boolean moved = false;
	
	/**
	 * Create a piece at a certain location and on a certain team.
	 * <p>
	 * This will just be used as a super call, as the subclasses will
	 * have their own changes to do.
	 * @param loc location of piece
	 * @param isWhite team affiliation. true if white, false if black.
	 */
	public Piece(Location loc, boolean isWhite) {
		this.loc = loc;
		this.isWhite = isWhite;
	}
	public void move(Location loc) {
		this.loc = loc;
		moved = true;
	}
	public Location getLoc() {
		return loc;
	}
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean isWhite() {
		return isWhite;
	}
	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public char[] getPossiblePromotions() {
		return possiblePromotions;
	}
	public void setPossiblePromotions(char[] possiblePromotions) {
		this.possiblePromotions = possiblePromotions;
	}
	public boolean hasMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public void promote(char letter) throws PieceCannotPromoteException { //the piece must override this
		throw new PieceCannotPromoteException(this + " cannot promote.");
	}
	/**
	 * Get all valid moves of a piece.
	 * <p>
	 * Override me, please! I must be overridden to function!
	 * @param b the board. in most cases, "this"
	 * @return a list of locations the piece can move to
	 */
	public abstract ArrayList<Location> getLegalMoves(Board b);
	
	@Override
	public String toString() {
		String piece = "";
		switch(letter) {
		case 'P':
			piece = "Pawn";
			break;
		case 'Q':
			piece = "Queen";
			break;
		case 'K':
			piece = "King";
			break;
		case 'N':
			piece = "Knight";
			break;
		case 'B':
			piece = "Bishop";
			break;
		case 'R':
			piece = "Rook";
			break;
		default:
			piece = "Unknown";
		}
		return piece + " on " + loc;
	}

}

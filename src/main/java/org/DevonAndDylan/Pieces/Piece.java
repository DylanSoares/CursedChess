package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public abstract class Piece {
	private char letter;
	private int value;
	private boolean isWhite;
	private char[] possiblePromotions;
	private Location loc;
	private boolean moved = false;
	
	public Piece(Location loc, boolean isWhite) {
		this.loc = loc;
		this.isWhite = isWhite;
	}
	public void move(Location loc) {
		
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public void capture(Location loc) {
		
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
	public boolean isMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
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

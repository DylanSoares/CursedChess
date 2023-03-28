package org.DevonAndDylan.Pieces;

public abstract class Piece {
	private char letter;
	private int value;
	private boolean isWhite;
	private char[] possiblePromotions;
	private char loc1;
	private int loc2;
	
	
	public void capture(char loc1, int loc2) {
		
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
	
	@Override
	public String toString() {
		return letter + "";
	}
}

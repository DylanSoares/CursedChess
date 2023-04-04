package org.DevonAndDylan.Pieces;

import java.util.ArrayList;

public class Board {
	private int width;
	private int length;
	private ArrayList<Piece> pieces;
	private Piece lastMovePiece;
	private Location lastMoveLocation;
	public Board() {
		this.width = 8;
		this.length = 8;
		populate();
	}
	public Board(ArrayList<Piece> pieces) {
		this.width = 8;
		this.length = 8;
		this.pieces = pieces;
	}
	public Board(int width, int length, ArrayList<Piece> pieces) {
		this.width = width;
		this.length = length;
		this.pieces = pieces;
	}
	private void populate() {
		boolean white = true;
		for (int i=1;i<=width;i+=7) {
			pieces.add(new Rook(new Location('a', i), white));
			pieces.add(new Rook(new Location('h', i), white));
			pieces.add(new Knight(new Location('b', i), white));
			pieces.add(new Knight(new Location('g', i), white));
			pieces.add(new Bishop(new Location('c', i), white));
			pieces.add(new Bishop(new Location('f', i), white));
			pieces.add(new Queen(new Location('d', i), white));
			pieces.add(new King(new Location('e', i), white));
			white = false;
		}
		
		for (int i=1;i<=width;i++) {
			pieces.add(new Pawn(new Location(toChar(i), 2), true));
			pieces.add(new Pawn(new Location(toChar(i), 7), false));
		}
		
	}
	public char toChar(int a) {
		return (char)(a+96);
	}
	public int toInt(char a) {
		return a-96;
	}
}

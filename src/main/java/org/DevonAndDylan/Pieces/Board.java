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
		pieces.add(new Rook(new Location('a', 1), true));
		pieces.add(new Rook(new Location('h', 1), true));
		pieces.add(new Knight(new Location('b', 1), true));
		pieces.add(new Knight(new Location('g', 1), true));
		pieces.add(new Bishop(new Location('c', 1), true));
		pieces.add(new Bishop(new Location('f', 1), true));
		pieces.add(new Queen(new Location('d', 1), true));
		pieces.add(new King(new Location('e', 1), true));
		pieces.add(new Rook(new Location('a', 8), false));
		pieces.add(new Rook(new Location('h', 8), false));
		pieces.add(new Knight(new Location('b', 8), false));
		pieces.add(new Knight(new Location('g', 8), false));
		pieces.add(new Bishop(new Location('c', 8), false));
		pieces.add(new Bishop(new Location('f', 8), false));
		pieces.add(new Queen(new Location('d', 8), false));
		pieces.add(new King(new Location('e', 8), true));
		
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

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
		for (int i=0;i<length;i++) {
			for (int j=0;j<width;j++) {
				//TODO populate board with default settings
			}
		}
		
	}
}

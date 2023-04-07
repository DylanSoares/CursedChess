package org.DevonAndDylan.Pieces;

import java.util.ArrayList;


public class Board {
	private int width;
	private int length;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private Piece lastMovePiece;
	private Location lastMoveLocation;
	private boolean whiteTurn = true;
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
	
	/*public boolean move(String m) {
		char letter = m.charAt(0);
		
	}*/
	
	public boolean move(Location start, Location end) {
		int a = toInt(start.getLoc1()); //start file
		int b = start.getLoc2(); //start rank
		int c = toInt(end.getLoc1()); //end file
		int d = end.getLoc2(); //end rank
		//moving to starting position
		if (start == end) {
			return false;
		}
		//moving out of bounds
		if (a > width || a < 1) {
			return false;
		}
		if (b > length || b < 1) {
			return false;
		}
		if (c > width || c < 1) {
			return false;
		}
		if (d > length || d < 1) {
			return false;
		}
		//see who's moving
		int sindex = -1;
		Piece startPiece = null;
		int eindex = -1;
		boolean capture = false;
		for (int i=0;i<pieces.size();i++) {
			if (pieces.get(i).getLoc().equals(start)) {
				sindex = i;
				startPiece = pieces.get(i);
			} else if (pieces.get(i).getLoc().equals(end)) {
				eindex = i;
				capture = true;
			}
		}
		if (sindex == -1) {
			return false;
		}
		
		
		int moveFile = c-a;
		int moveRank = d-b;
		int deltaFile = Math.abs(moveFile);
		int deltaRank = Math.abs(moveRank);
		if ((deltaFile == 2 && deltaRank == 1) || (deltaFile == 1 && deltaRank == 2)) {
			//knight movement
			if ((startPiece instanceof MovesLShaped)) {
				return true;
			}
		}
		if (deltaFile == 0 && deltaRank > 0 || deltaFile >= 0 && deltaRank == 0) {
			//rook/queen movement
			if ((startPiece instanceof MovesCrossShaped)) {
				//avoid a collision
				if (deltaFile == 0) {
					int lowerBound = Math.min(b+moveRank, b);
					int upperBound = Math.max(b+moveRank, b);
					for (Piece p : pieces) {
						
					}
				} else {
					int lowerBound = Math.min(a+moveFile, a);
					int upperBound = Math.max(a+moveFile, a);
				}
				
				
				return true;
			} 
		} //else if
		return false;
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
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (Piece p : pieces) {
			output.append(p).append("\n");
		}
		return output.toString();
	}

	public ArrayList<Piece> getPieces() {
		return this.pieces;
	}
	
}

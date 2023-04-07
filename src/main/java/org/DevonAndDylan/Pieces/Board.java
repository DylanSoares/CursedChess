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
	
	/**
	 * Return an int confirming if a move was valid and making it internally if so.
	 * <p>
	 * This method will do all the dirty work of checking internally if a given
	 * move is valid, considering from whether or not there's even a piece at the given
	 * location to if there'd be a collision in the way.
	 * <p>
	 * Be sure to refresh your display of the board, however you do it, 
	 * after a piece successfully moves.
	 * 
	 * @param start the location of the piece making the move
	 * @param end the destination the piece is traveling to
	 * @return <b>0</b> -  Successful move
	 * <br><b>1</b> - Piece cannot legally make the move
	 * <br><b>2</b> - Starting and end position are identical
	 * <br><b>3</b> - Move would end up out of bounds
	 * <br><b>4</b> - No piece at the starting location to move
	 * <br><b>5</b> - It is not that player's turn
	 * <br><b>6</b> - Piece would collide while trying to move
	 * <br><b>7</b> - Piece is trying to capture its teammate
	 */
	
	public int move(Location start, Location end) {
		int a = toInt(start.getLoc1()); //start file
		int b = start.getLoc2(); //start rank
		int c = toInt(end.getLoc1()); //end file
		int d = end.getLoc2(); //end rank
		//moving to starting position
		if (start == end) {
			return 2;
		}
		//moving out of bounds
		if (a > width || a < 1) {
			return 3;
		}
		if (b > length || b < 1) {
			return 3;
		}
		if (c > width || c < 1) {
			return 3;
		}
		if (d > length || d < 1) {
			return 3;
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
			return 4;
		}
		//if (startPiece)
		
		
		int moveFile = c-a;
		int moveRank = d-b;
		int deltaFile = Math.abs(moveFile);
		int deltaRank = Math.abs(moveRank);
		//movement check
		if ((deltaFile == 2 && deltaRank == 1) || (deltaFile == 1 && deltaRank == 2)) {
			//knight movement
			if (startPiece instanceof MovesLShaped) {
				if (capture) {
					pieces.remove(eindex);
				}
				pieces.get(sindex).move(end);
				return 0; //knights can jump so no check needed TODO move the pieces
			}
		}
		if (deltaFile == 0 && deltaRank > 0 || deltaFile > 0 && deltaRank == 0) {
			//rook/queen movement
			if (startPiece instanceof MovesCrossShaped) {
				//avoid a collision TODO bug test this stuff
				if (deltaFile == 0) {
					int lowerBound = Math.min(b+moveRank, b);
					int upperBound = Math.max(b+moveRank, b);
					for (Piece p : pieces) {
						int l = p.getLoc().getLoc2();
						if (l < upperBound && l > lowerBound) {
							return 6;
						}
					}
				} else {
					int lowerBound = Math.min(a+moveFile, a);
					int upperBound = Math.max(a+moveFile, a);
					for (Piece p : pieces) {
						int l = toInt(p.getLoc().getLoc1());
						if (l < upperBound && l > lowerBound) {
							return 6;
						}
					}
				}
				if (capture) {
					pieces.remove(eindex);
				}
				pieces.get(sindex).move(end);
				return 0; //done checking collisions and found none
			} 
		}
		if (deltaFile == deltaRank) {
			//bishop/queen movement
			if (startPiece instanceof MovesDiagonally) {
				
			}
		}
		
		
		return 1; // unknown/impossible move
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
	public char[][] toCharArray() {
		char output[][] = new char[length][width];
		for (int i=0;i<length;i++) {
			for (int j=0;j<width;j++) {
				output[i][j] = '0'; //empty
			}
		}
		
		for (Piece p : pieces) {
			Location loc = p.getLoc();
			int number = loc.getLoc2()-1; //-1 to account for index starting at 0
			int letter = toInt(loc.getLoc1())-1;
			output[number][letter] = p.getLetter();
		}
		
		return output;
	}
	
	public Piece[][] toPieceArray() {
		Piece output[][] = new Piece[length][width];
		
		for (Piece p : pieces) {
			Location loc = p.getLoc();
			int number = loc.getLoc2()-1;
			int letter = toInt(loc.getLoc1())-1;
			output[number][letter] = p;
		}
		
		return output;
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

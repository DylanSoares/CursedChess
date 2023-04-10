package org.DevonAndDylan.Pieces;

import java.util.ArrayList;


@SuppressWarnings("CommentedOutCode")
public class Board {
	private final int width;
	private final int length;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private Piece lastMovePiece;
	private Location lastMoveLocation;
	private boolean whiteTurn = true;


	/**
	 * @return true if white, false if black
	 */
	public boolean getWhoseTurn() {
		return whiteTurn;
	}


	public Piece getLastMovePiece() {
		return lastMovePiece;
	}


	public Location getLastMoveLocation() {
		return lastMoveLocation;
	}


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
	 * move is valid, considering from whether there's even a piece at the given
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
	 * <br><b>6</b> - Piece is trying to capture its teammate
	 */
	
	public int move(Location start, Location end) {
		int a = toInt(start.getFile()); //start file
		int b = start.getRank(); //start rank
		int c = toInt(end.getFile()); //end file
		int d = end.getRank(); //end rank
		//moving to starting position
		if (start.equals(end)) {
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
		if (startPiece.isWhite() == !(whiteTurn)) { //should fix issue of black moving whenever
			return 5;
		}
		if (capture && (startPiece.isWhite() == pieces.get(eindex).isWhite())) {
			return 6;
		}
		
		int moveFile = c-a;
		int moveRank = d-b;
		int deltaFile = Math.abs(moveFile);
		int deltaRank = Math.abs(moveRank);
		
		ArrayList<Location> moves = startPiece.getLegalMoves(this);
		for (Location l : moves) {
			//System.out.println(l + " " + end);
			if (l.equals(end)) {
				//special moves
				//en passant... oh god
				if (startPiece instanceof Pawn
						&& deltaFile > 0
						&& !capture) { //a pawn is moving diagonally but no target is found
					//System.out.println("En passant target: " + new Location(c, b, false));
					processEnPassant(end, sindex, new Location(c, b, false));
				}
				//TODO castling
				
				else {
					processMove(end, sindex, eindex, capture);
				}
				return 0;
			}
		}
		return 1; // unknown/impossible move
	}
	private void processEnPassant(Location end, int sindex, Location location) {
		int eindex = -1;
		for (int i=0;i<pieces.size();i++) {
			if (pieces.get(i).getLoc().equals(location)) {
				eindex = i;
				break;
			}
		}
		processMove(end, sindex, eindex, true);
	}


	private void processMove(Location end, int sindex, int eindex, boolean capture) {
		lastMovePiece = pieces.get(sindex);
		lastMoveLocation = end;
		whiteTurn = !whiteTurn;
		pieces.get(sindex).move(end);
		if (capture) {
			pieces.remove(eindex);
		}
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
	public static char toChar(int a) {
		return (char)(a+96);
	}
	public static int toInt(char a) {
		return a-96;
	}
	public char[][] toCharArray() {
		char[][] output = new char[length][width];
		for (int i=0;i<length;i++) {
			for (int j=0;j<width;j++) {
				output[i][j] = '0'; //empty
			}
		}
		
		for (Piece p : pieces) {
			Location loc = p.getLoc();
			int number = loc.getY();
			int letter = loc.getX();
			output[number][letter] = p.getLetter();
		}
		
		return output;
	}
	public int getWidth() {
		return width;
	}



	public int getLength() {
		return length;
	}



	/**
	 * Convert the board into a piece array with empty spaces.
	 * <p>
	 * Do try and remember that editing these pieces is NOT editing those
	 * pieces on the board itself. You MUST interact with the board's own methods
	 * to do so.
	 * @return a 2D array of Piece objects
	 */
	public Piece[][] toPieceArray() {
		Piece[][] output = new Piece[length][width];
		
		for (Piece p : pieces) {
			Location loc = p.getLoc();
			int number = loc.getY(); 
			int letter = loc.getX();
			output[number][letter] = p;
		}
		
		return output;
	}
	
	public void printPieceArray() {
		Piece[][] a = toPieceArray();
		for (int i=length-1;i>=0;i--) {
			for (int j=0;j<width;j++) {
				System.out.print(a[i][j] + " ");
				
			}
			System.out.println();
		}
		System.out.println();
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

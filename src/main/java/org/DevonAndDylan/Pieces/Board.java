package org.DevonAndDylan.Pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings("CommentedOutCode")
public class Board implements Serializable {
	private final int width;
	private final int length;
	private ArrayList<Piece> pieces = new ArrayList<>();
	private Piece lastMovePiece;
	private Location lastMoveStartLocation;
	private Location lastMoveEndLocation;
	private boolean whiteTurn = true;
	private boolean promote = false;
	private int promotePieceIndex;
	private boolean whiteCheck = false;
	private boolean blackCheck = false;
	private double score = -1;
	
	
	/**
	 * @return true if white, false if black
	 */
	public boolean getWhoseTurn() {
		return whiteTurn;
	}
	
	/**
	 * Return the score of the game
	 * @return 1 if white won, 0 if black won, 0.5 if it's a draw, and -1 if the game is in progress
	 */
	public double getScore() {
		return score;
	}
	
	public boolean isWhiteCheck() {
		return whiteCheck;
	}

	public boolean isBlackCheck() {
		return blackCheck;
	}

	/**
	 * Check if we're waiting on a promotion.
	 * @return true when waiting for a promotion
	 */
	public boolean isPromote() {
		return promote;
	}


	public Piece getLastMovePiece() {
		return lastMovePiece;
	}


	public Location getLastMoveEndLocation() {
		return lastMoveEndLocation;
	}
	public Location getLastMoveStartLocation() {
		return lastMoveStartLocation;
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
	
	/**
	 * Return an int confirming if a move was valid and making it internally if so.
	 * <p>
	 * This method will do all the dirty work of checking internally if a given
	 * move is valid, considering from whether there's even a piece at the given
	 * location to if there's being a collision in the way.
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
	 * <br><b>7</b> - A promotion is unresolved. Please call promote().
	 * <br><b>8</b> - Piece cannot legally make the move as the king is in check
	 * <br><b>9</b> - Castling is illegal as you would enter check during the castling
	 * <br><b>10</b> - The game has ended. No more moves can be made.
	 */
	
	public int move(Location start, Location end) {
		if (score != -1) {
			return 10;
		}
		int a = toInt(start.getFile()); //start file
		int b = start.getRank(); //start rank
		int c = toInt(end.getFile()); //end file
		int d = end.getRank(); //end rank
		//waiting on a promotion so all moves are illegal until then
		if (promote) {
			return 7;
		}
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
				if ((startPiece.isWhite() && whiteCheck)
						|| (!startPiece.isWhite() && blackCheck)) { //the player moving is in check
					boolean illegal = false;
					if (startPiece instanceof Pawn
							&& deltaFile > 0
							&& !capture) { //a pawn is moving diagonally but no target is found
						//System.out.println("En passant target: " + new Location(c, b, false));
						illegal = processEnPassantCheck(start, end, sindex, new Location(c, b, false), startPiece.isWhite());
					} else if (startPiece instanceof Pawn
							&& (d == length && startPiece.isWhite())
							|| (d == 1 && !startPiece.isWhite())) {
						illegal = processPromoteCheck(start, end, sindex, eindex, capture, startPiece.isWhite(), false);
					} else if (startPiece instanceof King
							&& deltaFile == 2) {
						return 8; //you can't castle while in check!
					}
					
					else {
						illegal = processMoveCheck(start, end, sindex, eindex, capture, startPiece.isWhite(), false);
					}
					if (illegal)
						return 8;
				} else {
					//special moves
					//en passant... oh god
					if (startPiece instanceof Pawn
							&& deltaFile > 0
							&& !capture) { //a pawn is moving diagonally but no target is found
						//System.out.println("En passant target: " + new Location(c, b, false));
						processEnPassant(start, end, sindex, new Location(c, b, false));
					} else if (startPiece instanceof Pawn
							&& (d == length && startPiece.isWhite())
							|| (d == 1 && !startPiece.isWhite())) {
						processPromote(start, end, sindex, eindex, capture);
					}else if (startPiece instanceof King
							&& deltaFile == 2) {
						if (moveFile > 0) { //castle right
							if (checkIfTargeted(new Location(c-1, d, false), startPiece.isWhite(), this) || checkIfTargeted(end, startPiece.isWhite(), this)) {
								return 9;
							}
							for (int i=0;i<pieces.size();i++) {
								if (pieces.get(i) instanceof Rook
										&& pieces.get(i).getLoc().equals(new Location(width, b, false))) {
									pieces.get(i).move(new Location(c-1, b, false));
								}
							}
						} else { //castle left
							if (checkIfTargeted(new Location(c+1, d, false), startPiece.isWhite(), this) || checkIfTargeted(end, startPiece.isWhite(), this)) {
								return 9;
							}
							for (int i=0;i<pieces.size();i++) {
								if (pieces.get(i) instanceof Rook
										&& pieces.get(i).getLoc().equals(new Location(1, b, false))) {
									pieces.get(i).move(new Location(c+1, b, false));
								}
							}
						}
						processMove(start, end, sindex, eindex, capture);
					}
					
					else {
						processMove(start, end, sindex, eindex, capture);
					}
					
				}
				updateCheck();
				return 0;
			}
		}
		return 1; // unknown/impossible move
	}
	private boolean processEnPassantCheck(Location start, Location end, int sindex, Location location, boolean white) {
		int eindex = -1;
		for (int i=0;i<pieces.size();i++) {
			if (pieces.get(i).getLoc().equals(location)) {
				eindex = i;
				break;
			}
		}
		return processMoveCheck(start, end, sindex, eindex, true, white, false);
	}

	private boolean processPromoteCheck(Location start, Location end, int sindex, int eindex, boolean capture,
			boolean white, boolean test) {
		Location target = null;
		ArrayList<Piece> piecesTemp = new ArrayList<Piece>();
		for (Piece p: pieces) {
			piecesTemp.add((Piece) p.clone());
		}
		piecesTemp.get(sindex).move(end);
		for (Piece p: piecesTemp) {
			if (p instanceof King &&
					p.isWhite() == white) {
				target = p.getLoc();
				break;
			}
		}
		
		if (capture) {
			piecesTemp.remove(eindex);
		}
		if (checkIfTargeted(target, white, new Board(width, length, piecesTemp))) {
			return true;
		}
		if (!test)
			processPromote(start, end, sindex, eindex, capture);
		return false;
	}
	/**
	 * Check if a move is illegal or not.
	 * @param start
	 * @param end
	 * @param sindex
	 * @param eindex
	 * @param capture
	 * @param white
	 * @param test false if you want the move to be processed afterwards
	 * @return true if illegal, false if legal
	 */
	private boolean processMoveCheck(Location start, Location end, int sindex, int eindex, boolean capture,
			boolean white, boolean test) { //temporarily make a move and check if it sticks
		Location target = null;
		ArrayList<Piece> piecesTemp = new ArrayList<Piece>();
		for (Piece p: pieces) {
			piecesTemp.add((Piece) p.clone());
		}
		piecesTemp.get(sindex).move(end);
		for (Piece p: piecesTemp) {
			if (p instanceof King &&
					p.isWhite() == white) {
				target = p.getLoc();
				break;
			}
		}
		
		if (capture) {
			piecesTemp.remove(eindex);
		}
		if (checkIfTargeted(target, white, new Board(width, length, piecesTemp))) {
			return true;
		}
		if (!test)
			processMove(start, end, sindex, eindex, capture);
		return false;
	}
	private void verifyMate() {
		// TODO Auto-generated method stub
		if (whiteTurn) {
			for (int i=0;i<pieces.size();i++) {
				if (pieces.get(i).isWhite()) {
					for (Location l:pieces.get(i).getLegalMoves(this)) {
						int eindex = -1;
						boolean capture = false;
						for (int j=0;j<pieces.size();j++) {
							if (pieces.get(j).getLoc().equals(l)) {
								eindex = j;
								capture = true;
								break;
							}
						}
						if (!processMoveCheck(pieces.get(i).getLoc(), l, i, eindex, capture, whiteTurn, true)) {
							return;
						}
					}
				}
			}
			if (whiteCheck) {
				score = 0; //white is checkmated
			} else {
				score = 0.5; //stalemate
			}
		} else {
			for (int i=0;i<pieces.size();i++) {
				if (!pieces.get(i).isWhite()) {
					for (Location l:pieces.get(i).getLegalMoves(this)) {
						int eindex = -1;
						boolean capture = false;
						for (int j=0;j<pieces.size();j++) {
							if (pieces.get(j).getLoc().equals(l)) {
								eindex = j;
								capture = true;
								break;
							}
						}
						if (!processMoveCheck(pieces.get(i).getLoc(), l, i, eindex, capture, whiteTurn, true)) {
							return;
						}
					}
				}
			}
			if (blackCheck) {
				score = 1; //black is checkmated
			} else {
				score = 0.5; //stalemate
			}
		}
	}

	private void processPromote(Location start, Location end, int sindex, int eindex, boolean capture) {
		lastMovePiece = pieces.get(sindex);
		lastMoveStartLocation = start;
		lastMoveEndLocation = end;
		pieces.get(sindex).move(end);
		if (capture) {
			if (sindex > eindex) {
				sindex--;
			}
			pieces.remove(eindex);
		}
		promotePieceIndex = sindex;
		
		
		promote = true;
	}

	/**
	 * Promotes a piece to a better piece via a given letter
	 *
	 * @param letter standard chess notation of the piece desired
	 */
	public void promote(char letter) {
		if (!promote) {
			return;
		}
		Location promoteLocation = pieces.get(promotePieceIndex).getLoc();
		switch (letter) {
			case 'Q' -> pieces.add(new Queen(promoteLocation, whiteTurn));
			case 'N' -> pieces.add(new Knight(promoteLocation, whiteTurn));
			case 'B' -> pieces.add(new Bishop(promoteLocation, whiteTurn));
			case 'R' -> pieces.add(new Rook(promoteLocation, whiteTurn));
			default -> {
				return;
			}
		}
		pieces.remove(promotePieceIndex);
		whiteTurn = !whiteTurn;
		promote = false;
		updateCheck();
	}
	
	private void processEnPassant(Location start, Location end, int sindex, Location location) {
		int eindex = -1;
		for (int i=0;i<pieces.size();i++) {
			if (pieces.get(i).getLoc().equals(location)) {
				eindex = i;
				break;
			}
		}
		processMove(start, end, sindex, eindex, true);
	}


	private void processMove(Location start, Location end, int sindex, int eindex, boolean capture) {
		lastMovePiece = pieces.get(sindex);
		lastMoveStartLocation = start;
		lastMoveEndLocation = end;
		whiteTurn = !whiteTurn;
		pieces.get(sindex).move(end);
		if (capture) {
			pieces.remove(eindex);
		}
	}
	
	private void updateCheck() {
		Location whiteKing = null;
		Location blackKing = null;
		for (Piece p: pieces) {
			if (p instanceof King) { //hardcoded that kings are the royal piece. w/e
				if (p.isWhite()) {
					whiteKing = p.getLoc();
				} else {
					blackKing = p.getLoc();
				}
			}
		}
		whiteCheck = checkIfTargeted(whiteKing, true, this);
		blackCheck = checkIfTargeted(blackKing, false, this);
		verifyMate();
	}


	private boolean checkIfTargeted(Location target, boolean isWhite, Board b) {
		for (Piece p: b.getPieces()) {
			if (p.isWhite() != isWhite) {
				ArrayList<Location> moves = p.getLegalMoves(b);
				for (Location l : moves) {
					if (l.equals(target)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void setPieces(ArrayList<Piece> pieces) {
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
	public static char toChar(int a) {
		return (char)(a+96);
	}
	public static int toInt(char a) {
		return a-96;
	}
	public char[][] toCharArray() {
		char[][] output = new char[length][width];
		for (int i=0;i<length;i++) {
			//empty
			Arrays.fill(output[i], '0');
		}
		
		for (Piece p : pieces) {
			Location loc = p.getLoc();
			int number = loc.getY();
			int letter = loc.getX();
			if (p.isWhite())
				output[number][letter] = p.getLetter();
			else
				output[number][letter] = Character.toLowerCase(p.getLetter());
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

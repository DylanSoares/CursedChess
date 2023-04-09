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
	
	/**
	 * Check for collisions for a piece that moves in a cross shape.
	 * <p>
	 * Rooks and queens should be calling this.
	 * @param board a 2D piece array of the board
	 * @param x the piece's x position on said array
	 * @param y the piece's y position on said array
	 * @return four integers denoting how far the piece can travel/capture legally
	 */
	public int[] checkCross(Piece[][] board, int x, int y) {
		int lastYabove = 0;
		int lastYbelow = board.length-1;
		int lastXleft = 0;
		int lastXright = board[0].length-1;
		
		for (int i=0;i<y;i++) { //lastYabove
			if (board[i][x] instanceof Piece) {
				if (board[i][x].isWhite() != this.isWhite) {
					lastYabove = i;
				} else {
					lastYabove = i+1;
				}
			}
		}
		for (int i=board.length-1;i>y;i--) { //lastYbelow
			if (board[i][x] instanceof Piece) {
				if (board[i][x].isWhite() != this.isWhite) {
					lastYbelow = i;
				} else {
					lastYbelow = i+1;
				}
			}
		}
		for (int i=0;i<x;i++) { //lastXleft
			if (board[y][i] instanceof Piece) {
				if (board[y][i].isWhite() != this.isWhite) {
					lastXleft = i;
				} else {
					lastXleft = i+1;
				}
			}
		}
		for (int i=board[0].length-1;i>x;i--) { //lastXright
			if (board[y][i] instanceof Piece) {
				if (board[y][i].isWhite() != this.isWhite) {
					lastXright = i;
				} else {
					lastXright = i+1;
				}
			}
		}
		
		int[] output = {lastYabove, lastYbelow, lastXleft, lastXright};
		return output;
	}
	
	
	/**
	 * Check for collisions for a piece that moves diagonally.
	 * <p>
	 * Bishops and queens should be calling this.
	 * @param board a 2D piece array of the board
	 * @param x the piece's x position on said array
	 * @param y the piece's y position on said array
	 * @return An arraylist of all valid locations.
	 */
	public ArrayList<Location> checkDiagonal(Piece[][] board, int x, int y) {
		ArrayList<Location> output = new ArrayList<Location>();
		//true nightmare fuel below
		
        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;
        
        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW] instanceof Piece) {
                if (board[yNW][xNW].isWhite() == this.isWhite) {
                    break;
                } else {
                    output.add(new Location (xNW, yNW, true));
                    break;
                }
            } else {
            	output.add(new Location (xNW, yNW, true));
                yNW--;
                xNW--;
            }
        }
        
        while (xSW >= 0 && ySW < board.length) {
            if (board[ySW][xSW] instanceof Piece) {
                if (board[ySW][xSW].isWhite() == this.isWhite) {
                    break;
                } else {
                    output.add(new Location (xSW, ySW, true));
                    break;
                }
            } else {
            	output.add(new Location (xSW, ySW, true));
                ySW++;
                xSW--;
            }
        }
        
        while (xSE < board[0].length && ySE < board.length) {
            if (board[ySE][xSE] instanceof Piece) {
                if (board[ySE][xSE].isWhite() == this.isWhite) {
                    break;
                } else {
                	output.add(new Location (xSE, ySE, true));
                    break;
                }
            } else {
            	output.add(new Location (xSE, ySE, true));
                ySE++;
                xSE++;
            }
        }
        
        while (xNE < board[0].length && yNE >= 0) {
            if (board[yNE][xNE] instanceof Piece) {
                if (board[yNE][xNE].isWhite() == this.isWhite) {
                    break;
                } else {
                	output.add(new Location (xNE, yNE, true));
                    break;
                }
            } else {
            	output.add(new Location (xNE, yNE, true));
                yNE--;
                xNE++;
            }
        }
        
        return output;
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

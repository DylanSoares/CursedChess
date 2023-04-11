package org.DevonAndDylan.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

public class Rook extends Piece implements Serializable {

	public Rook(Location loc, boolean isWhite) {
		super(loc, isWhite);
		setLetter('R');
	}

	@Override
	public ArrayList<Location> getLegalMoves(Board b) {
		ArrayList<Location> output = new ArrayList<>();
		Piece[][] board = b.toPieceArray();
		
		int x = this.getLoc().getX(); //the FILE
		int y = this.getLoc().getY(); //the RANK. remember that the array is [y][x] because, uh, yeah

		int[] cross = checkCross(board, x, y);
        for (int i = cross[0]; i <= cross[1]; i++) {
            if (i != y) output.add(new Location(x, i, true));
        }
        
        for (int i = cross[2]; i <= cross[3]; i++) {
            if (i != x) output.add(new Location(i, y, true));
        }
        
		return output;
	}

}

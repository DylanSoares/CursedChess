package org.DevonAndDylan.Pieces;


import java.io.Serializable;

public class Location implements Serializable {
	private char file;
	private int rank;
	
	public Location(char file, int rank) {
		this.file = file;
		this.rank = rank;
	}
	/**
	 * Create a location out of an int tuple.
	 * <p>
	 * This will properly convert it into a character when you give it an integer for the file.
	 * @param file the file (a, b, c...)
	 * @param rank the rank (1, 2, 3...)
	 * @param isIndex will add 1 if they're indexes to compensate for the change to chess notation
	 */
	public Location(int file, int rank, boolean isIndex) {
		int bonus = 0;
		if (isIndex)
			bonus = 1;
		this.file = (char)(file+96+bonus); //convert to letter
		this.rank = rank+bonus;
	}

	public char getFile() {
		return file;
	}
	/**
	 * Turn the file into an index starting at 0.
	 * <p>
	 * a -> 0, b -> 1, etc.
	 * @return x index
	 */
	public int getX() {
		return (((int)file) - 96) - 1;
	}

	public void setFile(char file) {
		this.file = file;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 * Turn the rank into an index starting at 0.
	 * <p>
	 * 1 -> 0, 2 -> 1, etc.
	 * @return y index
	 */
	public int getY() {
		return rank - 1;
	}
	
	@Override
	public String toString() {
		return file + "" + rank;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
            return true;
        }
		if (!(o instanceof Location l)) {
            return false;
        }
		return file == l.file && rank == l.rank;
	}
}

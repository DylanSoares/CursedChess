package org.DevonAndDylan.Pieces;



public class Location {
	private char file;
	private int rank;
	
	public Location(char file, int rank) {
		this.file = file;
		this.rank = rank;
	}
	public Location(int file, int loc2) {
		this.file = (char)(file+96); //convert to letter
		this.rank = loc2;
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
		if (!(o instanceof Location)) {
            return false;
        }
		Location l = (Location) o;
		return file == l.file && rank == l.rank;
	}
}

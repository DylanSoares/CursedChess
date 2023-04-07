package org.DevonAndDylan.Pieces;



public class Location {
	private char loc1;
	private int loc2;
	
	public Location(char loc1, int loc2) {
		this.loc1 = loc1;
		this.loc2 = loc2;
	}
	public Location(int loc1, int loc2) {
		this.loc1 = (char)(loc1+96); //convert to letter
		this.loc2 = loc2;
	}

	public char getLoc1() {
		return loc1;
	}

	public void setLoc1(char loc1) {
		this.loc1 = loc1;
	}

	public int getLoc2() {
		return loc2;
	}

	public void setLoc2(int loc2) {
		this.loc2 = loc2;
	}
	
	@Override
	public String toString() {
		return loc1 + "" + loc2;
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
		return loc1 == l.loc1 && loc2 == l.loc2;
	}
}

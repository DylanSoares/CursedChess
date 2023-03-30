package org.DevonAndDylan.Pieces;



public class Location {
	private char loc1;
	private int loc2;
	
	public Location(char loc1, char loc2) {
		this.loc1 = loc1;
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
		return loc1 + loc2 + "";
	}
}

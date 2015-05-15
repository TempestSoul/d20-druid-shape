package com.tempestsoul.dnd.d20;


public enum Size {
	Fine (8, -16, 16, 0.5, 0, null), Diminutive (4, -12, 12, 1, 0, null), Tiny (2, -8, 8, 2.5, 0, null), 
	Small (1, -4, 4, 5, 5, null), Medium (0, 0, 0, 5, 5, 5), 
	Large (-1, 4, -4, 10, 10, 5), Huge (-2, 8, -8, 15, 15, 10), Gargantuan (-4, 12, -12, 20, 20, 15), 
	Colossal (-8, 16, -16, 30, 30, 20);	// actually, anything >= 30 or >= 20, space >= 30
	
	private Size(int sizeMod, int grappleMod, int hideMod, double space, Integer reachTall, Integer reachLong) {
		this.sizeMod = sizeMod;
		this.grappleMod = grappleMod;
		this.hideMod = hideMod;
		this.space = space;
		/* may need wrapper class to hold creature's base reach... */
		this.reachTall = reachTall;
		this.reachLong = reachLong;
	}
	
	private final int sizeMod;
	private final int grappleMod;
	private final int hideMod;
	private final double space;
	private final Integer reachTall;
	private final Integer reachLong;
	
	int getSizeMod() { return sizeMod; }
	int getGrappleMod() { return grappleMod; }
	int getHideMod() { return hideMod; }
	double getSpace() { return space; }
	boolean isTall(Integer reach) { return reach == reachTall; }	// uh, hm. primitives with null...
	boolean isLong(Integer reach) { return reach == reachLong; }	// uh, hm. primitives with null...
	
	Integer getTallReach() { return reachTall; }
	Integer getLongReach() { return reachLong; }
	
}

package com.tempestsoul.dnd.d20.model;

public enum Size {
	Fine (8, -16, 16), Diminutive (4, -12, 12),	Tiny (2, -8, 8), 
	Small (1, -4, 4), Medium (0, 0, 0), 
	Large (-1, 4, -4), Huge (-2, 8, -8), Gargantuan (-4, 12, -12), Colossal (-8, 16, -16);
	
	private Size(int sizeMod, int grappleMod, int hideMod) {
		this.sizeMod = sizeMod;
		this.grappleMod = grappleMod;
		this.hideMod = hideMod;
	}
	
	private final int sizeMod;
	private final int grappleMod;
	private final int hideMod;
	
	int getSizeMod() { return sizeMod; }
	int getGrappleMod() { return grappleMod; }
	int getHideMod() { return hideMod; }
}

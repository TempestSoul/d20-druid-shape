package com.tempestsoul.dnd.d20.model;

import java.util.EnumSet;

public enum Ability {
	STR ("Strength"), DEX ("Dexterity") , CON ("Constitution"), 
	INT ("Intelligence"), WIS ("Wisdom"), CHA ("Charisma");
	
	Ability(String name) { this.name = name; }
	private final String name;
	static EnumSet<Ability> physicalScores = EnumSet.of(STR, DEX, CON);
	static EnumSet<Ability> mentalScores = EnumSet.of(INT, WIS, CHA);
	
	public String getName() { return name; }
}

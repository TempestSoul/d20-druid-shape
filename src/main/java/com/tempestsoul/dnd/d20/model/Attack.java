package com.tempestsoul.dnd.d20.model;

import java.util.EnumSet;

public class Attack {
	
	// Secondary attacks take a -5 on attack rolls, and are used on full attacks only?
	boolean primaryAtk;
	
	// the name of the weapon used
	String sWeapon;
	
	// number of weapons/attacks available (i.e. '2' claws, or '1' bite)
	int iNumber;	// can only use one, unless full attacking
	
	// Hoo boy. Varies.
	EnumSet<DamageType> damageTypes;
	
	// Ex: '1d4'. Not ideal for using with feats like Improved Natural Attack?
	String dmgDie;
	
	// should default to standard critical
	Critical critData;
	
	public Attack() {
		critData = new Critical();
		dmgDie = "0";
		sWeapon = "Touch";	// well, I have to put something!
		primaryAtk = true;	// wait, why do humans treat touch as a primary?
	}
	
	public static enum DamageType {
		Slashing, Bludgeoning, Piercing;
	}
	
	public static class Critical {
		short multiplier;
		short minRange;
		short maxRange;
		
		public Critical() {
			multiplier = 2;
			minRange = 20;
			maxRange = 20;
		}
		
		public Critical(short minRange, short maxRange, short multiplier) {
			this.minRange = minRange;
			this.maxRange = maxRange;
			this.multiplier = multiplier;
		}
		
		@Override
		public String toString() {
			String str = "";
			if(minRange == maxRange)
				str += minRange;
			else
				str += minRange + "-" + maxRange;
			str += "/x" + multiplier;
			return str;
		}
	}
	
	public boolean isPrimaryAtk() {
		return primaryAtk;
	}
	
	public void setPrimaryAtk(boolean primaryAtk) {
		this.primaryAtk = primaryAtk;
	}
	
	public String getName() {
		return sWeapon;
	}
	
	public void setName(String name) {
		this.sWeapon = name;
	}
	
	public int getNumber() {
		return iNumber;
	}
	
	public void setNumber(int count) {
		this.iNumber = count;
	}
	public EnumSet<DamageType> getDamageTypes() {
		return damageTypes;
	}
	
	public void setDamageTypes(EnumSet<DamageType> damageTypes) {
		this.damageTypes = damageTypes;
	}
	
	public String getDmgDie() {
		return dmgDie;
	}
	
	public void setDmgDie(String dmgDie) {
		this.dmgDie = dmgDie;
	}
	
	public Critical getCritData() {
		return critData;
	}
	
	public void setCritData(Critical critData) {
		this.critData = critData;
	}
}

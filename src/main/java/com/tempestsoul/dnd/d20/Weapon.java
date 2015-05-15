package com.tempestsoul.dnd.d20;

import java.util.Set;

public class Weapon {
	String name;
	String baseDamage;	// TODO make an object representing dice pool
	Size weaponSize;
	Set<DamageType> damageTypes;	// EnumSet is made at startup from known values, I think
	boolean isNatural;
	Integer reach;	// should be a multiple of 5 or null
	Critical critData;
	
	public Weapon() {
		// hmm... leave empty, or set defaults?
	}
	
	public Weapon(String name, String baseDamage, Size weaponSize, Set<DamageType> damageTypes) {
		this(name, baseDamage, weaponSize, damageTypes, false);
	}
	
	public Weapon(String name, String baseDamage, Size weaponSize, Set<DamageType> damageTypes, boolean isNatural) {
		this(name, baseDamage, weaponSize, damageTypes, isNatural, weaponSize.getReachTall());
	}
	
	public Weapon(String name, String baseDamage, Size weaponSize, Set<DamageType> damageTypes, boolean isNatural, Integer reach) {
		this(name, baseDamage, weaponSize, damageTypes, isNatural, reach, new Critical());
	}
	
	public Weapon(String name, String baseDamage, Size weaponSize, Set<DamageType> damageTypes, boolean isNatural, Integer reach, Critical critData) {
		this.name = name;
		this.baseDamage = baseDamage;
		this.weaponSize = weaponSize;
		this.damageTypes = damageTypes;
		this.isNatural = isNatural;
		this.reach = reach;
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
	
	public boolean isNatural() { return isNatural; }
	
	public String getName() { return name; }
	public void setName(String weaponName) { name = weaponName; }
	
}
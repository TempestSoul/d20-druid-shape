package com.tempestsoul.dnd.d20;

import java.util.EnumSet;

public class Attack {
	
	// Secondary attacks take a -5 on attack rolls, and are used on full attacks only?
	boolean primaryAtk;
	
	// the weapon used
	Weapon weapon;
	
	// number of weapons/attacks available (i.e. '2' claws, or '1' bite)
	int iNumber;	// can only use one, unless full attacking
	
	public Attack() {}
	public Attack(Weapon weapon) {
		this(1, weapon);
	}
	public Attack(int count, Weapon weapon) {
		this(count, weapon, true);
	}
	public Attack(int count, Weapon weapon, boolean isPrimary) {
		this.iNumber = count;
		this.weapon = weapon;
		this.primaryAtk = isPrimary;
	}
	
	public boolean isPrimaryAtk() {
		return primaryAtk;
	}
	
	public void setPrimaryAtk(boolean primaryAtk) {
		this.primaryAtk = primaryAtk;
	}
	
	public Weapon getWeapon() { return weapon; }
	public void setWeapon(Weapon weapon) { this.weapon = weapon; }
	
	public String getName() {
		return (weapon == null) ? null : weapon.getName();
	}
	
	public int getNumber() {
		return iNumber;
	}
	
	public void setNumber(int count) {
		this.iNumber = count;
	}
	
}

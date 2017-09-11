package com.tempestsoul.dnd.d20.model;

public class Skill {
	private final String name;
	private final Ability baseAbility;
	private Integer ranks = 0;
	private Integer miscBonusTotal = 0;
	private final boolean untrained;

	public Skill(String name, Ability baseAbility) {
		this(name, baseAbility, true);
	}

	public Skill(String name, Ability baseAbility, boolean untrained) {
		this.name = name;
		this.baseAbility = baseAbility;
		this.untrained = untrained;
	}

	public String getName() {
		return name;
	}
	
	public Ability getBaseAbility() {
		return baseAbility;
	}
	
	public Integer getRanks() {
		return ranks;
	}
	
	public void setRanks(Integer ranks) {
		this.ranks = ranks;
	}

	public void setMiscBonusTotal(Integer miscBonusTotal) {
		this.miscBonusTotal = miscBonusTotal;
	}

	public Integer getMiscBonusTotal() {
		return miscBonusTotal;
	}

	public boolean isUntrained() { return untrained; }
	
	@Override
	public boolean equals(Object o) {
		// a skill is equal to another if it has the same name.
		if(o instanceof Skill) {
			Skill s = (Skill) o;
			return this.name.equals(s.name);
		}
		return false;
	}
}

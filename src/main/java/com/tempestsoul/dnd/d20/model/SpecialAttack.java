package com.tempestsoul.dnd.d20.model;

import java.util.Map;

public class SpecialAttack {

	String name;
	SpecialAbilityType type;
	String description;
	boolean savingThrowReq;
	Ability baseSave;
	String saveName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpecialAbilityType getType() {
		return type;
	}

	public void setType(SpecialAbilityType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSavingThrowReq() {
		return savingThrowReq;
	}

	public void setSavingThrowReq(boolean savingThrowReq) {
		this.savingThrowReq = savingThrowReq;
	}

	public Ability getBaseSave() {
		return baseSave;
	}

	public void setBaseSave(Ability baseSave) {
		this.baseSave = baseSave;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	int getSavingThrowDC(int iNumHitDice, Map<Ability, Integer> allScores) throws Exception {
		if(!savingThrowReq)
			throw new Exception("Cannot calculate saving throw for ability without saving throw!");
		// TODO verify algorithm, add in extra bonuses
		return 10 + iNumHitDice /2 + allScores.get(baseSave);
	}
}

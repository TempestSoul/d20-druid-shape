package com.tempestsoul.dnd.d20.model;

/**
 * Hmmm... what did I mean for this to be? I presume it's a level in a class (including monster class)...
 * but this interface makes it look like the definition of a class instead...
 * Corresponds to ClassInfo in HeroForge? (also duplicated by D20ClassData enum...)
 */
public interface HitDieLevel {
	// numHitPointsRolled?
	Integer getHitDieSize();
	
	Integer getBaseAttackBonus(Integer numHitDice);
	
	Integer getBaseFortitudeSave(Integer numHitDice);
	Integer getBaseReflexSave(Integer numHitDice);
	Integer getBaseWillSave(Integer numHitDice);
	
	Integer getSkillPoints(AbilityScore intelligenceScore, Integer numHitDice);
}

package com.tempestsoul.dnd.d20.model;

import java.util.*;
import java.util.stream.Collectors;

import com.tempestsoul.dnd.d20.model.hitdie.DruidHitDieLevel;

public class Creature {
	private String name;

	private Size size;
	private Map<Ability, AbilityScore> stats;
    private CreatureType type;
    private List<CreatureSubType> subTypes;
    private String movement;    // TODO split into pieces?

    Map<Class<? extends HitDieLevel>, Integer> hitDice = new HashMap<>();
    private int iNaturalArmor;

	int iHitPoints;
	//int iDruidLvl;
	//TODO implement
	List<Skill> skills;
	List<String> feats;
	// could be calculated...
	int iBaseAtkBonus; // 3/4 druid level (if only druid); should be set by class level instead?
	int iBaseFort; // good
	int iBaseRef; // poor
	int iBaseWill; // good
	
	// Creature-specific
	private List<Attack> attacks;
	private List<SpecialAttack> specialAtks;
	
	public Creature() { }
	public Creature(Creature c) {
		// TODO update with new fields
		name = c.name;
		iHitPoints = c.iHitPoints;
		type = c.type;
		subTypes = new ArrayList<CreatureSubType>(c.subTypes);
		size = c.size;
		stats = new HashMap<Ability, AbilityScore>(c.stats);
		skills = new ArrayList<Skill>(c.skills);
		if (c.feats != null) {
			feats = new ArrayList<String>(c.feats);
		} else {
			feats = new ArrayList<>();
		}
		iBaseAtkBonus = c.iBaseAtkBonus;
		iBaseFort = c.iBaseFort;
		iBaseRef = c.iBaseRef;
		iBaseWill = c.iBaseWill;
		if (c.attacks != null) {
			attacks = new ArrayList<Attack>(c.attacks);
		} else {
			attacks = new ArrayList<>();
		}
		if (c.specialAtks != null) {
			specialAtks = new ArrayList<SpecialAttack>(c.specialAtks);
		} else {
			specialAtks = new ArrayList<>();
		}
		movement = c.movement;
		iNaturalArmor = c.iNaturalArmor;
	}

	public Integer findNumberOfHitDie() {
		return hitDice.values().stream().collect(Collectors.summingInt(Integer::intValue));
	}

    public Integer findNumberOfHitDie(HitDieLevel hitDieLevel) {
        Integer numHitDie = hitDice.get(hitDieLevel.getClass());
        return numHitDie == null ? 0 : numHitDie;
    }

	public void setPhysicalScores(Map<Ability, AbilityScore> src) {
		for(Ability stat : Ability.physicalScores) {
			stats.put(stat, src.get(stat));
		}
	}
	
	public void setMentalScores(Map<Ability, AbilityScore> src) {
		for(Ability stat : Ability.mentalScores) {
			stats.put(stat, src.get(stat));
		}
	}

	public Integer getInitiative() {
		AbilityScore score = stats.get(Ability.DEX);
		if(score == null)
			return null;
		return score.getModifier(); // + miscellaneous
	}
	
	public Integer getFortSave() {
		AbilityScore score = stats.get(Ability.CON);
		if(score == null) 
			return null;
		return iBaseFort + score.getModifier(); // + miscellaneous
	}
	
	public Integer getRefSave() {
		AbilityScore score = stats.get(Ability.DEX);
		if(score == null) 
			return null;
		return iBaseRef + score.getModifier(); // + miscellaneous
	}
	
	public Integer getWillSave() {
		AbilityScore score = stats.get(Ability.WIS);
		if(score == null) 
			return null;
		return iBaseWill + score.getModifier(); // + miscellaneous
	}
	
	public int getArmorCount() {
		//System.out.print(size.getSizeMod() + "+" + stats.get(Ability.DEX) + "+" + iNaturalArmor + "=");
		return 10 + stats.get(Ability.DEX).getModifier() + iNaturalArmor + size.getSizeMod();	// + dodge + misc
	}
	
	public int getTouchArmorCount() {
		//System.out.print(stats.get(Ability.DEX).getModifier() + "+" + size.getSizeMod() + "=");
		return 10 + stats.get(Ability.DEX).getModifier() + size.getSizeMod();	// + dodge + misc
	}
	
	public int getFlatArmorCount() {
		//System.out.print(iNaturalArmor + "+" + size.getSizeMod() + "=");
		int dexMod = stats.get(Ability.DEX).getModifier();
		return 10 + (dexMod < 0 ? dexMod : 0) + iNaturalArmor + size.getSizeMod();	// + misc
	}

	public int getGrappleBonus() {
		return getBaseAtkBonus() + stats.get(Ability.STR).getModifier() + size.getGrappleMod();	// + misc?
	}

	public int getAttackBonus(Attack attack) {
		// if ranged or Weapon Finesse + light weapon, use Dex; else Str
		// if MultiAttack, -2 instead of -5 for secondary natural attacks
		return getBaseAtkBonus() + stats.get(Ability.STR).getModifier() + size.getSizeMod() + (attack.isPrimaryAtk() ? 0 : -5);
	}

	public int getDamageBonus(Attack attack) {	// if only 1 natural primary, then 1.5x str
		return attack.isPrimaryAtk() ? stats.get(Ability.STR).getModifier() : stats.get(Ability.STR).getModifier()/2;
	}

	public int getSaveDC(SpecialAttack specialAttack) {
		return specialAttack.getSavingThrowDC(hitDice.values().stream().mapToInt(Integer::intValue).sum(), getStats());
	}
	
	public boolean isAquatic() {
		return subTypes != null && subTypes.contains(CreatureSubType.Aquatic);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getNumHitDice() {
		return findNumberOfHitDie();
	}
	
	public int getHitPoints() {
		return iHitPoints;
	}
	
	public void setHitPoints(int hitPoints) {
		this.iHitPoints = hitPoints;
	}
	
	public int getDruidLvl() {
		return getClassLvl(DruidHitDieLevel.class);
	}

	public int getClassLvl(Class<? extends HitDieLevel> hitDieClass) {
		Integer numHitDie = hitDice.get(hitDieClass);
		return numHitDie == null ? 0 : numHitDie;
	}

	public void setClassLvl(Class<? extends HitDieLevel> hitDieClass, int numHitDie) {
		hitDice.put(hitDieClass, numHitDie);
	}
	
	public CreatureType getType() {
		return type;
	}
	
	public void setType(CreatureType type) {
		this.type = type;
	}
	
	public List<CreatureSubType> getSubTypes() {
		return subTypes;
	}
	
	public void setSubTypes(List<CreatureSubType> subTypes) {
		this.subTypes = subTypes;
	}
	
	public Size getSize() {
		return size;
	}
	
	public void setSize(Size size) {
		this.size = size;
	}
	
	public Map<Ability, AbilityScore> getStats() {
		return stats;
	}
	
	public void setStats(Map<Ability, AbilityScore> stats) {
		this.stats = stats;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	/**
	 * 
	 * @param skillRanks
	 */
	public void setSkillRanks(Map<String, Integer> skillRanks) {
		for(String skillName : skillRanks.keySet()) {
			Skill skill = getSkillByName(skillName);
			if(skill != null) {
				skill.setRanks(skillRanks.get(skillName));
			} else {
				throw new IllegalArgumentException("No skill called " + skillName);
			}
		}
	}
	
	// should really think about some kind of skill wrapper or way to access the map...
	public Skill getSkillByName(String skillName) {
		if(skills == null)
			return null;
		for(Skill skill : skills) {
			if(skill.getName().equalsIgnoreCase(skillName))
				return skill;
		}
		return null;
	}
	
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public Integer getSkillMod(String skillName) {
		Skill skill = getSkillByName(skillName);
		return getSkillMod(skill);
	}

	public Integer getSkillMod(Skill skill) {
		if (skill == null) return 0;
		else {
			AbilityScore score = getStats().get(skill.getBaseAbility());
			return score != null ? score.getModifier() + skill.getRanks() + skill.getMiscBonusTotal() : skill.getRanks() + skill.getMiscBonusTotal();
		}
	}
	
	public List<String> getFeats() {
		return feats;
	}
	
	public void setFeats(List<String> feats) {
		this.feats = feats;
	}
	
	public int getBaseAtkBonus() {
		return iBaseAtkBonus;
	}
	
	public void setBaseAtkBonus(int baseAtkBonus) {
		this.iBaseAtkBonus = baseAtkBonus;
	}
	
	public int getBaseFort() {
		return iBaseFort;
	}
	
	public void setBaseFort(int iBaseFort) {
		this.iBaseFort = iBaseFort;
	}
	
	public int getBaseRef() {
		return iBaseRef;
	}
	
	public void setBaseRef(int iBaseRef) {
		this.iBaseRef = iBaseRef;
	}
	
	public int getBaseWill() {
		return iBaseWill;
	}
	
	public void setBaseWill(int iBaseWill) {
		this.iBaseWill = iBaseWill;
	}
	
	public List<Attack> getAttacks() {
		return attacks;
	}
	
	public void setAttacks(List<Attack> attacks) {
		this.attacks = attacks;
	}
	
	public List<SpecialAttack> getSpecialAtks() {
		return specialAtks;
	}
	
	public void setSpecialAtks(List<SpecialAttack> specialAtks) {
		this.specialAtks = specialAtks;
	}
	
	public String getMovement() {
		return movement;
	}
	
	public void setMovement(String movement) {
		this.movement = movement;
	}
	
	public int getNaturalArmor() {
		return iNaturalArmor;
	}
	
	public void setNaturalArmor(int naturalArmor) {
		this.iNaturalArmor = naturalArmor;
	}

	public String toString() {
		return String.format("%s (%s %s)", getName(), getSize().name(), getType().name());
	}
}

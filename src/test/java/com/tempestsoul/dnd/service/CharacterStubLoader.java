package com.tempestsoul.dnd.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tempestsoul.dnd.d20.model.Ability;
import com.tempestsoul.dnd.d20.model.AbilityScore;
import com.tempestsoul.dnd.d20.model.Creature;
import com.tempestsoul.dnd.d20.model.CreatureSubType;
import com.tempestsoul.dnd.d20.model.CreatureType;
import com.tempestsoul.dnd.d20.model.Size;
import com.tempestsoul.dnd.d20.model.hitdie.DruidHitDieLevel;
import com.tempestsoul.dnd.d20.model.hitdie.FighterHitDieLevel;

public class CharacterStubLoader implements CharacterLoadService {

	@Override
	public Creature loadCharacter(File file) {
		Creature druid = new Creature();
		druid.setClassLvl(DruidHitDieLevel.class, 8);
		druid.setClassLvl(FighterHitDieLevel.class, 2);
		druid.setBaseAtkBonus(6);
		druid.setBaseFort(6);
		druid.setBaseRef(2);
		druid.setBaseWill(6);
		druid.setFeats(Arrays.asList("Barf", "Combat Expertise", "Superior Two-Weapon Fighting"));
		druid.setHitPoints(5);	// somebody's suffering
		druid.setMovement("30 ft, fly 2000000000 mi");
		druid.setName("Spawny McCamperson");// <br/> someone isn't checking injected strings");
		druid.setNaturalArmor(0);
		//druid.setNumHitDice(10);
		druid.setSize(Size.Large);
		druid.setType(CreatureType.DRAGON);
		druid.setSubTypes(Arrays.asList(CreatureSubType.Fire, CreatureSubType.Evil));
		Map<Ability, AbilityScore> stats = new HashMap<Ability, AbilityScore>();
		stats.put(Ability.STR, new AbilityScore(10));
		stats.put(Ability.DEX, new AbilityScore(11));
		stats.put(Ability.CON, new AbilityScore(20));
		stats.put(Ability.INT, new AbilityScore(8));
		stats.put(Ability.WIS, new AbilityScore(32));
		stats.put(Ability.CHA, new AbilityScore(5));
		druid.setStats(stats);
		// TODO attacks, special attacks
		return druid;
	}
	
	/**
	 * An example of how to use the service for simple testing.
	 * @param args
	 */
	public static void main(String[] args) {
		// Use interface for variable type so we can easily switch out the 
		// stub for the real deal
		CharacterLoadService loadService = new CharacterStubLoader();
		// Get a creature loaded. Let's assume we don't have file system working yet.
		// so... use a null reference (normally, wouldn't work)
		Creature nullDruid = loadService.loadCharacter(null);
		// Now, what if we had an actual file?
		File file = new File("src/test/resources/Nimue  (Lvl 8)_v6.1.0.1.b2.14(D&D 3.5).hfg");
		Creature nimueDruid = loadService.loadCharacter(file);
		
		if(nimueDruid.equals(nullDruid)) {
			System.out.println("They're the same thing!");
		} else {
			System.out.println("Different files make different druids! Not a stub because it's doing work!");
		}
		
		// Prove we can use the nullDruid object
		System.out.println(nullDruid.getName());
		System.out.println("Druid level: " + nullDruid.getDruidLvl());
		System.out.println("Druid Scores:");
		// print scores in order declared in Ability enum
		for(Ability stat : Ability.values()) {
			System.out.println("\t" + stat.getName() + ": " + nullDruid.getStats().get(stat));
		}
		System.out.println("Speed: " + nullDruid.getMovement());
		// bla bla, keep testing functions to see if they work
	}

}

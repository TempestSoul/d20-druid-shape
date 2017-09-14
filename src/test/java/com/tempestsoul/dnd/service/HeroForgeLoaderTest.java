package com.tempestsoul.dnd.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import com.tempestsoul.dnd.d20.model.Ability;
import org.junit.Test;

import com.tempestsoul.dnd.d20.model.Creature;
import com.tempestsoul.dnd.d20.model.CreatureType;
import com.tempestsoul.dnd.d20.model.Size;

public class HeroForgeLoaderTest {

	HeroForgeLoadService service = new HeroForgeLoadService();

	@Test
	public void testLoadCharacter() {
		File testFile = new File("src/test/resources/Nimue  (Lvl 8)_v6.1.0.1.b2.14(D&D 3.5).hfg");
		assertTrue("Test file does not exist", testFile.exists());
		Creature nimue = service.loadCharacter(testFile);
		
		assertNotNull("Did not build character", nimue);
		assertEquals("Nimue ", nimue.getName());	// no, I don't know why she put an extra space in there
		assertEquals(8, nimue.getDruidLvl());
		assertEquals(Size.Medium, nimue.getSize());
		assertEquals(CreatureType.Humanoid, nimue.getType());
		assertNotNull(nimue.getStats());
		assertEquals(51, nimue.getHitPoints());
		assertEquals(6, nimue.getBaseAtkBonus());
		assertEquals(6, nimue.getBaseFort());
		assertEquals(2, nimue.getBaseRef());
		assertEquals(6, nimue.getBaseWill());
		assertEquals(7, nimue.getFortSave().intValue());
		assertEquals(4, nimue.getRefSave().intValue());
		assertEquals(11, nimue.getWillSave().intValue());
		// TODO create tests (expected, actual)
		// test for racial skill enhancements
//		assertEquals(new Integer(2), nimue.getSkillByName("Listen").getMiscBonusTotal());
//		assertEquals(new Integer(2), nimue.getSkillByName("Search").getMiscBonusTotal());
//		assertEquals(new Integer(2), nimue.getSkillByName("Spot").getMiscBonusTotal());
		// test for class skill enhancements (ugh)
		//assertEquals(new Integer(2), nimue.getSkillByName("Knowledge (nature)").getMiscBonusTotal());
//		assertEquals(new Integer(2), nimue.getSkillByName("Survival").getMiscBonusTotal());
		// test for parsed skill ranks
		assertEquals(new Integer(9), nimue.getSkillByName("Survival").getRanks());	// class
		assertEquals(Ability.DEX, nimue.getSkillByName("Move Silently").getBaseAbility());
		assertEquals(new Integer(1), nimue.getSkillByName("Move Silently").getRanks());	// cross-class
		// test for loaded feats
	}
}

package com.tempestsoul.dnd.service.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tempestsoul.dnd.d20.Ability;
import com.tempestsoul.dnd.d20.AbilityScore;
import com.tempestsoul.dnd.d20.Attack;
import com.tempestsoul.dnd.d20.Creature;
import com.tempestsoul.dnd.d20.CreatureType;
import com.tempestsoul.dnd.d20.Size;

public class ShapeListParserTest {

	ShapeListParser parser;
	
	@Before
	public void setUp() {
		parser = new ShapeListParser();
	}
	
	@Test
	public void testNullStream() {
		assertNull(parser.parseCreatureFile(null));
	}
	
	@Test
	public void testParseEmptyLine() {
		assertNull(parser.parseLine(null));
		assertNull(parser.parseLine(""));
	}
	
	@Test
	public void testParseComment() {
		// valid line, but shouldn't be a creature
		assertNull(parser.parseLine("# this is a comment"));
	}
	
	@Test
	public void testParseBasic() {
		Creature dog = parser.parseLine("Dog|1|Small|Animal|13/17/15|1|m40|5/5|bite(p1d4)|");
		assertNotNull("Creature should not be null", dog);
		assertEquals(Size.Small, dog.getSize());
		assertEquals(CreatureType.ANIMAL, dog.getType());
		Map<Ability, AbilityScore> stats = dog.getStats();
		assertNotNull(stats);
		assertEquals(13, stats.get(Ability.STR).getScore().intValue());
		assertEquals(17, stats.get(Ability.DEX).getScore().intValue());
		assertEquals(15, stats.get(Ability.CON).getScore().intValue());
		assertEquals(1, dog.getNaturalArmor());
		assertEquals(1, dog.getAttacks().size());
	}
	
	@Test
	public void testParseFractionalHD() {
		Creature c = parser.parseLine("Fake Rat|0.25|Tiny|Animal|2/15/10|0|m15|5/0|bite(p1d3)|");
		assertNotNull("Creature should not be null", c);
		assertTrue(0.25 == c.getNumHitDice());
		c = parser.parseLine("Fake Rat|1/4|Tiny|Animal|2/15/10|0|m15|5/0|bite(p1d3)|");
		assertNotNull(c);
		assertTrue(0.25 == c.getNumHitDice());
	}
	
	@Test
	public void testParseMoveSpeeds() {
		// May want to add square translations, or make into struct
		// Move
		Creature c = parser.parseLine("Dog|1|Small|Animal|13/17/15|1|m40|5/5|bite(p1d4)|");
		assertNotNull("Creature should not be null", c);
		assertEquals("40'", c.getMovement());
		// Swim
		c = parser.parseLine("Fish|1|Small|Animal|10/15/12|1|s30(average)|5/5|bite(s1d3)|");
		assertNotNull(c);
		assertEquals("swim 30'", c.getMovement());
		// Burrow
		c = parser.parseLine("Dire Mole|1|Small|Animal|13/17/15|1|m20b40|5/5|bite(p1d4)|");
		assertNotNull(c);
		assertEquals("20', burrow 40'", c.getMovement());
		// Flight
		c = parser.parseLine("Eagle|1|Small|Animal|10/15/12|1|m10f80(average)|5/5|2talon(p1d4)+bite(s1d4)|");
		assertNotNull(c);
		assertEquals("10', fly 80' (average)", c.getMovement());
	}
	
	@Test
	public void testParseAttacks() {
		Creature c = parser.parseLine("Fake Leopard|3|Medium|Animal|16/19/15|1|m40|5/5|bite(p1d6)+2claw(s3d3)|");
		assertNotNull("Creature should not be null", c);
		assertEquals(2, c.getAttacks().size());
		Attack biteAtk = c.getAttacks().get(0);
		assertEquals("bite", biteAtk.getName());
		assertEquals(1, biteAtk.getNumber());
		assertEquals(true, biteAtk.isPrimaryAtk());
		Weapon bite = biteAtk.getWeapon();
		assertEquals("bite", bite.getName());
		assertEquals(new Weapon.Critical(), bite.getCritData());
		assertEquals("1d6", bite.getDmgDie());
		assertEquals(true, bite.isNatural);
		Attack clawAtk = c.getAttacks().get(1);
		assertEquals("claw", clawAtk.getName());
		assertEquals(2, clawAtk.getNumber());
		assertEquals(false, clawAtk.isPrimaryAtk());
		Weapon claw = clawAtk.getWeapon();
		assertEquals("3d3", claw.getDmgDie());
	}
	
	// I have no idea how special attacks will work yet, so ignore this test
	@Ignore
	@Test
	public void testParseSpecialAttacks() {
		// Behold the might Omnimal! It should be split into separate tests for safekeeping!
		Creature c = parser.parseLine("Omnimal|3|Medium|Animal|16/19/15|1|m40|5/5|bite(p1d6)+2claw(s1d3)|" +
				"improved Grab[bite,rake],Pounce,Rake[s1d3],Poison[1d6 Con,1d6 Con,Fort neg/Con]");
		assertNotNull("Creature should not be null", c);
		// TODO add tests
		// case insensitive matching, but use specific case for output?
		// examine description for attacks, make sure they have right damage or attacks
		// verify that saves are properly injecting (and calculating?)
	}
	
	@Ignore
	@Test
	public void testParseStream() {
		// use src/test/resources/test-shapes.csv
	}
}

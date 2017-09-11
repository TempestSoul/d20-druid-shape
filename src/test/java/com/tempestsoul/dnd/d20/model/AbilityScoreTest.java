package com.tempestsoul.dnd.d20.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbilityScoreTest {

    @Test
    public void testGetModifier_Positive() {
        AbilityScore score = new AbilityScore(10);
        assertEquals(Integer.valueOf(0), score.getModifier());
        score = new AbilityScore(11);
        assertEquals(Integer.valueOf(0), score.getModifier());
        score = new AbilityScore(12);
        assertEquals(Integer.valueOf(1), score.getModifier());
        score = new AbilityScore(13);
        assertEquals(Integer.valueOf(1), score.getModifier());
        score = new AbilityScore(14);
        assertEquals(Integer.valueOf(2), score.getModifier());
        score = new AbilityScore(15);
        assertEquals(Integer.valueOf(2), score.getModifier());
    }

    @Test
    public void testGetModifier_Negative() {
        AbilityScore score = new AbilityScore(9);
        assertEquals(Integer.valueOf(-1), score.getModifier());
        score = new AbilityScore(8);
        assertEquals(Integer.valueOf(-1), score.getModifier());
        score = new AbilityScore(7);
        assertEquals(Integer.valueOf(-2), score.getModifier());
        score = new AbilityScore(6);
        assertEquals(Integer.valueOf(-2), score.getModifier());
        score = new AbilityScore(5);
        assertEquals(Integer.valueOf(-3), score.getModifier());
        score = new AbilityScore(4);
        assertEquals(Integer.valueOf(-3), score.getModifier());
    }
}

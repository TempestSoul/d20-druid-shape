package com.tempestsoul.dnd.d20;

import com.tempestsoul.dnd.d20.model.Creature;
import com.tempestsoul.dnd.d20.model.CreatureSubType;
import com.tempestsoul.dnd.d20.model.SpecialAbilityType;
import com.tempestsoul.dnd.d20.model.SpecialAttack;
import com.tempestsoul.dnd.d20.model.hitdie.DruidHitDieLevel;

public class WildShapeServiceImpl implements WildShapeService {
    /*
     * type/subtype: druid
    gain aquatic subtype of assumed
    size: wild shape
    natural weapons: shape
    natural armor: shape
    movement: shape
    special attacks(Ex): shape
    special qualities: druid
    abilities/attacks(Sp,Su): druid
    physical ability scores: shape
    mental ability scores: druid
    base save: druid
    skills: druid
    feats: druid
    BAB: druid
    hp: druid
    spellcasting: (Natural Spell) ? true : false
    spoken language: shape

    druid items: disappear
    shape items: fall off
    +10 disguise as form
    shape HD <= druid HD

    class Animal {
        int iHitDice;
        String name;
        boolean isAquatic;
        Size size;
        int iStrength;
        int iDexterity;
        int iConstitution;
        int iNaturalArmor;
        List<Attack> attacks;
        String sMovement;
        List<SpecialAttack> specialAttacks;
        int iSpace; int iReach;
    }
     */

    //http://www.wizards.com/default.asp?x=dnd/rg/20060523a <- DEAD LINK
    public Creature wildShape(Creature shaper, Creature creature) {
        int iDruidLvl = shaper.findNumberOfHitDie(new DruidHitDieLevel());
        if(iDruidLvl < 5) {
            throw new RuntimeException("Druids cannot wild shape until level 5; druid level is " + iDruidLvl);
        }
        if(creature.findNumberOfHitDie() > iDruidLvl) {
            throw new IllegalArgumentException("A druid cannot wild shape into creatures with more hit dice");
        }
        // check size & type: based on druid lvl (or does type go into separate functions? elemental should)
        // TODO finish implementing (is anything left?)
        Creature shape = new Creature(shaper);
        shape.setName(shaper.getName() + " [" + creature.getName() + " Shape]");
        // size: wild shape
        shape.setSize(creature.getSize());
        // TODO (space/reach: wild shape)
        // physical ability scores: shape
        // mental ability scores: druid
        shape.setPhysicalScores(creature.getStats());
        shape.setMentalScores(shaper.getStats());	// redundant, but oh well
        // natural weapons: shape
        shape.setAttacks(creature.getAttacks());
        // if old creature has aquatic, add aquatic
        if(creature.isAquatic() && !shape.isAquatic())
            shape.getSubTypes().add(CreatureSubType.Aquatic);
        // natural armor: shape
        shape.setNaturalArmor(creature.getNaturalArmor());
        // movement: shape
        shape.setMovement(creature.getMovement());
        // TODO don't forget the skill bonuses from movement speeds! (ugh. so swimSpd > X -> swim+Y, blabla...)

        // lose Ex special attacks
        if (shaper.getSpecialAtks() != null) {
            for (SpecialAttack atk : shaper.getSpecialAtks()) {
                if (atk.getType().equals(SpecialAbilityType.EXTRAORDINARY))
                    shape.getSpecialAtks().remove(atk);
            }
        }
        // special attacks(Ex): shape
        if (creature.getSpecialAtks() != null) {
            for (SpecialAttack atk : creature.getSpecialAtks()) {
                SpecialAbilityType atkType = atk.getType();
                if (atkType.equals(SpecialAbilityType.EXTRAORDINARY)
                        || atkType.equals(SpecialAbilityType.NATURAL))
                    shape.getSpecialAtks().add(atk);
            }
        }
        // NOT animal's racial skills, special qualities, type abilities, etc

        return shape;
    }
}

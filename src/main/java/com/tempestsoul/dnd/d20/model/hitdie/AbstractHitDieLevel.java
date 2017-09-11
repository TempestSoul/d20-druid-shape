package com.tempestsoul.dnd.d20.model.hitdie;

import com.tempestsoul.dnd.d20.model.AbilityScore;
import com.tempestsoul.dnd.d20.model.HitDieLevel;

/**
 * Created on 10/17/2015.
 */
public abstract class AbstractHitDieLevel implements HitDieLevel {

    protected Integer calculateFractionOfHitDice(Integer numHitDice, Double fraction) {
        Double result = Math.floor(fraction * Double.valueOf(numHitDice));
        return result.intValue();
    }

    protected Integer getGoodBaseAttackBonus(Integer numHitDice) {
        return calculateFractionOfHitDice(numHitDice, 1d);
    }

    /**
     * 3/4 Progression BAB (as cleric)
     * @param numHitDice
     * @return base attack bonus as calculated by the 3/4 progression
     */
    protected Integer getFairBaseAttackBonus(Integer numHitDice) {
        return calculateFractionOfHitDice(numHitDice, 0.75d);
    }

    protected Integer getPoorBaseAttackBonus(Integer numHitDice) {
        return calculateFractionOfHitDice(numHitDice, 0.5d);
    }

    protected Integer getGoodBaseSave(Integer numHitDice) {
        return calculateFractionOfHitDice(numHitDice, 0.5d) + 2;
    }

    protected Integer getPoorBaseSave(Integer numHitDice) {
        return calculateFractionOfHitDice(numHitDice, 1d/3d);
    }

    protected abstract Integer getSkillPointsPerLevel(AbilityScore intelligenceScore);

    // TODO does not handle the 4x modifier for first level!
    public Integer getSkillPoints(AbilityScore intelligenceScore, Integer numHitDice) {
        if (numHitDice > 0) {
            return getSkillPointsPerLevel(intelligenceScore) * numHitDice;
        } else {
            return 0;
        }
    }

}

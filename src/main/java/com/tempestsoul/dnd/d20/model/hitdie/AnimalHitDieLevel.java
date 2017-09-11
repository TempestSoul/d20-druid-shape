package com.tempestsoul.dnd.d20.model.hitdie;

import com.tempestsoul.dnd.d20.model.AbilityScore;

public class AnimalHitDieLevel extends AbstractHitDieLevel {

    @Override
    protected Integer getSkillPointsPerLevel(AbilityScore intelligenceScore) {
        return 2 + intelligenceScore.getModifier();
    }

    @Override
    public Integer getHitDieSize() {
        return 8;
    }

    @Override
    public Integer getBaseAttackBonus(Integer numHitDice) {
        return getFairBaseAttackBonus(numHitDice);
    }

    // The following are not true for all animals, but then Animal isn't actually a class

    @Override
    public Integer getBaseFortitudeSave(Integer numHitDice) {
        return getGoodBaseSave(numHitDice);
    }

    @Override
    public Integer getBaseReflexSave(Integer numHitDice) {
        return getGoodBaseSave(numHitDice);
    }

    @Override
    public Integer getBaseWillSave(Integer numHitDice) {
        return getPoorBaseSave(numHitDice);
    }

}

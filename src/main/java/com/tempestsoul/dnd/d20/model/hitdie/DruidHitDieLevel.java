package com.tempestsoul.dnd.d20.model.hitdie;

import com.tempestsoul.dnd.d20.model.AbilityScore;

public class DruidHitDieLevel extends AbstractHitDieLevel {

    @Override
    public Integer getHitDieSize() {
        return 8;
    }

    @Override
    public Integer getBaseAttackBonus(Integer numHitDice) {
        return getFairBaseAttackBonus(numHitDice);
    }

    @Override
    public Integer getBaseFortitudeSave(Integer numHitDice) {
        return getGoodBaseSave(numHitDice);
    }

    @Override
    public Integer getBaseReflexSave(Integer numHitDice) {
        return getPoorBaseSave(numHitDice);
    }

    @Override
    public Integer getBaseWillSave(Integer numHitDice) {
        return getGoodBaseSave(numHitDice);
    }

    @Override
    protected Integer getSkillPointsPerLevel(AbilityScore intelligenceScore) {
        return 4 + intelligenceScore.getModifier();
    }
}

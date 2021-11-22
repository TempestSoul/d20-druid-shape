package com.tempestsoul.dnd.ui.components;

import com.tempestsoul.dnd.d20.model.Ability;
import com.tempestsoul.dnd.d20.model.Creature;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CreatureRender {

    public String render(Creature creature) {
        StringBuilder sb = new StringBuilder();
        sb.append(creature).append("\n");
        sb.append("HP: ").append(creature.getHitPoints()).append("\n");
        sb.append("Init: ").append(String.format("%+d", creature.getInitiative())).append("\n");
        sb.append("Movement: ").append(creature.getMovement()).append("\n");
        sb.append("AC: ").append(creature.getArmorCount())
                .append(", touch ").append(creature.getTouchArmorCount())
                .append(", flat-footed ").append(creature.getFlatArmorCount())
                .append("\n");
        sb.append("BAB/Grapple: ").append(String.format("%+d", creature.getBaseAtkBonus())).append("/").append(String.format("%+d", creature.getGrappleBonus())).append("\n");
        // Attacks
        appendCollection(sb, "Attacks:\n", creature.getAttacks(), atk -> "- "
                + (atk.getNumber() > 1 ? atk.getNumber() + " " : "") + atk.getName()
                + " " + String.format("%+d", creature.getAttackBonus(atk))
                + " (" + atk.getDmgDie() + String.format("%+d", creature.getDamageBonus(atk)) + ")", "\n");
        sb.append("\n");
        // Space/Reach
        // Special Attacks
        appendCollection(sb, "Special Attacks:\n", creature.getSpecialAtks(), spAtk -> "- " + spAtk.getName() + "(" + spAtk.getType().getAbbrev() + ")" + ":" + spAtk.getDescription(), "\n");
        sb.append("\n");
        // Special Qualities
        // Saves
        sb.append("Saves: ")
                .append("Fort ").append(String.format("%+d", creature.getFortSave())).append(", ")
                .append("Ref ").append(String.format("%+d", creature.getRefSave())).append(", ")
                .append("Will ").append(String.format("%+d", creature.getWillSave())).append("\n");
        // Abilities
        appendCollection(sb, "Abilities: ", Arrays.asList(Ability.values()), a -> a.name() + ":" + creature.getStats().get(a), ", ");
        sb.append("\n");
        // Skills
        appendCollection(sb, "Skills: ", creature.getSkills(), skill -> "- " + skill.getName() + String.format(" %+d", creature.getSkillMod(skill)), "\n");
        // Feats

        // Footnotes/details

        return sb.toString();
    }

    private <T extends Object> void appendCollection(StringBuilder sb, String heading, List<T> values, Function<T, String> transformer, String delimiter) {
        if (values == null || values.isEmpty()) return;
        sb.append(heading);
        for (int i = 0; i < values.size(); ++i) {
            if (i > 0) sb.append(delimiter);
            sb.append(transformer.apply(values.get(i)));
        }
    }

}

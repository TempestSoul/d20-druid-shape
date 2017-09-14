package com.tempestsoul.dnd.service.util;

import com.tempestsoul.dnd.d20.model.Ability;
import com.tempestsoul.dnd.d20.model.Skill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum D20SkillData {

    Appraise("Appraise", Ability.INT, true),
    Balance("Balance", Ability.DEX, true),
    Bluff("Bluff", Ability.CHA, true),
    Climb("Climb",Ability.STR, true),
    Concentration("Concentration", Ability.CON, true),
    CraftAny("Craft .*", Ability.INT, true),
    DecipherScript("Decipher Script", Ability.INT, false),
    Diplomacy("Diplomacy", Ability.CHA, true),
    DisableDevice("Disable Device", Ability.INT, false),
    Disguise("Disguise", Ability.CHA, true),
    EscapeArtist("Escape Artist", Ability.DEX),
    Forgery("Forgery", Ability.INT),
    GatherInfo("Gather Information", Ability.CHA),
    HandleAnimal("Handle Animal", Ability.CHA, false),
    Heal("Heal", Ability.WIS),
    Intimidate("Intimidate", Ability.CHA),
    Jump("Jump", Ability.STR),
    KnowledgeAny("Knowledge .*", Ability.INT, false),
    Listen("Listen", Ability.WIS),
    MoveSilently("Move Silently", Ability.DEX),
    OpenLock("Open Lock", Ability.DEX, false),
    PerformAny("Perform .*", Ability.CHA),
    Profession("Profession .*", Ability.WIS, false),
    Ride("Ride", Ability.DEX),
    Search("Search", Ability.INT),
    SenseMotive("Sense Motive", Ability.WIS),
    SleightHand("Sleight of Hand", Ability.DEX, false),
    SpeakLanguage("Speak Language", null, false),
    Spellcraft("Spellcraft", Ability.INT, false),
    Spot("Spot", Ability.WIS),
    Survival("Survival", Ability.WIS),
    Swim("Swim", Ability.STR),
    Tumble("Tumble", Ability.DEX, false),
    UseMagicDevice("Use Magic Device", Ability.CHA, false),
    UseRope("Use Rope", Ability.DEX);


    D20SkillData(String nameRegEx, Ability keyAbility) {
        this.nameRegEx = Pattern.compile(nameRegEx);
        this.keyAbility = keyAbility;
        this.untrained = true;
    }

    D20SkillData(String nameRegEx, Ability keyAbility, boolean trainedOnly) {
        this.nameRegEx = Pattern.compile(nameRegEx);
        this.keyAbility = keyAbility;
        this.untrained = trainedOnly;
    }

    private Pattern nameRegEx;
    private Ability keyAbility;
    private boolean untrained;

    public Pattern getNameRegEx() {
        return nameRegEx;
    }

    public static Skill convertToSkill(String skillName) {
        for (D20SkillData knownSkill : D20SkillData.values()) {
            Matcher m = knownSkill.getNameRegEx().matcher(skillName);
            if (m.matches()) {
                return new Skill(skillName, knownSkill.keyAbility, knownSkill.untrained);
            }
        }
        return null;
    }

}

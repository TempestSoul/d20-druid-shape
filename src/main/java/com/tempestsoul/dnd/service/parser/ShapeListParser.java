package com.tempestsoul.dnd.service.parser;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
// import java.util.Arrays; // or was it Collections?

import com.tempestsoul.dnd.d20.Creature;

public class ShapeListParser {
    
    private static final String TOKEN_DELIM = "\\|";
    private static final String COMMENT_START = "#";

	public List<Creature> parseCreatureFile(InputStreamReader reader) {
		if(reader == null)
			return null;
		List<Creature> creatures = new ArrayList<Creature>();
		
		return null;
	}
    
    Scanner tokenize(String text) {
        Scanner tokenizer = new Scanner(text);
        tokenizer.setDelimiter(TOKEN_DELIM);
        return tokenizer;
    }
	
	Creature parseLine(String line) {
        if (line == null || line.startsWith(COMMENT_START)) {
            return null;
        }
        Creature creature = new Creature();
        // Black Bear|3|Medium|Animal|19/13/15|2|m40|5/5|2claws(p1d4)+bite(s1d6)|
        // start by tokenizing, then putting tokens on stack & popping top off to parse
        Scanner tokenizer = tokenize(line);
        // Mr. Schaub will kill me. Where's mah defensive!!!?
        // also, I need to find my parser notes. this is embarassing.
        creature.setName(tokenizer.next());
        creature.setNumHitDice(tokenizer.next());
        creature.setSize(getSize(tokenizer.next()));
        String typeToken = tokenizer.next();
        creature.setType(getType(typeToken));
        creature.setSubTypes(getSubtypes(typeToken));
        creature.setPhysicalScores(getPhysicalScores(tokenizer.next()));
        creature.setNaturalArmor(getNatArmor(tokenizer.next()));
        creature.setMovement(getMovement(tokenizer.next()));
	String spaceReach = tokenizer.next(); 
	// space/reach affects both creature and attacks...
        creature.setAttacks(getAttacks(tokenizer.next(), spaceReach));
        // remaining token(s?) = Special Attacks
		return creature;
	}
    
    double getHitDice(String hitDiceToken) {
        try {
            Double hd = Double.valueOf(hitDiceToken);
            return hd.value();
        } catch (Exception e) { // TODO fix to specific exception
            // rethrow, Creature Parse exception!! bad data!
        }
    }
    
    Size getSize(String sizeToken) {
        try {
            // ugh, string parsing into enum. Maybe make a Size.fromString() instead?
            if ("Medium".equals(sizeToken)) {
                return Size.Medium;
            }
        } catch (Exception e) {
        }
    }
    
    CreatureType getType(String typeToken) {
        // Animal(Aquatic) -> Animal
        // bleh, this regex feels horrible. Hm. Could do stringsplit instead... or substring... really, it's all the same
        int startSubtype = typeToken.indexOf('(');
        if (startSubtype >= 0)
            typeToken = typeToken.substring(0, startSubtype);
        // parse type out, OR
        Pattern typePattern = Pattern.compile("([^\\(^\\)]+)(\\([^)]\\))?");
        Matcher matcher = typePattern.matcher(typeToken);
        // type can have subtype in parentheses
        if (matcher.matches()) {
            String type = matcher.group(1);
            // parse type into CreatureType
        } else {
            // poorly formatted data!! explode!!
        }
    }
    
    CreatureSubType getSubType(String typeToken) {
        // Animal(Aquatic) -> Aquatic
        return null;
    }
    
    Map<Ability, AbilityScore> getPhysicalScores(String physicalScoresToken) {
        // 12/13/15 => {Str:12,Dex:13,Con:15}
        Map<Ability, AbilityScore> physicalScores = new HashMap<Ability, AbilityScore>();
        String [] abilityScores = physicalScoresToken.split("/");
        if (abilityScores.length() != 3) {
            throw new Exception("Could not parse ability scores from text:" + physicalScoresToken);
        } else {
            physicalScores.put(Ability.STR, new AbilityScore(abilityScores[0]));
            physicalScores.put(Ability.DEX, new AbilityScore(abilityScores[1]));
            physicalScores.put(Ability.CON, new AbilityScore(abilityScores[2]));
        }
            
        return null;
    }
    
    int getNaturalArmor(String armorToken) {
	return Integer.valueOf(armorToken);
    }
    
    String getMovement(String movement) {
        // maybe I should do more with this? do I want an object model?
        return movement;
    }
    
    List<Attack> getAttacks(String attackToken, String spaceReach) {
        // 2claws(p1d4)+bite(s1d6)
	return null;
    }
}

package com.tempestsoul.dnd.service.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
// import java.util.Arrays; // or was it Collections?

import com.tempestsoul.dnd.d20.model.*;
import com.tempestsoul.dnd.d20.model.hitdie.AnimalHitDieLevel;
import com.tempestsoul.dnd.d20.model.hitdie.PlantHitDieLevel;

public class ShapeListParser {
    private static final Logger logger = Logger.getLogger(ShapeListParser.class.getSimpleName());
    
    private static final String TOKEN_DELIM = "\t";
    private static final String SUBTOKEN_DELIM = "\\|";
    private static final String COMMENT_START = "#";

	public List<Creature> parseCreatureFile(InputStreamReader reader) {
		if(reader == null)
			return null;
		List<Creature> creatures = new ArrayList<Creature>();
        BufferedReader lineReader = null;
        try {
            lineReader = new BufferedReader(reader);
            String line = null;
            while ((line = lineReader.readLine()) != null) {
                Creature c = parseLine(line);
                if (c != null) {
                    creatures.add(c);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not read line from buffered stream", e);
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Cannot close stream", e);
                }
            }
        }
		
		return creatures;
	}
    
    private Scanner tokenize(String text) {
        Scanner tokenizer = new Scanner(text).useDelimiter(TOKEN_DELIM);
        return tokenizer;
    }
	
	Creature parseLine(String line) {
        if (line == null || line.isEmpty() || line.startsWith(COMMENT_START)) {
            return null;
        }
        Creature creature = new Creature();
        // # Name|#HD|Size|Type|Str/Dex/Con|NA|Speed|Space/Reach|Attacks|SpAttacks(type)[text]
        // Black Bear|3|Medium|Animal|19/13/15|2|m40|5/5|2claws(p1d4)+bite(s1d6)|
        Scanner tokenizer = tokenize(line);
        // my teachers will kill me. Where's mah defensive!!!?
        // also, I need to find my parser notes. this is embarrassing.
        creature.setName(tokenizer.next());
        int iHitDice = tokenizer.nextInt(); // TODO handle creatures with less than 1 HD

        creature.setSize(getSize(tokenizer.next()));

        String typeToken = tokenizer.next();
        creature.setType(getType(typeToken));
        creature.setClassLvl(parseClass(creature.getType()), iHitDice);

        creature.setSubTypes(getSubtypes(typeToken));
        creature.setStats(getPhysicalScores(tokenizer.next()));
        creature.setNaturalArmor(tokenizer.nextInt());
        creature.setMovement(getMovement(tokenizer.next()));
        String spaceReach = tokenizer.next();
        // space/reach affects both creature and attacks...
        creature.setAttacks(getAttacks(tokenizer.next(), spaceReach));
        // remaining token(s?) = Special Attacks
        creature.setSpecialAtks(getSpecialAttacks(tokenizer.hasNext() ? tokenizer.next() : null));
		return creature;
	}

    private Size getSize(String sizeToken) {
        return Size.valueOf(sizeToken);
    }

    // uh, I'm not sure what this pattern is doing...
    private static final Pattern typePattern = Pattern.compile("([^()]+)(\\([^)]\\))?");
    
    private CreatureType getType(String typeToken) {
        // Animal(Aquatic) -> Animal
        // bleh, this regex feels horrible. Hm. Could do stringsplit instead... or substring... really, it's all the same
        int startSubtype = typeToken.indexOf('(');
        if (startSubtype >= 0)
            typeToken = typeToken.substring(0, startSubtype);
        return CreatureType.valueOf(typeToken);
    }

    private Class<? extends HitDieLevel> parseClass(CreatureType type) {
        Class<? extends HitDieLevel> typeClass = null;
        switch(type) {
            case Animal:
                typeClass = AnimalHitDieLevel.class;
                break;
            case Plant:
                typeClass = PlantHitDieLevel.class;
                break;
            default: break;
        }
        return typeClass;
    }

    private List<CreatureSubType> getSubtypes(String subtypeToken) {
        // Animal(Aquatic) -> Aquatic
        List<CreatureSubType> subtypes = null;
        if (subtypeToken.contains("(")) {
            subtypes = new ArrayList<>();
            // call getSubtype repeatedly... eventually
            Matcher m = typePattern.matcher(subtypeToken);
            if (m.matches()) {
                String subtype = m.group(2);
                subtypes.add(getSubType(subtype));
            }
        }
	    return subtypes;
    }
    
    private CreatureSubType getSubType(String typeToken) {
        return CreatureSubType.valueOf(typeToken);
    }
    
    private Map<Ability, AbilityScore> getPhysicalScores(String physicalScoresToken) {
        // 12/13/15 => {Str:12,Dex:13,Con:15}
        Map<Ability, AbilityScore> physicalScores = new HashMap<Ability, AbilityScore>();
        Scanner scanner = new Scanner(physicalScoresToken).useDelimiter("/");
        physicalScores.put(Ability.STR, new AbilityScore(scanner.nextInt()));
        physicalScores.put(Ability.DEX, new AbilityScore(scanner.nextInt()));
        physicalScores.put(Ability.CON, new AbilityScore(scanner.nextInt()));
        return physicalScores;
    }
    
    private String getMovement(String movement) {
        // maybe I should do more with this? do I want an object model?
        return movement;
    }
    
    private List<Attack> getAttacks(String attacksToken, final String spaceReach) {
        // 2claws(p1d4)+bite(s1d6) = primary 2 x claws (1d4) and secondary bite (1d6)
        String[] attacks = attacksToken.split("\\+");
        return Arrays.stream(attacks)
                .map(atk -> parseAttack(atk, spaceReach))
                .filter(atk -> atk != null)
                .collect(Collectors.toList());
    }

    private static final Pattern attackPattern = Pattern.compile("(?<num>\\d+)?(?<name>\\D+?)\\((?<type>[ps])(?<die>\\d+d\\d+)(?<special>.*)?\\)");
    private Attack parseAttack(String attackToken, String spaceReach) {
        Attack attack = null;
        Matcher atkMatcher = attackPattern.matcher(attackToken);
        if (atkMatcher.matches()) {
            attack = new Attack();
            attack.setName(atkMatcher.group("name"));
            Integer numWeapons = atkMatcher.group("num") == null ? 1 : Integer.valueOf(atkMatcher.group("num"));
            attack.setNumber(numWeapons);
            attack.setPrimaryAtk("p".equals(atkMatcher.group("type")));
            attack.setDmgDie(atkMatcher.group("die"));
            // TODO uh, reach?
        }
        return attack;
    }

    private List<SpecialAttack> getSpecialAttacks(String specialAtksToken) {
        List<SpecialAttack> attacks = new ArrayList<>();
        if (specialAtksToken != null && !specialAtksToken.isEmpty()) {
            Scanner scanner = new Scanner(specialAtksToken).useDelimiter(SUBTOKEN_DELIM);
            while (scanner.hasNext()) {
                SpecialAttack sa = parseSpecialAttack(scanner.next());
                if (sa != null) {
                    attacks.add(sa);
                }
            }
        }
        return attacks;
    }

    private static final Pattern specialAttackPattern = Pattern.compile("(?<name>.*?)(\\((?<type>)\\))?(\\[(?<text>.*?)\\])?");
    private SpecialAttack parseSpecialAttack(String specialAttackToken) {
        //Improved Grab[bite,rake]
        SpecialAttack specialAttack = null;
        Matcher spAtkMatcher = specialAttackPattern.matcher(specialAttackToken);
        if (spAtkMatcher.matches()) {
            specialAttack = new SpecialAttack();
            specialAttack.setName(spAtkMatcher.group("name"));
            specialAttack.setType(SpecialAbilityType.fromAbbrev(spAtkMatcher.group("type")));
            specialAttack.setDescription(spAtkMatcher.group("text"));
        }
        return specialAttack;
    }
}

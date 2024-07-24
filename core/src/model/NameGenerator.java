package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Picks a name for an entity created upon start of the game
 * @author Nazarii Revitskyi
 * @version July 14, 2024.
 */
public class NameGenerator {
    public static final String NEW_LINE = System.lineSeparator();
    private static final int NAMES_NUM = 10;
    /**
     * Warrior names array.
     */
    private final String[] myWarriorNames = {"Skeithvirr The Wolf",
        "Rhaumm The Tower",
        "Hrurn The Ironclad",
        "Tirstald The Vengeful",
        "Brorkadrumm The Sentinel",
        "Aesbrekr The Thunder",
        "Ornsamm The Defiant",
        "Drotrin The Bloody",
        "Sgolvammenn The Maneater",
        "Ignedr The Undying"};
    /**
     * Priestess names array.
     */
    private final String[] myPriestessNames = {"Ella the Loving",
        "Nissa the Gracious",
        "Valorie the Devoted",
        "Leeta the Faithful",
        "Rosalind the Valiant",
        "Tara the Honest",
        "Melanie the Caring",
        "Helen the Lionheart",
        "Arianna the Righteous",
        "Yavia the Reliable"};
    /**
     * Thief names array.
     */
    private final String[] myThiefNames = {
        "Ciro The Ghost",
        "Ozul The Slayer",
        "Dante The Claw",
        "Cassius The Scythe",
        "Zadicus The Reaper",
        "Cleon The Pale",
        "Luther The Hunter",
        "Urien The Skull",
        "Arad The Poisoner",
        "Orion The Serpent"};

    /**
     * Ogre names array.
     */
    private final String[] myOgreNames = {"Gokug",
        "Drurok",
        "Wugrok",
        "Tonegrot",
        "Kouzor",
        "Zetakag",
        "Orerg",
        "Xukug",
        "Nezug",
        "Mulozir"};
    /**
     * Gremlin names array.
     */
    private final String[] myGremlinNames = {"Boc",
        "Chut",
        "Zag",
        "Ikdag",
        "Rubbg",
        "Vaz",
        "Kud",
        "Agzod",
        "Uzloc",
        "Ontroz"};
    /**
     * Skeleton names array.
     */
    private final String[] mySkeletonNames = {"Stuqur Bonecall",
        "Echralazar Metus",
        "Yauzhul Mallus",
        "Chrozhar Nyte",
        "Vraurius The Defiler",
        "Chratic The Unliving",
        "Griothum The Raised",
        "Hathik Cruor",
        "Zexor Haggard",
        "Strelak The Mute"
    };
    /**
     * Random value generator used to select names.
     */
    private final Random myRandom;

    /**
     * Constructor initializes the object and Random value generator.
     */
    public NameGenerator(){
        myRandom = new Random();
    }

    /**
     * Returns random name out of list of Warrior names.
     * @return String name of warrior character.
     */
    public String getWarriorName(){
        return myWarriorNames[myRandom.nextInt(NAMES_NUM)];
    }
    /**
     * Returns random name out of list of Priestess names.
     * @return String name of priestess character.
     */
    public String getPriestessName(){
        return myPriestessNames[myRandom.nextInt(NAMES_NUM)];
    }

    /**
     * Returns random name out of list of Thief names.
     * @return String name of thief character.
     */
    public String getThiefName(){
        return myThiefNames[myRandom.nextInt(NAMES_NUM)];
    }

    /**
     * Returns random name out of list of Ogre names.
     * @return String name of ogre character.
     */
    public String getOgreName(){
        return myOgreNames[myRandom.nextInt(NAMES_NUM)];
    }

    /**
     * Returns random name out of list of Gremlin names.
     * @return String name of gremlin character.
     */
    public String getGremlinName(){
        return myGremlinNames[myRandom.nextInt(NAMES_NUM)];
    }

    /**
     * Returns random name out of list of Skeleton names.
     * @return String name of skeleton character.
     */
    public String getSkeletonName(){
        return mySkeletonNames[myRandom.nextInt(NAMES_NUM)];
    }

    /**
     * Method that converts all data present in the class to string.
     * @return String representation of class data.
     */
    @Override
    public String toString(){
        final StringBuilder stringBuilder = new StringBuilder();
        final Map<String, String[]> allArraysMap = new HashMap<>();
        allArraysMap.put("Warrior Names", myWarriorNames);
        allArraysMap.put("Priestess Names", myPriestessNames);
        allArraysMap.put("Thief Names", myThiefNames);
        allArraysMap.put("Ogre Names", myOgreNames);
        allArraysMap.put("Gremlin Names", myGremlinNames);
        allArraysMap.put("Skeleton Names", mySkeletonNames);
        for(String strCharacterType : allArraysMap.keySet()){
            stringBuilder.append(strCharacterType).append(NEW_LINE);
            for(String name : allArraysMap.get(strCharacterType)){
                stringBuilder.append(name).append(", ");
            }
            stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
            stringBuilder.append(NEW_LINE).append(NEW_LINE);
        }
        return stringBuilder.toString();
    }
}
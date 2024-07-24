import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.NameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * This class tests the correctness of implementation of name generating class.
 * If adding more entity types to dungeon need to specify new values for proper testing.
 * @author Nazarii Revitskyi
 * @version July 17, 2024
*/
public class TestNameGenerator {
    /**
     * instance of name generator to test.
     */
    private final NameGenerator myNameGenerator = new NameGenerator();
    /**
     * Number of repeated function runs that is sufficient to assume test passes.
     */
    private static final int RANDOMNESS_FACTOR = 100;
    /**
     * Number of entity types that are currently in the game (if adding more need to update).
     */
    private static final int NUMBER_OF_ENTITY_TYPES = 6;

    /**
     * Test ability to store name generator name info as string.
     */
    @Test
    void testNameGeneratorHasDataAfterBeingInstantiated(){
        String strNameGeneratorData = myNameGenerator.toString();
        assertFalse(strNameGeneratorData.isEmpty());
        System.out.println(strNameGeneratorData);
    }

    /**
     * Test warrior returns warrior type names.
     */
    @Test
    void testWarriorName(){
        String warriorNames = "Skeithvirr The Wolf, Rhaumm The Tower, Hrurn The Ironclad," +
            " Tirstald The Vengeful, Brorkadrumm The Sentinel, Aesbrekr The Thunder, " +
            "Ornsamm The Defiant, Drotrin The Bloody, Sgolvammenn The Maneater," +
            " Ignedr The Undying";
        String randomWarriorName = myNameGenerator.getWarriorName();
        assertTrue(warriorNames.contains(randomWarriorName));
        System.out.println("\nThe warrior name: " +
            randomWarriorName + " exists in a list of possible warrior names.\n" + warriorNames);
    }
    /**
     * Test priestess returns priestess type names.
     */
    @Test
    void testPriestessName(){
        String priestessNames = "Ella the Loving, Nissa the Gracious, Valorie the Devoted, " +
            "Leeta the Faithful, Rosalind the Valiant, Tara the Honest, Melanie the Caring, " +
            "Helen the Lionheart, Arianna the Righteous, Yavia the Reliable";
        String randomPriestessName = myNameGenerator.getPriestessName();
        assertTrue(priestessNames.contains(randomPriestessName));
        System.out.println("\nThe priestess name: " +
            randomPriestessName + " exists in a list of possible priestess names.\n"
            + priestessNames);
    }
    /**
     * Test thief returns thief type names.
     */
    @Test
    void testThiefName(){
        String thiefNames = "Ciro The Ghost, Ozul The Slayer, Dante The Claw, " +
            "Cassius The Scythe, Zadicus The Reaper, Cleon The Pale, Luther The Hunter, " +
            "Urien The Skull, Arad The Poisoner, Orion The Serpent";
        String randomThiefNames = myNameGenerator.getThiefName();
        assertTrue(thiefNames.contains(randomThiefNames));
        System.out.println("\nThe thief name: " +
            randomThiefNames + " exists in a list of possible thief names.\n"
            + thiefNames);
    }
    /**
     * Test ogre returns ogre type names.
     */
    @Test
    void testOgreName(){
        String ogreNames = "Gokug, Drurok, Wugrok, Tonegrot, Kouzor, Zetakag, Orerg, Xukug" +
            ", Nezug, Mulozir";
        String randomOgreNames = myNameGenerator.getOgreName();
        assertTrue(ogreNames.contains(randomOgreNames));
        System.out.println("\nThe ogre name: " +
            randomOgreNames + " exists in a list of possible ogre names.\n"
            + ogreNames);
    }
    /**
     * Test gremlin returns gremlin type names.
     */
    @Test
    void testGremlinName(){
        String gremlinNames = "Boc, Chut, Zag, Ikdag, Rubbg, Vaz, Kud, Agzod, Uzloc, Ontroz";
        String randomGremlinNames = myNameGenerator.getGremlinName();
        assertTrue(gremlinNames.contains(randomGremlinNames));
        System.out.println("\nThe gremlin name: " +
            randomGremlinNames + " exists in a list of possible gremlin names.\n"
            + gremlinNames);
    }
    /**
     * Test skeleton returns skeleton type names.
     */
    @Test
    void testSkeletonName(){
        String skeletonNames = "Stuqur Bonecall, Echralazar Metus, Yauzhul Mallus," +
            " Chrozhar Nyte, Vraurius The Defiler, Chratic The Unliving, Griothum The Raised," +
            " Hathik Cruor, Zexor Haggard, Strelak The Mute";
        String randomSkeletonNames = myNameGenerator.getSkeletonName();
        assertTrue(skeletonNames.contains(randomSkeletonNames));
        System.out.println("\nThe skeleton name: " +
            randomSkeletonNames + " exists in a list of possible skeleton names.\n"
            + skeletonNames);
    }
    /**
     * Test for ability to generate 100 different entities which should suffice for the scope
     * of the project.
     */
    @Test
    void testPrintHundredRandomNames(){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < RANDOMNESS_FACTOR; i++){
            switch(rand.nextInt(NUMBER_OF_ENTITY_TYPES)){
                case 0:
                    sb.append(myNameGenerator.getWarriorName()).append(System.lineSeparator());
                    break;
                case 1:
                    sb.append(myNameGenerator.getPriestessName()).append(System.lineSeparator());
                    break;
                case 2:
                    sb.append(myNameGenerator.getThiefName()).append(System.lineSeparator());
                    break;
                case 3:
                    sb.append(myNameGenerator.getOgreName()).append(System.lineSeparator());
                    break;
                case 4:
                    sb.append(myNameGenerator.getGremlinName()).append(System.lineSeparator());
                    break;
                case 5:
                    sb.append(myNameGenerator.getSkeletonName()).append(System.lineSeparator());
                    break;
            }
        }
        System.out.println(sb.toString());
        //test passed if the method reached this line.
        assertTrue(true);
    }
}

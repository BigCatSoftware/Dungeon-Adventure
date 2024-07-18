import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.NameGenerator;
import org.junit.jupiter.api.Test;

/**
 * This class tests the correctness of implementation of name generating class.
 */
public class TestNameGenerator {
    //TODO: revert constructor back to package-protected.
    private final NameGenerator myNameGenerator = new NameGenerator();
    @Test
    void testNameGeneratorHasDataAfterBeingInstantiated(){
        String strNameGeneratorData = myNameGenerator.toString();
        assertFalse(strNameGeneratorData.isEmpty());
        System.out.println(strNameGeneratorData);
    }
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
    @Test
    void testThiefName(){
        String priestessNames = "Ella the Loving, Nissa the Gracious, Valorie the Devoted, " +
            "Leeta the Faithful, Rosalind the Valiant, Tara the Honest, Melanie the Caring, " +
            "Helen the Lionheart, Arianna the Righteous, Yavia the Reliable";
        String randomPriestessName = myNameGenerator.getPriestessName();
        assertTrue(priestessNames.contains(randomPriestessName));
        System.out.println("\nThe priestess name: " +
            randomPriestessName + " exists in a list of possible priestess names.\n"
            + priestessNames);
    }
}

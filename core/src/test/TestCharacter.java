import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.DungeonCharacter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
public class TestCharacter {
    DungeonCharacter myWarrior = new Warrior();
    DungeonCharacter myPriestess = new Priestess();
    DungeonCharacter myThief = new Thief();
    /**
     * Test initialization of all fields necessary for correct work of Warrior class.
     *
     */
    @Test
    void createWarriorInstanceTest(){
        //Individual characteristics of Warrior character
        assertEquals(125, myWarrior.getCurrentHealth());
        assertEquals(35, myWarrior.getMinDamage());
        assertEquals(60, myWarrior.getMaxDamage());
        assertEquals(75, myWarrior.getSpecialMinDamage());
        assertEquals(175, myWarrior.getSpecialMaxDamage());
        assertEquals(20, myWarrior.getBlockChance());
        assertEquals(80, myWarrior.getHitChance());
        assertEquals(4, myWarrior.getCurrentSpeed());
    }
    @Test
    void createPriestessInstanceTest(){
        //Individual characteristics of Priestess character
        assertEquals(75, myPriestess.getCurrentHealth());
        assertEquals(25, myPriestess.getMinDamage());
        assertEquals(45, myPriestess.getMaxDamage());
        assertEquals(30, myPriestess.getSpecialHeal());
        assertEquals(30, myPriestess.getBlockChance());
        assertEquals(70, myPriestess.getHitChance());
        assertEquals(5, myPriestess.getCurrentSpeed());
    }
    @Test
    void createThiefInstanceTest(){
        //Individual characteristics of Priestess character
        assertEquals(75, myThief.getCurrentHealth());
        assertEquals(25, myThief.getMinDamage());
        assertEquals(45, myThief.getMaxDamage());
        assertEquals(30, myThief.getSpecialHeal());
        assertEquals(30, myThief.getBlockChance());
        assertEquals(70, myThief.getHitChance());
        assertEquals(5, myThief.getCurrentSpeed());
    }

}

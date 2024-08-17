import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dungeonadventure.database.SQLiteConnections;
import model.Enemy;
import model.EntityLoader;
import model.NameGenerator;
import model.Priestess;
import model.Thief;
import model.Warrior;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test character creation for both hero and enemy which uses sqlite database.
 * @author Nazarii Revitskyi
 * @version Aug. 16, 2024.
 */
public class TestCharacter {
    final Warrior myWarrior;
    final Priestess myPriestess;
    final Thief myThief;
    final Enemy myGremlin;
    final Enemy mySkeleton;
    final Enemy myOgre;
    TestCharacter(){
        if(!SQLiteConnections.isConnected()){
            SQLiteConnections.establishConnection();
        }
        myWarrior = new Warrior(NameGenerator.getWarriorName(),1,1);
        myPriestess = new Priestess(NameGenerator.getPriestessName(),1,1);
        myThief = new Thief(NameGenerator.getThiefName(),1,1);
        myGremlin = EntityLoader.createGremlin(2,2);
        mySkeleton = EntityLoader.createSkeleton(3,3);
        myOgre = EntityLoader.createOgre(4,4);
    }
    /**
     * Test initialization of all fields necessary for correct work of Warrior class.
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
        assertEquals(4, myWarrior.getSpeed());
    }
    /**
     * Test initialization of all fields necessary for correct work of Priestess class.
     */
    @Test
    void createPriestessInstanceTest(){
        //Individual characteristics of Priestess character
        assertEquals(75, myPriestess.getCurrentHealth());
        assertEquals(25, myPriestess.getMinDamage());
        assertEquals(45, myPriestess.getMaxDamage());
        assertEquals(30, myPriestess.getSpecialMinHeal());
        assertEquals(50, myPriestess.getSpecialMaxHeal());
        assertEquals(30, myPriestess.getBlockChance());
        assertEquals(70, myPriestess.getHitChance());
        assertEquals(5, myPriestess.getSpeed());
    }
    /**
     * Test initialization of all fields necessary for correct work of Thief class.
     */
    @Test
    void createThiefInstanceTest(){
        //Individual characteristics of Priestess character
        assertEquals(75, myThief.getCurrentHealth());
        assertEquals(20, myThief.getMinDamage());
        assertEquals(40, myThief.getMaxDamage());
        assertEquals(40, myThief.getBlockChance());
        assertEquals(80, myThief.getHitChance());
        assertEquals(6, myThief.getSpeed());
    }
    @Test
    void createGremlinInstanceTest(){
        assertEquals(70, myGremlin.getCurrentHealth());
        assertEquals(15, myGremlin.getMinDamage());
        assertEquals(30, myGremlin.getMaxDamage());
        assertEquals(40, myGremlin.getMyHealChance());
        assertEquals(80, myGremlin.getHitChance());
        assertEquals(5, myGremlin.getSpeed());
        assertEquals(20, myGremlin.getMyMinHeal());
        assertEquals(40, myGremlin.getMyMaxHeal());
    }
    @Test
    void createSkeletonInstanceTest(){
        assertEquals(100, mySkeleton.getCurrentHealth());
        assertEquals(30, mySkeleton.getMinDamage());
        assertEquals(50, mySkeleton.getMaxDamage());
        assertEquals(30, mySkeleton.getMyHealChance());
        assertEquals(80, mySkeleton.getHitChance());
        assertEquals(3, mySkeleton.getSpeed());
        assertEquals(30, mySkeleton.getMyMinHeal());
        assertEquals(50, mySkeleton.getMyMaxHeal());
    }
    @Test
    void createOgreInstanceTest(){
        assertEquals(200, myOgre.getCurrentHealth());
        assertEquals(30, myOgre.getMinDamage());
        assertEquals(60, myOgre.getMaxDamage());
        assertEquals(10, myOgre.getMyHealChance());
        assertEquals(60, myOgre.getHitChance());
        assertEquals(2, myOgre.getSpeed());
        assertEquals(30, myOgre.getMyMinHeal());
        assertEquals(60, myOgre.getMyMaxHeal());
    }
}

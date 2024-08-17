import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.dungeonadventure.database.SQLiteConnections;
import model.Enemy;
import model.EntityLoader;
import model.NameGenerator;
import model.Priestess;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestPriestess {
    /**
     * Tries for randomness constant to determine correct output consistently.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;
    /**
     * Create priestess to test.
     */
    Priestess myPriestess = new Priestess(NameGenerator.getPriestessName(), 4, 2);
    /**
     * Test initialization of all fields necessary for correct work of Priestess class.
     */
    @Test
    void createPriestessInstanceTest(){

        String result = myPriestess.toString();
        assertFalse(result.isEmpty());
        //Individual characteristics of Priestess character
        System.out.println(result);
    }
    /**
     * Creates a log of combat that applies all test cases in simulation of battle where
     * character will fight until losing all health and dying.
     */
    @Test
    void attackAndDamageToPriestess(){
        if(!SQLiteConnections.isConnected()){
            SQLiteConnections.establishConnection();
        }
        Enemy mySkeleton = EntityLoader.createSkeleton(2,2);
        Random rand = new Random();
        int numOfTurns = 0;
        boolean isHeroTurn = true;
        while(!myPriestess.getIsDead() && !mySkeleton.getIsDead())
        {
            numOfTurns++;
            if(isHeroTurn){
                //special action choice
                if(rand.nextInt(101) <= 30 && myPriestess.getCurrentHealth()+20 < myPriestess.getMaxHealth()){
                    int healthBeforeHeal = myPriestess.getCurrentHealth();
                    myPriestess.specialAction();
                    System.out.println("[" + myPriestess.getMyName() + "] healed for " +
                        (myPriestess.getCurrentHealth()-healthBeforeHeal) + " HP: "
                        + myPriestess.getCurrentHealth() + "/" + myPriestess.getMaxHealth());
                    isHeroTurn = false;
                }
                //attack choice
                else{
                    System.out.println(myPriestess.attack(mySkeleton));
                    isHeroTurn = false;
                }
            }
            else{
                numOfTurns++;
                System.out.println(mySkeleton.attack(myPriestess));
                isHeroTurn = true;
            }
        }
        if(myPriestess.getIsDead()){
            System.out.println("[" + myPriestess.getMyName() + "]" + " has perished to be forgotten" +
                " forever.");
        }
        else{
            System.out.println("[" + mySkeleton.getMyName() + "]" + " has perished to be forgotten" +
                " forever.");
        }
    }
}

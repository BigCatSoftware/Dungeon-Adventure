import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import model.NameGenerator;
import model.Ogre;
import model.Warrior;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestOgre {
    /**
     * Tries for randomness constant to determine correct output consistently.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;
    /**
     * Create warrior to test.
     */
    Ogre myOgre = new Ogre(NameGenerator.getOgreName(), 4, 2);
    /**
     * Test initialization of all fields necessary for correct work of Warrior class.
     */
    @Test
    void createWarriorInstanceTest(){

        String result = myOgre.toString();
        assertFalse(result.isEmpty());
        //Individual characteristics of Warrior character
        System.out.println(result);
    }
    /**
     * Creates a log of combat that applies all test cases in simulation of battle where
     * character will fight until losing all health and dying.
     */
    @Test
    void attackAndDamageToOgre(){
        Random rand = new Random();
        for(int i = 0; i < TRIES_FOR_RANDOMNESS; i++){
            if(myOgre.getCurrentHealth() > 0){
                System.out.println(myOgre.receiveDamage(rand.nextInt(15, 31)));
            }
            else{
                System.out.println("[" + myOgre.getMyName() + "]" + " has perished to be forgotten" +
                    " forever.");
                break;
            }
        }
    }
}

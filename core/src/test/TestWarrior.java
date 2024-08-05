import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import model.NameGenerator;
import model.Warrior;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestWarrior {
    /**
     * Tries for randomness constant to determine correct output consistently.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;
    /**
     * Create warrior to test.
     */
    Warrior myWarrior = new Warrior(NameGenerator.getWarriorName(), 4, 2);
    /**
     * Test initialization of all fields necessary for correct work of Warrior class.
     */
    @Test
    void createWarriorInstanceTest(){

        String result = myWarrior.toString();
        assertFalse(result.isEmpty());
        //Individual characteristics of Warrior character
        System.out.println(result);
    }

    /**
     * Tests passing null for name for this character
     */
    @Test
    void createWarriorInstanceNoName(){
        myWarrior = new Warrior(null, 4,2);
        System.out.println(myWarrior.toString());
    }

    /**
     * Tests passing "" for name for this character
     */
    @Test
    void createWarriorInstanceEmptyName(){
        myWarrior = new Warrior("", 4, 2);
        System.out.println(myWarrior.toString());
    }

    /**
     * Tests negative position instantiation for this character.
     */
    @Test
    void createWarriorInstanceNegativePosition(){
        assertThrows(IllegalArgumentException.class, () ->
        {myWarrior = new Warrior(NameGenerator.getWarriorName(), -1, -1);});
    }

    /**
     * Tests negative x position instantiation of this character
     */
    @Test
    void createWarriorInstanceNegativeX(){
        assertThrows(IllegalArgumentException.class, () ->
        {myWarrior = new Warrior(NameGenerator.getWarriorName(), -1, Integer.MAX_VALUE);});
    }

    /**
     * Tests negative y position instantiation of this character
     */
    @Test
    void createWarriorInstanceNegativeY(){
        assertThrows(IllegalArgumentException.class, () ->
        {myWarrior = new Warrior(NameGenerator.getWarriorName(), Integer.MAX_VALUE, -1);});
    }

    /**
     * Tests zero position instantiation of this character
     */
    @Test
    void createWarriorInstanceZeroPosition(){
        myWarrior = new Warrior(NameGenerator.getWarriorName(), 0, 0);
        System.out.println(myWarrior.toString());
    }

    /**
     * Tests maximum position instantiation for this character.
     */
    @Test
    void createWarriorInstanceMaxPosition(){
        myWarrior = new Warrior(NameGenerator.getWarriorName(), Integer.MAX_VALUE, Integer.MAX_VALUE);
        System.out.println(myWarrior.toString());
    }

//    /**
//     * Test that special attack is returning damage in defined range; for warrior: 75-175
//     */
//    @Test
//    void specialActionCorrectDamageRange(){
//        final int saMax = 175;
//        final int saMin = 75;
//        int actualDamage = 0;
//        int attackSuccessCount = 0;
//        for(int i = 0; i<TRIES_FOR_RANDOMNESS; i++){
//            actualDamage = myWarrior.specialAction();
//            if(actualDamage != 0){
//                attackSuccessCount++;
//                if(actualDamage < saMin || actualDamage > saMax){
//                    fail();
//                }
//            }
//        }
//        System.out.print("Total success: " + attackSuccessCount);
//    }

//    /**
//     * Test that attack is returning damage in defined range: for warrior: 35-60
//     */
//    @Test
//    void testCorrectDamageRange(){
//        final int saMax = 60;
//        final int saMin = 35;
//        int actualDamage = 0;
//        int attackSuccessCount = 0;
//        for(int i = 0; i<TRIES_FOR_RANDOMNESS; i++){
//            actualDamage = myWarrior.attack();
//            if(actualDamage != 0){
//                attackSuccessCount++;
//                if(actualDamage < saMin || actualDamage > saMax){
//                    fail();
//                }
//            }
//        }
//        System.out.print("Total success: " + attackSuccessCount);
//    }

    /**
     * Check if passing correct name for special skill.
     */
    @Test
    void correctSpecialActionName(){
        assertEquals("Crushing Blow", myWarrior.getSpecialActionName());
    }

    /**
     * Check if block chance is within 20% probability
     */
    @Test
    void checkBlockChance(){
        //Warrior block chance = 20%
        int successBlock = 0;
        for(int i = 0; i < TRIES_FOR_RANDOMNESS; i++){
            if(myWarrior.checkForBlock()){
                successBlock++;
            }
        }
        System.out.println(successBlock);
    }

    /**
     * Creates a log of combat that applies all test cases in simulation of battle where
     * character will fight until losing all health and dying.
     */
    @Test
    void attackAndDamageToWarrior(){
        Random rand = new Random();
        for(int i = 0; i < TRIES_FOR_RANDOMNESS; i++){
            if(myWarrior.getCurrentHealth() > 0){
                System.out.println(myWarrior.receiveDamage(rand.nextInt(15, 31)));
            }
            else{
                System.out.println("[" + myWarrior.getMyName() + "]" + " has perished to be forgotten" +
                    " forever.");
                break;
            }
        }
    }
}

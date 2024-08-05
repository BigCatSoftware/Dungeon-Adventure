package model;

import java.util.Random;
/**
 * Hero subclass, the Thief is a playable character that has normal HP and less powerful attacks,
 * fastest among heroes. His special ability called 'Surprise attack' will warp his enemies
 * perception of time.
 * @author Nazarii Revitskyi
 * @version July 23, 2024.
 */
public class Thief extends Hero{
    /**
     * Thief health constant
     */
    private final static int THIEF_HEALTH = 75;
    /**
     * Thief min damage constant
     */
    private final static int THIEF_MIN_DAMAGE = 20;
    /**
     * Thief max damage constant
     */
    private final static int THIEF_MAX_DAMAGE = 40;
    /**
     * Thief block chance constant
     */
    private final static int THIEF_BLOCK_CHANCE = 40;
    /**
     * Thief hit chance constant
     */
    private final static int THIEF_HIT_CHANCE = 80;
    /**
     * Thief speed constant
     */
    private final static int THIEF_SPEED = 6;
    //SPECIAL SKILL CONSTANTS
    /**
     * Thief skill success chance constant
     */
    private final static int THIEF_SKILL_SUCCESS = 40;
    /**
     * Thief skill no outcome chance constant
     */
    private final static int THIEF_SKILL_NO_OUTCOME = 40;
    /**
     * Thief skill fail chance constant
     */
    private final static int THIEF_SKILL_FAIL = 20;
    /**
     * Create Thief type, Hero specialization that requires name and position on the map.
     * @param theName string name for this character
     * @param theX int position relative to x-axis
     * @param theY int position relative to y-axis
     */
    public Thief(final String theName, final int theX, final int theY){
        super(theName != null && !theName.isEmpty() ? theName : NameGenerator.getThiefName(),
            THIEF_HEALTH, THIEF_MIN_DAMAGE, THIEF_MAX_DAMAGE, THIEF_BLOCK_CHANCE,
            THIEF_HIT_CHANCE, THIEF_SPEED, theX, theY);
    }

    /**
     * Thief has an ability called "Surprise attack" which has 40% chance to attack two times
     * in one turn, 40% chance to perform normal attack, 20% chance to fail action.
     * @return int 0 - no turns, 1 - one turn, 2 - two turns.
     */
    public String specialAction(final Enemy theEnemy){
        if(theEnemy == null){
            throw new IllegalArgumentException("Can't call attack on null character at DungeonCharacter attack().");
        }
        Random rand = new Random();
        final int randomInt = rand.nextInt(RANDOM_FROM_HUNDRED+1);
        final String outputString;
        if(randomInt <= THIEF_SKILL_FAIL){
            outputString = "[" + getMyName() + "] uses <<" + getSpecialActionName() + ">> but misses.";
        }
        else if(randomInt <= THIEF_SKILL_FAIL + THIEF_SKILL_NO_OUTCOME){
            outputString = "[" + getMyName() + "] uses <<" + getSpecialActionName() +
                ">> but deals partial damage - \n[" + getSpecialActionName() +"] "
                + attack(theEnemy);
        }
        else{
            outputString = "[" + getMyName() + "] uses <<" + getSpecialActionName() + ">>" +
                " and does twice the damage!!! - \n[" + getSpecialActionName() +"] "
                + attack(theEnemy) + "\n[" + getSpecialActionName() +"] " + attack(theEnemy);
        }
        return outputString;
    }

    /**
     * Method used to return value to heal for this character
     * @return int amount to heal
     */
    int heal(){
        return 0;
    }
    /**
     * Returns the name of special action for this character
     * @return string special action name.
     */
    public String getSpecialActionName(){
        return "Surprise Attack";
    }
    /**
     * Represents information about his character as string
     * @return string representation of data.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString()).append(NEW_LINE).append(NEW_LINE)
            .append("Thief SPECIAL ACTION: ").append(getSpecialActionName()).append(NEW_LINE)
            .append("SA success chance: ").append(THIEF_SKILL_SUCCESS).append("%")
            .append(NEW_LINE).append("SA no outcome chance: ").append(THIEF_SKILL_NO_OUTCOME)
            .append("%").append(NEW_LINE).append("SA fail chance: ").append(THIEF_SKILL_FAIL).append("%");
        return stringBuilder.toString();
    }
}

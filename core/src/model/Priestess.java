package model;

import java.io.Serializable;
import java.util.Random;
/**
 * Hero subclass, the Priestess is a playable character that has normal HP and normal attacks,
 * faster than Warrior but slower than Thief. Her special ability called 'Heal' will save her
 * from imminent death.
 * @author Nazarii Revitskyi
 * @version July 23, 2024.
 */

public final class Priestess extends Hero implements Healable, Serializable {
    private static final long serialVersionUID = 1L;
    //CHARACTER PARAMETER CONSTANTS.
    /**
     * Priestess health constant.
     */
    private final static int PRIESTESS_HEALTH = 75;
    /**
     * Priestess min damage constant.
     */
    private final static int PRIESTESS_MIN_DAMAGE = 25;
    /**
     * Priestess max damage constant.
     */
    private final static int PRIESTESS_MAX_DAMAGE = 45;
    /**
     * Priestess block chance constant.
     */
    private final static int PRIESTESS_BLOCK_CHANCE = 30;
    /**
     * Priestess hit chance constant.
     */
    private final static int PRIESTESS_HIT_CHANCE = 70;
    /**
     * Priestess speed constant
     */
    private final static int PRIESTESS_SPEED = 5;
    //SPECIAL SKILL CONSTANTS
    /**
     * Priestess min heal constant
     */
    private final static int PRIESTESS_SKILL_MIN_HEAL = 30;
    /**
     * Priestess max heal constant
     */
    private final static int PRIESTESS_SKILL_MAX_HEAL = 50;
    /**
     * Create Priestess type, Hero specialization that requires name and position on the map.
     * @param theName string name for this character
     * @param theX int position relative to x-axis
     * @param theY int position relative to y-axis
     */
    public Priestess(final String theName, final int theX, final int theY){
        super(theName != null && !theName.isEmpty() ? theName : NameGenerator.getPriestessName(),
            PRIESTESS_HEALTH, PRIESTESS_MIN_DAMAGE, PRIESTESS_MAX_DAMAGE, PRIESTESS_BLOCK_CHANCE,
            PRIESTESS_HIT_CHANCE, PRIESTESS_SPEED, theX, theY, 0, 0);
    }

    /**
     * Priestess special skill "Heal" adds 30-50 to its health.
     * @return string message of action.
     */
    public String specialAction(){
        return addHealth(heal());
    }

    /**
     * Method used to return value to heal for this character
     * @return int amount to heal
     */
    public int heal(){
        Random rand = new Random();
        return rand.nextInt(PRIESTESS_SKILL_MIN_HEAL, PRIESTESS_SKILL_MAX_HEAL+1);
    }

    /**
     * Returns the name of special action for this character
     * @return string special action name.
     */
    public String getSpecialActionName(){
        return "Heal";
    }
    /**
     * Represents information about his character as string
     * @return string representation of data.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString()).append(NEW_LINE).append(NEW_LINE)
            .append("Priestess SPECIAL ACTION: ").append(getSpecialActionName()).append(NEW_LINE)
            .append("SA heal: ").append(PRIESTESS_SKILL_MIN_HEAL).append("-")
            .append(PRIESTESS_SKILL_MAX_HEAL).append(NEW_LINE);
        return stringBuilder.toString();
    }
}
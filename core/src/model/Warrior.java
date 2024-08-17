package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * Hero subclass, the Warrior is a playable character that has high HP and powerful attacks,
 * but not as fast as the other heroes. His special ability called 'Crushing Blow' will aid him
 * in his quest.
 * @author Nazarii Revitskyi
 * @version July 19, 2024.
 */
public final class Warrior extends Hero implements Serializable {
    private static final long serialVersionUID = 1L;
    //CHARACTER PARAMETER CONSTANTS
    /**
     * Warrior health constant
     */
    private final static int WARRIOR_HEALTH = 125;
    /**
     * Warrior min damage constant
     */
    private final static int WARRIOR_MIN_DAMAGE = 35;
    /**
     * Warrior max damage constant
     */
    private final static int WARRIOR_MAX_DAMAGE = 60;
    /**
     * Warrior block chance constant
     */
    private final static int WARRIOR_BLOCK_CHANCE = 20;
    /**
     * Warrior hit chance constant
     */
    private final static int WARRIOR_HIT_CHANCE = 80;
    /**
     * Warrior speed constant.
     */
    private final static int WARRIOR_SPEED = 4;
    //SPECIAL SKILL CONSTANTS
    /**
     * Warrior Skill min damage constant
     */
    private final static int WARRIOR_SKILL_MIN_DAMAGE = 75;
    /**
     * Warrior Skill max damage constant
     */
    private final static int WARRIOR_SKILL_MAX_DAMAGE = 175;
    /**
     * Warrior Skill chance constant.
     */
    private final static int WARRIOR_SKILL_CHANCE = 40;
    /**
     * Create Warrior type, Hero specialization that requires name and position on the map.
     * @param theName string name for this character
     * @param theX int position relative to x-axis
     * @param theY int position relative to y-axis
     */
    public Warrior(final String theName, final int theX, final int theY){
        super(theName != null && !theName.isEmpty() ? theName : NameGenerator.getWarriorName(),
            WARRIOR_HEALTH, WARRIOR_MIN_DAMAGE, WARRIOR_MAX_DAMAGE,
            WARRIOR_BLOCK_CHANCE, WARRIOR_HIT_CHANCE, WARRIOR_SPEED, theX, theY, 0, 0);
    }

    /**
     * Special Action that has a chance of 40% to succeed. Deals 75 to 175 points of damage.
     * if fails, deals 0 damage.
     * @return int damage by character.
     */
    public String specialAction(final Enemy theEnemy) {
        if(theEnemy == null){
            throw new IllegalArgumentException("Can't call attack on null character at DungeonCharacter attack().");
        }
        int damage = 0;
        final StringBuilder sb = new StringBuilder();
        if(specialAttackChance()){
            final Random rand = new Random();
            damage = rand.nextInt(WARRIOR_SKILL_MIN_DAMAGE, WARRIOR_SKILL_MAX_DAMAGE+1);
            sb.append("[").append(getMyName()).append("] used <<")
                .append(getSpecialActionName()).append(">> and dealt ").append(damage)
                .append(" damage. -> ").append(theEnemy.receiveDamage(damage));
        }
        else{
            sb.append("[").append(getMyName()).append("] used <<")
                .append(getSpecialActionName()).append(">> but missed.");
        }
        return sb.toString();
    }

    /**
     * Method used to return value to heal for this character
     * @return int amount to heal
     */
    public int heal(){
        return 0;
    }
    /**
     * Check with 40% chance of successful special attack.
     * @return true/false to perform special attack.
     */
    public boolean specialAttackChance(){
        Random rand = new Random();
        return rand.nextInt(RANDOM_FROM_HUNDRED+1) <= WARRIOR_SKILL_CHANCE;
    }

    /**
     * Returns the name of special action for this character
     * @return string special action name.
     */
    public String getSpecialActionName(){
        //class fields
        return "Crushing Blow";
    }

    /**
     * Returns min special action damage for warrior.
     * @return int min special action damage for warrior.
     */
    public int getSpecialMinDamage(){
        return WARRIOR_SKILL_MIN_DAMAGE;
    }
    /**
     * Returns max special action damage for warrior.
     * @return int max special action damage for warrior.
     */
    public int getSpecialMaxDamage(){
        return WARRIOR_SKILL_MAX_DAMAGE;
    }
    /**
     * Represents information about his character as string
     * @return string representation of data.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString()).append(NEW_LINE).append(NEW_LINE)
            .append("WARRIOR SPECIAL ACTION: ").append(getSpecialActionName()).append(NEW_LINE)
            .append("SA damage: ").append(WARRIOR_SKILL_MIN_DAMAGE).append("-")
            .append(WARRIOR_SKILL_MAX_DAMAGE).append(NEW_LINE)
            .append("SA chance: ").append(WARRIOR_SKILL_CHANCE);
        return stringBuilder.toString();
    }
}

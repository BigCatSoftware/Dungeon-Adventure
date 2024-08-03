package model;

import java.util.Random;

/**
 * Class that defines Enemy type of character that is different in its ability to be
 * a non-playable character that populates the dungeon and roams in the rooms, has ability to
 * heal.
 * @author Nazarii Revitskyi
 * @version July 23, 2024.
 */
abstract public class Enemy extends DungeonCharacter {
    /**
     * Represents int chance to heal and is used in combat
     */
    private final int myHealChance;
    /**
     * Int min value to heal this character in combat
     */
    private final int myMinHeal;
    /**
     * Int max value to heal this character in combat
     */
    private final int myMaxHeal;

    /**
     * Enemy constructor instantiates heal chance
     * @param theName string name of this character
     * @param theHealth int health of this character
     * @param theMinDamage int min damage of this character
     * @param theMaxDamage int max damage of this character
     * @param theHealChance int heal chance of this character
     * @param theHitChance int hit chance of this character
     * @param theSpeed int speed of this character
     * @param theMinHeal int min heal for this character
     * @param theMaxHeal int max heal for this character.
     * @param theX int x position of this character
     * @param theY int y position of this character
     */
    Enemy(final String theName, final int theHealth, final int theMinDamage,
          final int theMaxDamage, final int theHealChance, final int theHitChance,
          final int theSpeed, final int theMinHeal, final int theMaxHeal, final int theX,
          final int theY){
        super(theName, theHealth, theMinDamage, theMaxDamage, theHitChance, theSpeed, theX, theY);
        init(theHealChance, theMinHeal, theMaxHeal);
        myHealChance = theHealChance;
        myMinHeal = theMinHeal;
        myMaxHeal = theMaxHeal;
    }

    /**
     * Test parameters for correctness
     * @param theHealChance int heal chance of this character
     * @param theMinHeal int min heal for this character
     * @param theMaxHeal int max heal for this character
     */
    void init(final int theHealChance, final int theMinHeal, final int theMaxHeal){
        if(theHealChance < 0 || theHealChance > 100 || theMinHeal <= 0 || theMaxHeal <= 0){
            throw new IllegalArgumentException("Can't initialize illegal values");
        }
    }
    /**
     * Upon combat when enemy received damage there is a chance to heal, otherwise the
     * damage is applied to their health.
     * @param incomingDamage int value of damage to apply to this character.
     * @return String message description of state.
     */
    @Override
    public String receiveDamage(final int incomingDamage){
        String message = "";
        if(checkForHeal()){
            message = super.receiveDamage(incomingDamage);
            if(!getIsDead()){
                int healthBeforeHeal = getCurrentHealth();
                addHealth();
                message += " and healed for " + (getCurrentHealth() - healthBeforeHeal)+
                    " HP: " + getCurrentHealth() + "/" + getMaxHealth();
            }
        }
        else{
            message = super.receiveDamage(incomingDamage);
        }
        return message;
    }

    /**
     * Returns int amount to heal that is defined by a type of character and its healing
     * capability.
     * @return int heal amount
     */
    int heal(){
        Random rand = new Random();
        return rand.nextInt(myMinHeal, myMaxHeal+1);
    }

    /**
     * Returns heal chance for this character
     * @return int chance to heal for this character
     */
    public int getMyHealChance(){
        return myHealChance;

    }
    /**
     * Check if heal is successful for this character.
     * @return true/false if heal was successful.
     */
    boolean checkForHeal(){
        Random rand = new Random();
        return  rand.nextInt(RANDOM_FROM_HUNDRED + 1) <= myHealChance;
    }
    /**
     * Adds to Dungeon Character toString() enemies heal chance.
     * @return string representation of data for this character.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString()).append(NEW_LINE).append(NEW_LINE)
            .append("Enemy Heal Chance: ").append(myHealChance).append(NEW_LINE)
            .append("Enemy Heal amount: ").append(myMinHeal).append("-").append(myMaxHeal);
        return stringBuilder.toString();
    }
}

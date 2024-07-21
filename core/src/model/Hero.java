package model;

import java.util.Random;
/**
 * Class that defines Hero type of character that is different in its ability to be playable
 * by a user, has special actions and ability to block.
 * @author Nazarii Revitskyi
 * @version July 19, 2024.
 */
abstract public class Hero extends DungeonCharacter {

    /**
     * Represents int chance to block attack and is used in combat.
     */
    private final int myBlockChance;


    /**
     * Hero constructor instantiates block chance.
     */
    Hero(final String theName, final int theHealth, final int theMinDamage,
         final int theMaxDamage, final int theBlockChance, final int theHitChance,
         final int theSpeed, final int theX, final int theY){
        super(theName, theHealth, theMinDamage, theMaxDamage, theHitChance, theSpeed, theX, theY);
        myBlockChance = theBlockChance;
    }

    /**
     * Upon combat when hero received damage there is a chance to block it, otherwise the
     * damage is applied to their health.
     * @param incomingDamage int value of damage to apply to this character.
     * @return String message description of state.
     */
    @Override
    public String receiveDamage(final int incomingDamage){
        if(checkForBlock()){
            return getMyName() + " blocked the attack.";
        }
        return super.receiveDamage(incomingDamage);
    }
    /**
     *  Special Action that performs powerful attack.
     * @return int damage by character.
     */
    abstract int specialAction();

    /**
     * Returns block chance for this character.
     * @return int chance to block attack to this character.
     */
    public int getMyBlockChance(){
        return myBlockChance;
    }

    /**
     * Check if block is successful for this character.
     * @return true/false if attack was blocked.
     */
    public boolean checkForBlock(){
        Random rand = new Random();
        return  rand.nextInt(RANDOM_FROM_HUNDRED + 1) <= myBlockChance;
    }

    /**
     * Adds to Dungeon Character toString() hero's block chance.
     * @return string representation of data for this character.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString()).append(NEW_LINE).append(NEW_LINE)
            .append("Hero Block Chance: ").append(myBlockChance);
        return stringBuilder.toString();
    }
}

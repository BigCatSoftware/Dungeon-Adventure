package model;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.Random;
/**
 * Class that defines Hero type of character that is different in its ability to be playable
 * by a user, has special actions and ability to block.
 * @author Nazarii Revitskyi
 * @version July 19, 2024.
 */
abstract public class Hero extends DungeonCharacter implements Serializable {
    /**
     * Health from potion modifier.
     */
    private final static int HEALING_POTION_HEAL_AMOUNT = 40;
    /**
     * Bomb min damage modifier.
     */
    private final static int BOMB_MIN_DAMAGE = 70;
    /**
     * Bomb max damage modifier.
     */
    private final static int BOMB_MAX_DAMAGE = 200;
    /**
     * Represents int chance to block attack and is used in combat.
     */
    private final int myBlockChance;
    /**
     * Represents int keys inside hero inventory.
     */
    private int myKeysInv;
    /**
     * Represents int health potions inside hero inventory.
     */
    private int myHealthInv;

    /**
     * Represents int bombs inside hero inventory.
     */
    private int myBombInv;

    /**
     * Hero constructor instantiates block chance.
     * @param theName string name of this character
     * @param theHealth int health of this character
     * @param theMinDamage int min damage of this character
     * @param theMaxDamage int max damage of this character
     * @param theBlockChance int block chance of this character
     * @param theHitChance int hit chance of this character
     * @param theSpeed int speed of this character
     * @param theX int x position of this character
     * @param theY int y position of this character
     */
    Hero(final String theName, final int theHealth, final int theMinDamage,
         final int theMaxDamage, final int theBlockChance, final int theHitChance,
         final int theSpeed, final int theX, final int theY){
        super(theName, theHealth, theMinDamage, theMaxDamage, theHitChance, theSpeed, theX, theY);
        init(theBlockChance);
        myBlockChance = theBlockChance;
        myKeysInv = 0;
        myHealthInv = 0;
        myBombInv = 0;
    }
    void init(final int theBlockChance){
        if(myBlockChance < 0 || myBlockChance > 100){
            throw new IllegalArgumentException("Chance can't go out of bounds");
        }
    }

    /**
     * Method used to return value to heal for this character
     * @return int amount to heal.
     */
    abstract int heal();

    /**
     * Upon combat when hero received damage there is a chance to block it, otherwise the
     * damage is applied to their health.
     * @param incomingDamage int value of damage to apply to this character.
     * @return String message description of state.
     */
    @Override
    public String receiveDamage(final int incomingDamage){
        if(checkForBlock()){
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Block.ogg")));
            return getMyName() + " blocked the attack.";
        }
        return super.receiveDamage(incomingDamage);
    }
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
     * This method will add a key to hero's inventory.
     */
    public void addKey(){
        if(myKeysInv < 4){
            myKeysInv++;
        }
    }

    /**
     * This method will add a key to hero's inventory.
     */
    public void addBomb() {
        if(myBombInv < 999){
            myBombInv++;
        }
    }

    /**
     * This method will add health potion to hero's inventory.
     */
    public void addHealthPotion(){
        if(myHealthInv < 999){
            myHealthInv++;
        }
    }
    public String useHealthPotion(){
        String result = "There are no potions to use.";
        if(myHealthInv > 0){
            result = "[" + getMyName() + "] Used a Health Potion: " + addHealth(HEALING_POTION_HEAL_AMOUNT);
            myHealthInv--;
        }
        return result;
    }
    public String useBomb(final Enemy theEnemy) {
        String result = "There are no bombs to use.";
        if (myBombInv > 0) {
            result = "[" + getMyName() + "] Used a Bomb." + bombDamage(theEnemy, BOMB_MIN_DAMAGE, BOMB_MAX_DAMAGE);
            myBombInv--;
        }
        return result;
    }
    /**
     * This method will return the number of keys currently in hero's inventory.
     * @return int number of keys in hero's inventory
     */
    public int getHeroKeys(){
        return myKeysInv;
    }

    public int getHeroBombs() {
        return myBombInv;
    }

    /**
     * This method will return the number of health potions currently in hero's inventory.
     * @return int number of health potions in hero's inventory.
     */
    public int getHeroHealthPotions(){
        return myHealthInv;
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


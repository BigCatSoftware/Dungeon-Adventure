package model;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import model.Position;
import org.sqlite.SQLiteDataSource;

/**
 * Abstract dungeon character class defines variety of entities that will inhabit the dungeon.
 * @author Nazarii Revitskyi
 * @version July 17, 2024.
 */
abstract public class DungeonCharacter implements CharacterActions {
    /**
     * String representing newline characters based on system running the program.
     */
    static final String NEW_LINE = System.lineSeparator();
    /**
     * This value is used to check success when comparing to success rate of character action.
     */
    static final int RANDOM_FROM_HUNDRED = 100;
    /**
     * String defines name that the character will have.
     */
    private final String myName;
    /**
     * Number representing health of a character
     */
    private int myCurrentHealth;
    /**
     * Maximum health for this character. Do not use in calculating current health.
     */
    private final int myMaxHealth;
    /**
     * Number representing minimum damage character can deal.
     */
    private final int myMinDamage;
    /**
     * Number representing maximum damage character can deal.
     */
    private final int myMaxDamage;
    /**
     * Number representing chance that the damage will be dealt by this character.
     */
    private final int myHitChance;
    /**
     * Number representing speed to initiate first move upon combat.
     */
    private final int mySpeed;
    /**
     * True/false value that determines if character is dead.
     */
    private boolean myIsDead;
    /**
     * Wrapper for x y location and character movement.
     */
    private Position myPosition;
    /**
     * Type of death when player dies to enemy.
     */
    private boolean myDiedToEntity;
    /**
     * Type of death when player dies to trap.
     */
    private boolean myDiedToTrap;
    /**
     * Initializes values upon creation of new Dungeon Character.
     */
     DungeonCharacter(final String theName, final int theHealth, final int theMinDamage,
                      final int theMaxDamage, final int theHitChance, final int theSpeed,
                      final int theX, final int theY){
         init(theHealth, theMinDamage, theMaxDamage, theHitChance, theSpeed);
         myName = theName;
         myCurrentHealth = theHealth;
         myMaxHealth = theHealth;
         myMinDamage = theMinDamage;
         myMaxDamage = theMaxDamage;
         myHitChance = theHitChance;
         mySpeed = theSpeed;
         myPosition = new Position(theX, theY);
         myIsDead = false;
         myDiedToEntity = false;
         myDiedToTrap = false;
     }
     private void init(final int theHealth, final int theMinDamage, final int theMaxDamage,
               final int theHitChance, final int theSpeed){
         //TODO: separate the possible problem variables.
         if(theHealth <= 0 || theMinDamage <= 0 || theMaxDamage <= 0 || theHitChance <= 0 ||
             theSpeed <= 0){
             throw new IllegalArgumentException("Parameters can't be negative or zero.");
         }
     }
    /**
     * Update Position of character by +1 on y-axis.
     */
    @Override
    public void moveCharacterUp() {
         int currentY = myPosition.getMyY();
         if(currentY != Integer.MAX_VALUE){
             myPosition.setMyY(++currentY);
         }
    }

    /**
     * Update Position of character by -1 on y-axis.
     */
    @Override
    public void moveCharacterDown() {
        int currentY = myPosition.getMyY();
        if(currentY != 0){
            myPosition.setMyY(--currentY);
        }
    }

    /**
     * Update Position of character by -1 on x-axis.
     */
    @Override
    public void moveCharacterLeft() {
        int currentX = myPosition.getMyX();
        if(currentX != 0){
            myPosition.setMyX(--currentX);
        }
    }

    /**
     * Update Position of character by +1 on x-axis.
     */
    @Override
    public void moveCharacterRight() {
         int currentX = myPosition.getMyX();
         if(currentX != Integer.MAX_VALUE){
             myPosition.setMyX(++currentX);
         }
    }

    /**
     * Takes damage from trap and applies it to character.
     * @param theDamage damage to apply
     * @return string message
     */
    public String harmFromTrap(final int theDamage){
        String result;
        if(theDamage < 0){
            throw new IllegalArgumentException("harmFromTrap, incoming damage parameter can't" +
                " be negative.");
        }
        if(theDamage == 0){
            result = "[" + getMyName() + "] stepped on a trap but avoided damage";
        }
        else{
            final int healthBeforeTrap = myCurrentHealth;
            myCurrentHealth -= theDamage;
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Hit.ogg")));
            checkIsDead();
            result = "[" + getMyName() + "] stepped on a trap and received " + theDamage + " damage " +
                " <" + healthBeforeTrap + " -> " + myCurrentHealth + "> HP:" + myCurrentHealth
                + "/" + myMaxHealth;
            if(getIsDead()){
                result += "\n[" + getMyName() + "] dies to trap damage. ";
                myDiedToTrap = true;
            }
        }
        return result;
    }

    /**
     * Applies damage to health of character.
     * @param theIncomingDamage int value of damage to apply to this character.
     * @return String message description of state.
     */
    @Override
    public String receiveDamage(final int theIncomingDamage){
        if(theIncomingDamage < 0){
            throw new IllegalArgumentException("receiveDamage, incoming damage parameter can't" +
                "be negative.");
        }
        String result;
        myCurrentHealth -= theIncomingDamage;
        mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Hit.ogg")));
        checkIsDead();
        if(getIsDead()){
            result = "HP: " + myCurrentHealth + "/" + myMaxHealth + " " + myName + " suffered "
                + theIncomingDamage + " damage and perished from their wounds.";
            myDiedToEntity = true;
        }
        result = "HP: " + myCurrentHealth + "/" + myMaxHealth + " " + myName + " suffered "
            + theIncomingDamage + " damage. ";
        return result;
    }
    /**
     * Returns cause and type of death.
     * @return boolean if character died to enemy.
     */
    public boolean getDiedToEnemy(){
        return myDiedToEntity;
    }

    /**
     * Returns cause and type of death.
     * @return boolean if character died to trap.
     */
    public boolean getDiedToTrap(){
        return myDiedToTrap;
    }
    /**
     * Method used to return value to heal for this character
     * @return int health to heal
     */
    abstract int heal();
    /**
     * Checks if passed value from characters heal ability is correct and adds health to
     * current health.
     */
    String addHealth(int healthToAdd){
        StringBuilder builder = new StringBuilder();
        int healthBeforeHeal = myCurrentHealth;
        if(healthToAdd < 0){
            throw new IllegalArgumentException("Can't add negative health to this character.");
        }
        if(myCurrentHealth > 0 && myCurrentHealth != myMaxHealth){
            if(myCurrentHealth + healthToAdd > myMaxHealth){
                myCurrentHealth = myMaxHealth;
            }
            else{
                myCurrentHealth += healthToAdd;
            }

        }
        builder.append(" [").append(myName).append("] healed for ").append(healthToAdd).
            append(" <").append(healthBeforeHeal).append(" -> ").append(myCurrentHealth).
            append("> HP:").append(myCurrentHealth).append("/").append(myMaxHealth);
        return builder.toString();
    }

    /**
     * Checks if passed value from characters heal ability is correct and adds health to
     * current health.
     */
    String bombDamage(){
        final int bombDamage = 40;
        StringBuilder builder = new StringBuilder();
        int healthBeforeDamage = myCurrentHealth;
        if(healthBeforeDamage > 0){
            if(myCurrentHealth - bombDamage > 0){
                myCurrentHealth -= bombDamage;
            }
            else{
                myCurrentHealth = 0;
            }

        }
        builder.append(" [").append(myName).append("] got hit with bomb shrapnel for ").append(bombDamage).
                append(" <").append(healthBeforeDamage).append(" -> ").append(myCurrentHealth).
                append("> HP:").append(myCurrentHealth).append("/").append(myMaxHealth);
        return builder.toString();
    }
    /**
     * Checks current health. If health is less or equal zero the character is dead.
     */
    private void checkIsDead(){
        if(myCurrentHealth <= 0){
            myCurrentHealth = 0;
            myIsDead = true;
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Fall.ogg")));
        }
    }

    /**
     * If hit chance check was successful, applies random damage value between min and max
     * values to other specified character, otherwise returns 0.
     */
    @Override
    public String attack(final DungeonCharacter theCharacter) {
        StringBuilder actionDescBuild = new StringBuilder();
        if(theCharacter == null){
            throw new IllegalArgumentException("Can't call attack on null character at DungeonCharacter attack().");
        }
        int damage = 0;
        if(attackSuccessCheck()){
            final Random rand = new Random();
            damage = rand.nextInt(myMinDamage, myMaxDamage+1);
            actionDescBuild.append("[").append(getMyName()).append("] attacked for ")
                .append(damage).append(" damage. -> ").append(
                    theCharacter.receiveDamage(damage));
        }
        else{
            actionDescBuild.append("[").append(getMyName()).append("] had missed.");
            mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Miss.ogg")));
        }
        return actionDescBuild.toString();
    }

    /**
     * Checks if random value in range 0-100 is less than or equal to hit chance.
     * @return boolean whether attack succeeded based on character hit chance.
     */
    private boolean attackSuccessCheck(){
        final Random rand = new Random();
        return (rand.nextInt(RANDOM_FROM_HUNDRED+1) <= myHitChance);
    }

    /**
     * Returns name assigned to this character.
     * @return String name for this character.
     */
    @Override
    public String getMyName(){
        return myName;
    }
    /**
     * Returns current health of this character.
     * @return int current health.
     */
    @Override
    public int getCurrentHealth() {
        return myCurrentHealth;
    }
    @Override
    public int getMaxHealth(){
        return myMaxHealth;
    }

    /**
     * Returns chance with which the character will successfully land a hit to deal damage.
     * @return int chance to hit for this character.
     */
    @Override
    public int getHitChance() {
        return myHitChance;
    }

    /**
     * Returns minimum damage this character can deal.
     * @return int min damage value.
     */
    @Override
    public int getMinDamage() {
        return myMinDamage;
    }

    /**
     * Returns maximum damage this character can deal.
     * @return int max damage value.
     */
    @Override
    public int getMaxDamage() {
        return myMaxDamage;
    }

    /**
     * Returns the speed to initiate first action during combat for this character
     * @return int value of speed.
     */
    @Override
    public int getSpeed() {
        return mySpeed;
    }

    /**
     * Returns the true-false value to check if this character is dead.
     * @return true/false value to check if dead.
     */
    @Override
    public boolean getIsDead() {
        return myIsDead;
    }
    /**
     * Returns character's position.
     * @return Position object unique to this character.
     */
    public Position getPosition(){
        return myPosition;
    }
    public void setMyPosition(final Position thePosition) {
        myPosition = thePosition;
    }
    /**
     * Converts data about character to string.
     * @return string representation of data.
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(myName).append(NEW_LINE)
            .append("Health: ").append(myCurrentHealth).append(NEW_LINE)
            .append("Damage: ").append(myMinDamage).append("-").append(myMaxDamage).append(NEW_LINE)
            .append("Hit Chance: ").append(myHitChance).append(NEW_LINE)
            .append("Speed: ").append(mySpeed).append(NEW_LINE)
            .append("Is Dead: ").append(myIsDead).append(NEW_LINE)
            .append("Position: ").append(myPosition.toString());
        return stringBuilder.toString();
    }
}

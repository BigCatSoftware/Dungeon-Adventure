package Model;

import java.util.Random;

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
    private String myName;
    /**
     * Number representing health of a character
     */
    private int myHealth;
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
    private final Position myPosition;

    /**
     * Initializes values upon creation of new Dungeon Character.
     */
    DungeonCharacter(final String theName, final int theHealth, final int theMinDamage,
                     final int theMaxDamage, final int theHitChance, final int theSpeed,
                     final int theX, final int theY){
        myName = theName;
        myHealth = theHealth;
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myHitChance = theHitChance;
        mySpeed = theSpeed;
        myPosition = new Position(theX, theY);
        myIsDead = false;
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
     * Applies damage to health of character.
     * @param incomingDamage int value of damage to apply to this character.
     */
    @Override
    public void receiveDamage(final int incomingDamage) {
        if(incomingDamage < 0){
            throw new IllegalArgumentException("receiveDamage, incoming damage parameter can't" +
                    "be negative.");
        }
        myHealth -= incomingDamage;
        checkIsDead();
    }

    /**
     * Checks current health. If health is less or equal zero the character is dead.
     */
    private void checkIsDead(){
        if(myHealth <= 0){
            myHealth = 0;
            myIsDead = true;
        }
    }

    /**
     * If hit chance check was successful, returns random damage value between min and max
     * values for this character, otherwise returns 0.
     * @return int random value of damage between min and max values for this character.
     */
    @Override
    public int attack() {
        int damage = 0;
        if(attackSuccessCheck()){
            final Random rand = new Random();
            damage = rand.nextInt(myMinDamage, myMaxDamage+1);
        }
        return damage;
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
    public String getMyName(){
        return myName;
    }
    public void setMyName(final String theName){
        if(theName.contains("\n") || theName.contains("\r")){
            throw new IllegalArgumentException("Name can't have new line characters.");
        }
        myName = theName;
    }
    /**
     * Returns current health of this character.
     * @return int current health.
     */
    @Override
    public int getCurrentHealth() {
        return myHealth;
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
     * Converts data about character to string.
     * @return string representation of data.
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(myName).append(NEW_LINE)
                .append("Health: ").append(myHealth).append(NEW_LINE)
                .append("Damage: ").append(myMinDamage).append("-").append(myMaxDamage).append(NEW_LINE)
                .append("Hit Chance: ").append(myHitChance).append(NEW_LINE)
                .append("Speed: ").append(mySpeed).append(NEW_LINE)
                .append("Is Dead: ").append(myIsDead).append(NEW_LINE)
                .append("Position: ").append(myPosition.toString());
        return stringBuilder.toString();
    }
    /**
     * This is class that is used for dungeon characters to update their locations on the map which
     * may be stored in a data structure to update the view.
     * @author Nazarii Revitskyi
     * @version July 17, 2024.
     */
    private final class Position{
        /**
         * X coordinate.
         */
        private int myX;
        /**
         * Y coordinate.
         */
        private int myY;

        /**
         * Pass X and Y coordinate to store in this object which can be later used to track
         * entity position on a map using data structure.
         * @param theX int x coordinate
         * @param theY int y coordinate
         */
        Position(final int theX, final int theY){
            init(theX, theY);
            myX = theX;
            myY = theY;
        }
        /**
         * Check for correctness. The values have to be zero or positive.
         * @param theX  int x coordinate
         * @param theY  int y coordinate
         */
        private void init(final int theX, final int theY){
            if(theX < 0 || theY < 0){
                throw new IllegalArgumentException("The constructor arguments for position object" +
                        " are numbers that are" +
                        "not negative.");
            }
        }
        /**
         * Returns integer x coordinate of this entity.
         * @return int of x coordinate.
         */
        int getMyX(){
            return myX;
        }

        /**
         * Returns integer y coordinate of this entity.
         * @return int of y coordinate.
         */
        int getMyY(){
            return myY;
        }

        /**
         * Set x position of this object to new value. Value has to be non-negative.
         * @param theX int new x coordinate for this object.
         */
        private void setMyX(final int theX){
            if(theX < 0){
                throw new IllegalArgumentException("The x cannot be set to negative value for" +
                        "character position.");
            }
            myX = theX;
        }

        /**
         * Set y position of this object to new value. Value has to be non-negative.
         * @param theY int new y coordinate for this object.
         */
        private void setMyY(final int theY){
            if(theY < 0){
                throw new IllegalArgumentException("The y cannot be set to negative value for" +
                        "character position.");
            }
            myY = theY;
        }

        /**
         * Returns string of data for this class
         * @return string of data.
         */
        @Override
        public String toString(){
            return "(" + myX +", " + myY + ")";
        }
    }
}

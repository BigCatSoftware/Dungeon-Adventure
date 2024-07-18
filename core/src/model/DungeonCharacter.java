package model;

import java.util.Random;

/**
 * Abstract dungeon character class defines variety of entities that will inhabit the dungeon.
 * @author Nazarii Revitskyi
 * @version July 17, 2024.
 */
public class DungeonCharacter implements CharacterActions {
    //TODO: getters for fields and start Enemy and Hero.
    private int myHealth;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myHitChance;
    private final int mySpeed;
    private boolean myIsDead;
    private final Position myPosition;
     DungeonCharacter(){
        myHealth = -1;
        myMinDamage = -1;
        myMaxDamage = -1;
        myHitChance = -1;
        mySpeed = -1;
        myPosition = new Position(0,0);
        myIsDead = false;
     }
    @Override
    public void moveCharacterUp() {
         int currentY = myPosition.getMyY();
         if(currentY != Integer.MAX_VALUE){
             myPosition.setMyY(++currentY);
         }
    }

    @Override
    public void moveCharacterDown() {
        int currentY = myPosition.getMyY();
        if(currentY != 0){
            myPosition.setMyY(--currentY);
        }
    }

    @Override
    public void moveCharacterLeft() {
        int currentX = myPosition.getMyX();
        if(currentX != 0){
            myPosition.setMyX(--currentX);
        }
    }

    @Override
    public void moveCharacterRight() {
         int currentX = myPosition.getMyX();
         if(currentX != Integer.MAX_VALUE){
             myPosition.setMyX(++currentX);
         }
    }

    @Override
    public void receiveDamage(final int incomingDamage) {
        if(incomingDamage < 0){
            throw new IllegalArgumentException("receiveDamage, incoming damage parameter can't" +
                "be negative.");
        }
        myHealth -= incomingDamage;
        if(myHealth < 0){
            myHealth = 0;
            myIsDead = true;
        }
    }

    @Override
    public int attack() {
         Random rand = new Random();
        return rand.nextInt(myMinDamage, myMaxDamage+1);
    }

    @Override
    public boolean healChanceOnHit() {
        return false;
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
        public int getMyX(){
            return myX;
        }

        /**
         * Returns integer y coordinate of this entity.
         * @return int of y coordinate.
         */
        public int getMyY(){
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

    }
}

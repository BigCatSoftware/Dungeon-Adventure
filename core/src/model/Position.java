package model;

import java.io.Serializable;
import java.util.Objects;

public final class Position implements Serializable {
    private static final long serialVersionUID = 1L;
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
    public Position(final int theX, final int theY){
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
    void setMyX(final int theX){
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
    void setMyY(final int theY){
        if(theY < 0){
            throw new IllegalArgumentException("The y cannot be set to negative value for" +
                    "character position.");
        }
        myY = theY;
    }
    @Override
    public boolean equals(Object theOther){
        boolean isEqual = false;
        if(theOther instanceof Position){
            final Position otherPosition = (Position)theOther;
            if(getMyY() == otherPosition.getMyY() && getMyX() == otherPosition.getMyX()){
                isEqual = true;
            }
        }
        return isEqual;
    }
    @Override
    public int hashCode(){
        return Objects.hash(getMyX(),getMyY());
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
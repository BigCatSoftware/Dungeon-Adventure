package model;

/**
 * Represents a door in the dungeon.
 * A door can be placed between rooms and can be locked or unlocked.
 *
 * @author Tiger Schueler
 * @version 27JUL24
 */
public class Door {

    private final Position myPosition;
    private boolean isLocked;

    /**
     * Constructs a door at the specified position.
     * By default, the door is unlocked.
     *
     * @param thePosition the position of the door in the dungeon
     */
    public Door(Position thePosition) {
        myPosition = thePosition;
        isLocked = false;
    }

    /**
     * Gets the position of the door.
     *
     * @return the position of the door
     */
    public Position getPosition() {
        return myPosition;
    }

    /**
     * Checks if the door is locked.
     *
     * @return true if the door is locked, false otherwise
     */
    public boolean lockedStatus() {
        return isLocked;
    }

    /**
     * Sets the locked status of the door.
     *
     * @param locked true to lock the door, false to unlock it
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}

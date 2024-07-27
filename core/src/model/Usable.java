package model;

/**
 * Interface for objects that can be used in the game.
 * Implementing this interface indicates that an object has a specific use or effect
 * that can be activated by the player or game events.
 *
 * @author Tiger Schueler
 * @version 1.1
 */
public interface Usable {

    /**
     * Defines the behavior that occurs when the object is used.
     * Implementing classes should specify the actions taken when the object is used,
     * such as applying effects, altering game state, or interacting with other game elements.
     *
     * @return
     */
    public int use();
}

package model;

/**
 * Interface for objects that can be collected in the game.
 * Implementing this interface indicates that an object can be collected,
 * triggering some effect or state change within the game.
 *
 * @author Tiger Schueler
 * @version 1.1
 */
public interface Collectible {

    /**
     * Defines the behavior that occurs when the object is collected.
     * Implementing classes should specify the actions taken when the object is collected,
     * such as adding to an inventory, increasing score, or triggering events.
     */
    void collect();
}

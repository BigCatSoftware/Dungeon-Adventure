package model;

/**
 * Defines methods that describe actions available to a dungeon character.
 * @author Nazarii Revitskyi
 * @version July 13, 2024
 */
public interface CharacterActions {
    void moveCharacterUp();
    void moveCharacterDown();
    void moveCharacterLeft();
    void moveCharacterRight();
    void receiveDamage(final int incomingDamage);
    int attack();
    boolean healChanceOnHit();
}

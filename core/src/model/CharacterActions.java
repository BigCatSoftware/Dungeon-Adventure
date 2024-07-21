package model;

/**
 * Defines methods that describe actions available to a dungeon character.
 * @author Nazarii Revitskyi
 * @version July 13, 2024
 */
interface CharacterActions {
    void moveCharacterUp();
    void moveCharacterDown();
    void moveCharacterLeft();
    void moveCharacterRight();
    String receiveDamage(final int incomingDamage);
    int attack();
    String getMyName();
    int getCurrentHealth();
    int getHitChance();
    int getMinDamage();
    int getMaxDamage();
    int getSpeed();
    boolean getIsDead();
}

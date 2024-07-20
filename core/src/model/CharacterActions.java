package Model;

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
    void receiveDamage(final int incomingDamage);
    int attack();
    String getMyName();
    void setMyName(final String theName);
    int getCurrentHealth();
    int getHitChance();
    int getMinDamage();
    int getMaxDamage();
    int getSpeed();
    boolean getIsDead();
}

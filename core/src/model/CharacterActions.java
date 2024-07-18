package Model;

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
    int receiveDamage();
    int attack();
    void specialSkill();
    boolean block();
    boolean dodge();
    int heal();
    boolean healChanceOnHit();
}

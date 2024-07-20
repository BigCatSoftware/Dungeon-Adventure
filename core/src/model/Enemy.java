package Model;

public class Enemy extends DungeonCharacter {
    /**
     * Initializes values upon creation of new Dungeon Character.
     *
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theHitChance
     * @param theSpeed
     * @param theX
     * @param theY
     */
    Enemy(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theHitChance, int theSpeed, int theX, int theY) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theHitChance, theSpeed, theX, theY);
    }
}

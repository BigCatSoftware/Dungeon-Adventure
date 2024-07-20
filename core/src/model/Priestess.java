package Model;

public class Priestess extends Hero {
    /**
     * Hero constructor instantiates block chance.
     *
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theBlockChance
     * @param theHitChance
     * @param theSpeed
     * @param theX
     * @param theY
     */
    Priestess(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theBlockChance, int theHitChance, int theSpeed, int theX, int theY) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theBlockChance, theHitChance, theSpeed, theX, theY);
    }

    @Override
    int specialAction() {
        return 0;
    }
}

package model;

/**
 * One of the monsters inhabiting the dungeon. Fast but weak and quick to heal.
 * @author Nazarii Revitskyi
 * @version July 24, 2024.
 */
public class Gremlin extends Enemy{
    //CHARACTER PARAMETER CONSTANTS
    /**
     * Gremlin health constant
     */
    private final static int GREMLIN_HEALTH = 70;
    /**
     * Gremlin min damage constant
     */
    private final static int GREMLIN_MIN_DAMAGE = 15;
    /**
     * Gremlin max damage constant
     */
    private final static int GREMLIN_MAX_DAMAGE = 30;
    /**
     * Gremlin heal chance constant
     */
    private final static int GREMLIN_HEAL_CHANCE = 40;
    /**
     * Gremlin hit chance constant
     */
    private final static int GREMLIN_HIT_CHANCE = 80;
    /**
     * Gremlin speed constant
     */
    private final static int GREMLIN_SPEED = 5;
    /**
     * Gremlin min heal constant
     */
    private final static int GREMLIN_MIN_HEAL = 20;
    /**
     * Gremlin max heal constant
     */
    private final static int GREMLIN_MAX_HEAL = 40;

    /**
     * Create Gremlin type, Enemy specialization that requires name and position on the map.
     * @param theName string name for this character
     * @param theX int x position
     * @param theY int y position
     */
    public Gremlin(final String theName, final int theX, final int theY) {
        super(theName != null ? theName : NameGenerator.getGremlinName(), GREMLIN_HEALTH,
            GREMLIN_MIN_DAMAGE, GREMLIN_MAX_DAMAGE, GREMLIN_HEAL_CHANCE, GREMLIN_HIT_CHANCE,
            GREMLIN_SPEED, GREMLIN_MIN_HEAL, GREMLIN_MAX_HEAL, theX, theY);
    }
}

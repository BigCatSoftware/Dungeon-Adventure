package model;

/**
 * One of the monsters inhabiting the dungeon. Strong but slow.
 * @author Nazarii Revitskyi
 * @version July 24, 2024
 */
public class Ogre extends Enemy{
    //CHARACTER PARAMETER CONSTANTS
    /**
     * Ogre health constant
     */
    private final static int OGRE_HEALTH = 200;
    /**
     * Ogre min damage constant
     */
    private final static int OGRE_MIN_DAMAGE = 30;
    /**
     * Ogre max damage constant
     */
    private final static int OGRE_MAX_DAMAGE = 60;
    /**
     * Ogre heal chance constant
     */
    private final static int OGRE_HEAL_CHANCE = 10;
    /**
     * Ogre hit chance constant
     */
    private final static int OGRE_HIT_CHANCE = 60;
    /**
     * Ogre speed constant
     */
    private final static int OGRE_SPEED = 2;
    /**
     * Ogre min heal constant
     */
    private final static int OGRE_MIN_HEAL = 30;
    /**
     * Ogre max heal constant
     */
    private final static int OGRE_MAX_HEAL = 60;

    /**
     * Create Ogre type, Enemy specialization that requires name and position on the map.
     * @param theName string name for this character
     * @param theX int x position
     * @param theY int y position
     */
    public Ogre(final String theName, final int theX, final int theY){
        super(theName != null ? theName : NameGenerator.getOgreName(), OGRE_HEALTH,
            OGRE_MIN_DAMAGE, OGRE_MAX_DAMAGE, OGRE_HEAL_CHANCE, OGRE_HIT_CHANCE, OGRE_SPEED,
            OGRE_MIN_HEAL, OGRE_MAX_HEAL, theX, theY);
    }
}

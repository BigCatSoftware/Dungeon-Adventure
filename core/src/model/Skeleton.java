package model;

/**
 * One of the monsters inhabiting the dungeon. Balanced stats and healing properties.
 * @author Nazarii Revitskyi
 * @version July 24, 2024.
 */
public class Skeleton extends Enemy{
    //CHARACTER PARAMETER CONSTANTS
    /**
     * Skeleton health constant
     */
    private final static int SKELETON_HEALTH = 100;
    /**
     * Skeleton min damage constant
     */
    private final static int SKELETON_MIN_DAMAGE = 30;
    /**
     * Skeleton max damage constant
     */
    private final static int SKELETON_MAX_DAMAGE = 50;
    /**
     * Skeleton heal chance constant
     */
    private final static int SKELETON_HEAL_CHANCE = 30;
    /**
     * Skeleton hit chance constant
     */
    private final static int SKELETON_HIT_CHANCE = 80;
    /**
     * Skeleton speed constant
     */
    private final static int SKELETON_SPEED = 3;
    /**
     * Skeleton min heal constant
     */
    private final static int SKELETON_MIN_HEAL = 30;
    /**
     * Skeleton max heal constant
     */
    private final static int SKELETON_MAX_HEAL = 50;

    /**
     * Create Skeleton type, Enemy specialization that requires name and position on the map.
     * @param theName string name for this character
     * @param theX int x position
     * @param theY int y position
     */
    public Skeleton(final String theName, final int theX, final int theY){
        super(theName != null ? theName : NameGenerator.getSkeletonName(), SKELETON_HEALTH,
            SKELETON_MIN_DAMAGE, SKELETON_MAX_DAMAGE, SKELETON_HEAL_CHANCE, SKELETON_HIT_CHANCE,
            SKELETON_SPEED, SKELETON_MIN_HEAL, SKELETON_MAX_HEAL, theX, theY);
    }
}

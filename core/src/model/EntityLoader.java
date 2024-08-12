package model;

import static model.DungeonCharacter.RANDOM_FROM_HUNDRED;

import com.dungeonadventure.database.SQLiteConnections;

import javax.naming.Name;

import java.util.Random;

/**
 * This class is used to populate the dungeon grid with variety of enemies.
 * @author Nazarii Revitskyi
 * @version July 28, 2024.
 */
public final class EntityLoader {
    /**
     * Number of enemies present on the map.
     */
    public static final int MONSTER_COUNT = 20;
    /**
     * Number of cells away from character to place enemy
     */
    private static final int CELLS_CLOSE_TO_PLAYER = 5;
    /**
     * Chance in percents to spawn gremlin
     */
    private static final int CHANCE_FOR_GREMLIN = 50;
    /**
     * Chance in percents to spawn skeleton
     */
    private static final int CHANCE_FOR_SKELETON = 35;
    /**
     * Chance in percents to spawn ogre
     */
    private static final int CHANCE_FOR_OGRE = 15;
    /**
     * Constructor no function.
     */
    private EntityLoader(){
    }
    public static Enemy createGremlin(final int theX, final int theY){
        return SQLiteConnections.readTable("Gremlin", NameGenerator.getGremlinName(), theX, theY);
    }
    public static Enemy createSkeleton(final int theX, final int theY){
        return SQLiteConnections.readTable("Skeleton", NameGenerator.getSkeletonName(), theX, theY);
    }
    public static Enemy createOgre(final int theX, final int theY){
        return SQLiteConnections.readTable("Ogre", NameGenerator.getOgreName(), theX, theY);
    }
    public static Enemy randomEnemy(final int theX, final int theY){
        final Random rand = new Random();
        final Enemy enemy;
        final int theRandomNum = rand.nextInt(RANDOM_FROM_HUNDRED);
        if(theRandomNum <= CHANCE_FOR_GREMLIN){
            enemy = createGremlin(theX, theY);
        }
        else if(theRandomNum <= CHANCE_FOR_GREMLIN + CHANCE_FOR_SKELETON){
            enemy = createSkeleton(theX, theY);
        }
        else{
            enemy = createOgre(theX, theY);
        }
        return enemy;
    }
}

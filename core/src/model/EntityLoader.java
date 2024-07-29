package model;

import static controller.DungeonInputProcessor.GAME_MASTER;
import static model.DungeonCharacter.RANDOM_FROM_HUNDRED;

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

    /**
     * Call to this function will populate the dungeon with enemies.
     * @param theMap the game grid of cells
     * @return new game grid of cells that has enemy positions in it.
     */
    Cell[][] placeEntities(final Cell[][] theMap){
        //entities must be away from the player in 5 cell radius.
        Random rand = new Random();
        int monstersAdded = 0;
        while(monstersAdded != MONSTER_COUNT){
            int randX = rand.nextInt(CELLS_CLOSE_TO_PLAYER,theMap.length);
            int randY = rand.nextInt(CELLS_CLOSE_TO_PLAYER,theMap.length);
            if(theMap[randX][randY].isWalkable()){
                theMap[randX][randY].addMonster(randomEnemy(randX, randY));
            }
        }
        return null;
    }
    private Enemy randomEnemy(final int theX, final int theY){
        final Random rand = new Random();
        final Enemy enemy;
        final int theRandomNum = rand.nextInt(RANDOM_FROM_HUNDRED);
        if(theRandomNum <= CHANCE_FOR_GREMLIN){
            enemy = new Gremlin(NameGenerator.getGremlinName(), theX, theY);
        }
        else if(theRandomNum <= CHANCE_FOR_GREMLIN + CHANCE_FOR_SKELETON){
            enemy = new Skeleton(NameGenerator.getSkeletonName(), theX, theY);
        }
        else{
            enemy = new Ogre(NameGenerator.getOgreName(), theX, theY);
        }
        return enemy;
    }
}

package com.dungeonadventure.database;

import model.Dungeon;
import model.Enemy;
import model.Hero;
import model.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game data including the hero, enemies, and the dungeon map.
 * This class is used to save and load the state of the game.
 *
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class GameData implements Serializable {
    private final Hero myHero; // The hero object in the game
    private final ArrayList<Enemy> myEnemies; // List of enemies in the game
    private final Dungeon myDungeon; // 2D array representing the dungeon map

    /**
     * Constructs a new GameData object with the specified hero, enemies, and map.
     *
     * @param theHero The hero object representing the player.
     * @param theEnemies A list of enemies present in the game.
     * @param theDungeon A 2D array of Tile representing the dungeon map.
     */
    public GameData(final Hero theHero, final ArrayList<Enemy> theEnemies, final Dungeon theDungeon) {
        myHero = theHero;
        myEnemies = theEnemies;
        myDungeon = theDungeon;
    }

    /**
     * Gets the hero object.
     *
     * @return The hero object representing the player.
     */
    public Hero getHero() {
        return myHero;
    }

    /**
     * Gets the list of enemies.
     *
     * @return A list of Enemy objects present in the game.
     */
    public ArrayList<Enemy> getEnemies() {
        return myEnemies;
    }

    /**
     * Gets the dungeon map.
     *
     * @return A 2D array of Tile representing the dungeon map.
     */
    public Dungeon getDungeon() {
        return myDungeon;
    }
}
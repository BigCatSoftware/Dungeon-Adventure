package model;

import com.dungeonadventure.database.GameData;

import static model.DungeonCharacter.NEW_LINE;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that tracks the activity, receives requests and sends necessary responses.
 * Tracks progress from start to end of the adventure.
 * This class is implemented as a singleton.
 *
 * @author Nazarii Revitskyi, Tiger Schueler
 * @version 10AUG24
 */
public final class GameMaster {
    //Class fields.
    private static final GameMaster myInstance = new GameMaster();

    /**
     * This is a dungeon generator that will produce a map to store.
     */
    private Dungeon myDungeon;

    /**
     * A grid of cells representing the dungeon map.
     */
    private Tile[][] myMap;
    /**
     * Hero to track on game grid and update the data based on events or changes in this object.
     */
    private Hero myPlayer;

    //TODO: change enemy to arraylist of enemies.
    private ArrayList<Enemy> myEnemies;
    private Enemy myCurrentEnemy;
    //methods
    private boolean myHeroSet;
    private boolean myIsCheats;
    /**
     * Private constructor for the GameMaster singleton.
     * Initializes the dungeon, map, and populates the dungeon with enemies.
     */
    private GameMaster(){
        myDungeon = new Dungeon();
        myDungeon.printMap();
        myMap = myDungeon.getMap();
        myPlayer = null;
        myHeroSet = false;
        myEnemies = new ArrayList<>();
        populate();
        System.out.println(getEnemyPositionsToString());
        myIsCheats = false;
    }

    /**
     * Returns the singleton instance of GameMaster.
     *
     * @return the singleton instance of GameMaster.
     */
    public static synchronized GameMaster getInstance(){
        return myInstance;
    }

    /**
     * Returns the player character for this game session.
     *
     * @return the hero character.
     * @throws IllegalArgumentException if the player has not been set.
     */
    public Hero getPlayer(){
        if(myPlayer == null){
            throw new IllegalArgumentException("Game Master hasn't defined a player object to use it elsewhere");
        }
        return myPlayer;
    }

    /**
     * Sets the player character for the game session.
     *
     * @param thePlayer the hero character to set.
     * @throws IllegalArgumentException if the player is null or if the hero has already been set.
     */
    public void setPlayer(final Hero thePlayer){
        if(thePlayer == null || myHeroSet){
            throw new IllegalArgumentException("Can't set player for this GameMaster instance.");
        }
        myPlayer = thePlayer;
        myHeroSet = true;
    }

    /**
     * Returns the player's X-coordinate on the map.
     *
     * @return the player's X-coordinate.
     */
    public int getPlayerX(){
        return myPlayer.getPosition().getMyX();
    }

    /**
     * Returns the player's Y-coordinate on the map.
     *
     * @return the player's Y-coordinate.
     */
    public int getPlayerY(){
        return myPlayer.getPosition().getMyY();
    }

    /**
     * Returns the current enemy the player is engaged with.
     *
     * @return the current enemy.
     */
    public Enemy getEnemy(){
        return myCurrentEnemy;
    }

    /**
     * Returns a list of all enemies in the dungeon.
     *
     * @return an ArrayList of all enemies.
     */
    public ArrayList<Enemy> getAllEnemies(){
        return myEnemies;
    }
    public void enemyMove(){
        //move character randomly.
        for(Enemy e : myEnemies){
            boolean[] direction = new boolean[4]; //up, right, down, left.
            direction[0] = myMap[e.getPosition().getMyX()][e.getPosition().getMyY()+1].isWalkable();
            direction[1] = myMap[e.getPosition().getMyX()+1][e.getPosition().getMyY()].isWalkable();
            direction[2] = myMap[e.getPosition().getMyX()][e.getPosition().getMyY()-1].isWalkable();
            direction[3] = myMap[e.getPosition().getMyX()-1][e.getPosition().getMyY()].isWalkable();

            Random rand = new Random();
            int choice;
            do{
                choice = rand.nextInt(4);
            }while(!direction[choice]);
            switch(choice){
                case 0:
                    e.moveCharacterUp();
                    break;
                case 1:
                    e.moveCharacterRight();
                    break;
                case 2:
                    e.moveCharacterDown();
                    break;
                case 3:
                    e.moveCharacterLeft();
                    break;
            }
        }
    }
    /**
     * Returns a string representing the positions of all enemies in the dungeon.
     *
     * @return a string of enemy positions.
     */
    public String getEnemyPositionsToString(){
        StringBuilder sb = new StringBuilder();
        for(Enemy e : myEnemies){
            sb.append("[").append(e.getMyName()).append("] is a ").
                    append(e.getClass().getSimpleName()).append(" and is at ").append(e.getPosition().
                            toString()).append(NEW_LINE);
        }
        return sb.toString();
    }
    public Enemy.Type getEnemyType(final Enemy theEnemy){
        return theEnemy.getType();
    }
    public boolean isHeroNearEnemy(){
        boolean isEnemy = false;
        for(Enemy e : myEnemies){
            isEnemy = myPlayer.getPosition().equals(e.getPosition());
            if(isEnemy){
                myCurrentEnemy = e;
                return isEnemy;
            }
        }
        return isEnemy;
    }
    /**
     * Adds a health potion to the hero's inventory.
     */
    public void heroPicksHealthPotion(){
        myPlayer.addHealthPotion();
    }

    /**
     * Uses a health potion from the hero's inventory.
     *
     * @return a string describing the result of using the health potion.
     */
    public String heroUsesHealthPotion(){
        return myPlayer.useHealthPotion();
    }

    /**
     * Uses a bomb from the hero's inventory.
     *
     * @return a string describing the result of using the bomb.
     */
    public String heroUsesBomb() {
        return myPlayer.useBomb(myCurrentEnemy);
    }

    /**
     * Returns the number of health potions the hero has.
     *
     * @return the number of health potions.
     */
    public int getHeroHealthPotions(){
        return myPlayer.getHeroHealthPotions();
    }

    /**
     * Adds a key to the hero's inventory.
     */
    public void heroPicksKey(){
        myPlayer.addKey();
    }

    /**
     * Returns the number of keys the hero has.
     *
     * @return the number of keys.
     */
    public int getHeroKeys(){
        return myPlayer.getHeroKeys();
    }

    public void heroPicksBomb() {
        myPlayer.addBomb();
    }

    public int getHeroBombs() {
        return myPlayer.getHeroBombs();
    }

    public String heroTrapDamage(final Tile theTile){
        return myPlayer.harmFromTrap(theTile);
    }
    public String getHeroDeathLog(){
        String message = "[" + myPlayer.getMyName() + "] gave up all hope...";
        if(myPlayer.getIsDead()){
            if(myPlayer.getDiedToEnemy()){
                message = "[" + myPlayer.getMyName() + "] - " + myPlayer.getClass().getSimpleName() +
                        " had died while fighting [" + myCurrentEnemy.getMyName() + "] - "
                        + myCurrentEnemy.getType().toString();
            }
            else if(myPlayer.getDiedToTrap()){
                message = "[" + myPlayer.getMyName() + "] - " + myPlayer.getClass().getSimpleName() +
                        " had died in trap while exploring the dungeon.";
            }
        }
        return message;
    }

    /**
     * Populates the dungeon with enemies.
     * Adds random enemies to the map at walkable positions.
     */
    private void populate(){
        Random rand = new Random();
        myEnemies.add(EntityLoader.randomEnemy(1,2));
        System.out.println("Current Enemy Count: " + myEnemies.size());
        while(myEnemies.size() != 20){
            int x = rand.nextInt(50);
            int y = rand.nextInt(50);
            if(myMap[x][y].isWalkable()){
                myEnemies.add(EntityLoader.randomEnemy(x,y));
            }
        }
    }

    /**
     * Removes the current enemy from the list if it is dead.
     */
    public void removeCurrentEnemyIfDead(){
        if(myCurrentEnemy.getIsDead()){
            myEnemies.remove(myCurrentEnemy);
        }
    }

    /**
     * Restarts the game by regenerating the dungeon, resetting the player, and repopulating
     * the dungeon with enemies.
     */
    public void restart(){
        myDungeon = new Dungeon();
        myDungeon.printMap();
        myMap = myDungeon.getMap();
        myPlayer = null;
        myHeroSet = false;
        myEnemies = new ArrayList<>();
        myIsCheats = false;
        populate();
        System.out.println(getEnemyPositionsToString());
    }
    public void updateMapFOW(){
        myDungeon.updateFOW(getPlayerX(), getPlayerY(), 3, myIsCheats);
    }
    public boolean[][] getMapFOW(){
        return myDungeon.getMapFOW();
    }
    public boolean[][] getMapExploredFOW(){
        return myDungeon.getMapExploredFOW();
    }
    public void toggleCheats(){
        if(myIsCheats){
            myIsCheats = false;
            myDungeon.cheatMapVis(false);
        }
        else{
            myIsCheats = true;
            myDungeon.cheatMapVis(true);
        }
    }
    /**
     * Returns Tile[][] grid of cells that define the game grid and store tile, position,
     * enemy arraylist, item.
     * @return Tile[][] grid of cells that define the game grid.
     */
    public Tile[][] getMap(){
        return myMap;
    }

    /**
     * Compare the speed of two characters. Returns positive if first is faster than second.
     * Preferable use: first is faster than second.
     * @param theFirst DungeonCharacter that is compared first.
     * @param theSecond DungeonCharacter that is compared second.
     * @return Turns for this character compared to other character.
     */
    public int compareSpeed(final DungeonCharacter theFirst, final DungeonCharacter theSecond){
        return theFirst.compareSpeed(theSecond);
    }

    /**
     * Performs an attack by the player on the current enemy.
     *
     * @return a string describing the result of the attack.
     * @throws IllegalStateException if the current enemy or player is null.
     */
    public String playerPerformAttack(){
        if(myCurrentEnemy == null){
            throw new IllegalStateException("GameMaster issued player attack enemy but the enemy is null");
        }
        if(myPlayer == null){
            throw new IllegalStateException("GameMaster issued player attack enemy but the player is null");
        }
        return myPlayer.attack(myCurrentEnemy);
    }

    /**
     * Performs an attack by the current enemy on the player.
     *
     * @return a string describing the result of the attack.
     * @throws IllegalStateException if the current enemy or player is null.
     */
    public String enemyPerformAttack(){
        if(myCurrentEnemy == null){
            throw new IllegalStateException("GameMaster issued enemy <attack> player but the enemy is null");
        }
        if(myPlayer == null){
            throw new IllegalStateException("GameMaster issued enemy <attack> player but the player is null");
        }
        return myCurrentEnemy.attack(myPlayer);
    }

    /**
     * Performs a special action by the player on the current enemy.
     *
     * @return a string describing the result of the special action.
     * @throws IllegalStateException if the current enemy or player is null, or if the player is not a recognized hero type.
     */
    public String specialActionPerform(){
        if(myCurrentEnemy == null){
            throw new IllegalStateException("GameMaster issued player <special action> but the enemy is null");
        }
        if(myPlayer == null){
            throw new IllegalStateException("GameMaster issued player <special action> but the player is null");
        }
        final String result;
        if(myPlayer instanceof Warrior){
            result = ((Warrior) myPlayer).specialAction(myCurrentEnemy);
        }
        else if(myPlayer instanceof Priestess){
            result = ((Priestess) myPlayer).specialAction();
        }
        else if(myPlayer instanceof Thief){
            result = ((Thief) myPlayer).specialAction(myCurrentEnemy);
        }
        else {
            throw new IllegalStateException("GameMaster player is not an instance " +
                    "of concrete implementation of Hero abstract class."
                    + myPlayer.getClass().getSimpleName());
        }
        return result;
    }
    public Dungeon getDungeon() {
        return myDungeon;
    }
    public void loadGame(final GameData theGameData) {
        // Load player data
        myPlayer = theGameData.getHero();
        myHeroSet = true;

        // Load dungeon data
        myDungeon = theGameData.getDungeon();
        myMap = myDungeon.getMap();

        // Load enemy data
        myEnemies = theGameData.getEnemies();
    }
}
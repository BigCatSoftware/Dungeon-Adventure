package model;

import com.dungeonadventure.database.DatabaseManager;
import com.dungeonadventure.database.GameData;

import static model.DungeonCharacter.NEW_LINE;

import java.util.ArrayList;
import java.util.List;
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
    private Tile[][] myMap; //TODO: change to Room array to send to Entity and Item loader to populate.
    /**
     * Hero to track on game grid and update the data based on events or changes in this object.
     */
    private Hero myPlayer;

    //TODO: change enemy to arraylist of enemies.
    /**
     * List of enemies present in the dungeon.
     */
    private ArrayList<Enemy> myEnemies;

    /**
     * The enemy that the player is currently engaged with.
     */
    private Enemy myCurrentEnemy;

    /**
     * Flag indicating whether the hero has been set.
     */
    private boolean myHeroSet;
    private final DatabaseManager dbManager; // DatabaseManager instance
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
        dbManager = new DatabaseManager();
        populate();
        System.out.println(getEnemyPositionsToString());
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

    /**
     * Checks if the hero is near an enemy.
     *
     * @return true if the hero is near an enemy, false otherwise.
     */
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
     * Checks if the hero is near a health potion.
     *
     * @return true if the hero is near a health potion, false otherwise.
     */
    public boolean isHeroNearHealthPotion() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.HEALTH_POTION;
    }

    /**
     * Checks if the hero is near a key.
     *
     * @return true if the hero is near a key, false otherwise.
     */
    public boolean isHeroNearKey() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.KEY;
    }

    /**
     * Checks if the hero is near a bomb.
     *
     * @return true if the hero is near a bomb, false otherwise.
     */
    public boolean isHeroNearBomb() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.BOMB;
    }

    /**
     * Checks if the hero is near the exit.
     *
     * @return true if the hero is near the exit, false otherwise.
     */
    public boolean isHeroNearExit() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.EXIT;
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
        return myPlayer.useBomb();
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

    /**
     * Checks if the hero is near a poison potion.
     *
     * @return true if the hero is near a poison potion, false otherwise.
     */
    public boolean isHeroNearPoisonPotion() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.POISON_POTION;
    }

    /**
     * Checks if the hero is near a pit trap.
     *
     * @return true if the hero is near a pit trap, false otherwise.
     */
    public boolean isHeroNearPitTrap() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.PIT_TRAP;
    }

    public String heroTrapDamage(final int theDamage){
        return myPlayer.harmFromTrap(theDamage);
    }
    public String getHeroDeathLog(){
        String message = "[" + myPlayer.getMyName() + "] gave up all hope...";
        if(myPlayer.getIsDead()){
            if(myPlayer.getDiedToEnemy()){
                message = "[" + myPlayer.getMyName() + "] - " + myPlayer.getClass().getSimpleName() +
                    " had died while fighting [" + myCurrentEnemy.getMyName() + "] - "
                    + myCurrentEnemy.getClass().getSimpleName();
            }
            else if(myPlayer.getDiedToTrap()){
                message = "[" + myPlayer.getMyName() + "] - " + myPlayer.getClass().getSimpleName() +
                    " had died while exploring the dungeon to <Poison>.";
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
        populate();
        System.out.println(getEnemyPositionsToString());
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

    public void saveGame() {
        int playerX = myPlayer.getPosition().getMyX();
        int playerY = myPlayer.getPosition().getMyY();
        //String inventory = myPlayer.getInventoryAsString(); // Implement this method
        List<Enemy> enemiesData = new ArrayList<>();

        for (Enemy enemy : myEnemies) {
            enemiesData.add(new Enemy(enemy.getMyName(), enemy.getCurrentHealth(), enemy.getMinDamage(),
                    enemy.getMaxDamage(), enemy.getMyHealChance(), enemy.getHitChance(),
                    enemy.getSpeed(), enemy.getMyMinHeal(), enemy.getMyMaxHeal(), enemy.getPosition().getMyX(),
                    enemy.getPosition().getMyY()));
        }

        dbManager.saveGame(playerX, playerY, enemiesData);
    }

    public void loadGame() {
        GameData gameData = dbManager.loadGame();
        if (gameData != null) {
            Position inPosition = new Position(gameData.getPlayerX(), gameData.getPlayerY());
            myPlayer.setMyPosition(inPosition);
            //myPlayer.loadInventoryFromString(gameData.getInventory()); // Implement this method
            myEnemies.clear(); // Clear current enemies before loading
            for (Enemy enemyData : gameData.getEnemies()) {
                Enemy enemy = new Enemy(enemyData.getMyName(), enemyData.getCurrentHealth(), enemyData.getMinDamage(),
                        enemyData.getMaxDamage(), enemyData.getMyHealChance(), enemyData.getHitChance(),
                        enemyData.getSpeed(), enemyData.getMyMinHeal(), enemyData.getMyMaxHeal(), enemyData.getPosition().getMyX(),
                        enemyData.getPosition().getMyY());
                myEnemies.add(enemy);
            }
        }
    }
}

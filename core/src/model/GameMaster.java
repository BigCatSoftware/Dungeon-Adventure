package model;

import static model.DungeonCharacter.NEW_LINE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class that tracks the activity, receives requests and sends necessary responses.
 * Tracks progress from start to end of the adventure.
 * @author Nazarii Revitskyi
 * @version July 28, 2024.
 */
public final class GameMaster {
    //Class fields.
    private static final GameMaster myInstance = new GameMaster();
    /**
     * This is a dungeon generator that will produce a map to store.
     */
    private Dungeon myDungeon;
    /**
     * This is a grid of cells that will be used to check game status.
     */
    private Tile[][] myMap; //TODO: change to Room array to send to Entity and Item loader to populate.
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
     * Create GameMaster that will drive the main game logic.
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
    public static synchronized GameMaster getInstance(){
        return myInstance;
    }

    /**
     * Returns a player instance that was made for this game session after user created it.
     * @return hero object.
     */
    public Hero getPlayer(){
        if(myPlayer == null){
            throw new IllegalArgumentException("Game Master hasn't defined a player object to use it elsewhere");
        }
        return myPlayer;
    }
    public void setPlayer(final Hero thePlayer){
        if(thePlayer == null || myHeroSet){
            throw new IllegalArgumentException("Can't set player for this GameMaster instance.");
        }
        myPlayer = thePlayer;
        myHeroSet = true;
    }
    public int getPlayerX(){
        return myPlayer.getPosition().getMyX();
    }
    public int getPlayerY(){
        return myPlayer.getPosition().getMyY();
    }
    public Enemy getEnemy(){
        return myCurrentEnemy;
    }
    public ArrayList<Enemy> getAllEnemies(){
        return myEnemies;
    }
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

    public boolean isHeroNearHealthPotion() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.HEALTH_POTION;
    }
    public void heroPicksHealthPotion(){
        myPlayer.addHealthPotion();
    }
    public String heroUsesHealthPotion(){
        return myPlayer.useHealthPotion();
    }
    public int getHeroHealthPotions(){
        return myPlayer.getHeroHealthPotions();
    }
    public void heroPicksKey(){
        myPlayer.addKey();
    }
    public int getHeroKeys(){
        return myPlayer.getHeroKeys();
    }
    public boolean isHeroNearPoisonPotion() {
        final GameMaster gm = GameMaster.getInstance();
        final Tile[][] map = gm.getMap();
        final int playerY = gm.getPlayerY();
        final int playerX = gm.getPlayerX();
        return map[playerX][playerY] == Tile.POISON_POTION;
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
                    + myCurrentEnemy.getType().toString();
            }
            else if(myPlayer.getDiedToTrap()){
                message = "[" + myPlayer.getMyName() + "] - " + myPlayer.getClass().getSimpleName() +
                    " had died while exploring the dungeon to <Poison>.";
            }
        }
        return message;
    }
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
    public void removeCurrentEnemyIfDead(){
        if(myCurrentEnemy.getIsDead()){
            myEnemies.remove(myCurrentEnemy);
        }
    }
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

    public String playerPerformAttack(){
        if(myCurrentEnemy == null){
            throw new IllegalStateException("GameMaster issued player attack enemy but the enemy is null");
        }
        if(myPlayer == null){
            throw new IllegalStateException("GameMaster issued player attack enemy but the player is null");
        }
        return myPlayer.attack(myCurrentEnemy);
    }
    public String enemyPerformAttack(){
        if(myCurrentEnemy == null){
            throw new IllegalStateException("GameMaster issued enemy <attack> player but the enemy is null");
        }
        if(myPlayer == null){
            throw new IllegalStateException("GameMaster issued enemy <attack> player but the player is null");
        }
        return myCurrentEnemy.attack(myPlayer);
    }
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
}

package model;

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
    private final Dungeon myDungeon;
    /**
     * This is a grid of cells that will be used to check game status.
     */
    private final Tile[][] myMap; //TODO: change to Room array to send to Entity and Item loader to populate.
    /**
     * Hero to track on game grid and update the data based on events or changes in this object.
     */
    private Hero myPlayer;
    //TODO: change enemy to arraylist of enemies.
    private final Enemy myEnemy = EntityLoader.randomEnemy(1,2);
    //methods
    private boolean myHeroSet;
    /**
     * Create GameMaster that will drive the main game logic.
     */
    private GameMaster(){
        myDungeon = new Dungeon();
        myDungeon.printMap();
        myMap = myDungeon.getMap();
        myPlayer = null;
        myHeroSet = false;
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
        return myEnemy;
    }
    public boolean isHeroNearEnemy(){
        return myPlayer.getPosition().equals(myEnemy.getPosition());
    }
    /**
     * Returns Tile[][] grid of cells that define the game grid and store tile, position,
     * enemy arraylist, item.
     * @return Tile[][] grid of cells that define the game grid.
     */
    public Tile[][] getMap(){
        return myMap;
    }
}

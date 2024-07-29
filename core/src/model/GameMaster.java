package model;

/**
 * Class that tracks the activity, receives requests and sends necessary responses.
 * Tracks progress from start to end of the adventure.
 * @author Nazarii Revitskyi
 * @version July 28, 2024.
 */
public final class GameMaster {
    //Class fields.
    /**
     * This is a dungeon generator that will produce a map to store.
     */
    private final Dungeon myDungeon;
    /**
     * This is a grid of cells that will be used to check game status.
     */
    private final Cell[][] myMap;
    /**
     * Hero to track on game grid and update the data based on events or changes in this object.
     */
    private Hero myPlayer;
    //methods

    /**
     * Create GameMaster that will drive the main game logic.
     * @param thePlayer hero playable character by a user.
     */
    public GameMaster(final Hero thePlayer){
        myDungeon = new Dungeon();
        myDungeon.printMap();
        myMap = myDungeon.getMap();
        myPlayer = thePlayer;
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
    public int getPlayerX(){
        return myPlayer.getPosition().getMyX();
    }
    public int getPlayerY(){
        return myPlayer.getPosition().getMyY();
    }
    /**
     * Returns Cell[][] grid of cells that define the game grid and store tile, position,
     * enemy arraylist, item.
     * @return Cell[][] grid of cells that define the game grid.
     */
    public Cell[][] getMap(){
        return myMap;
    }
}

package model;

import java.util.ArrayList;

/**
 * Defines object that will be used in tracking the map of DungeonAdventure. After Dungeon is
 * generated the grid is composed of Cell objects that hold their type of Tile, position,
 * list of entities and item.
 * @author Nazarii Revitskyi
 * @version July 27, 2024.
 */
public final class Cell {
    /**
     * Tile object.
     */
    private Tile myTile;
    /**
     * Position object.
     */
    private final Position myPosition;
    /**
     * ArrayList of Entities on this Cell
     */
    private final ArrayList<Enemy> myMonsterList = new ArrayList<>();
    /**
     * Item object on this Cell
     */
    private Item myItem = null;
    /**
     * Checks if entity movement is possible on this cell.
     */
    private boolean myWalkable = false;

    /**
     * Constructs a grid cell that will hold the tile type, position on xy plane, item in cell,
     * and if it is walkable by entity.
     * @param theTile tile type this cell holds
     * @param thePosition position of this cell on xy plane
     * @param theWalkable true/false if possible to walk on this cell.
     */
    Cell(final Tile theTile, final Position thePosition, final boolean theWalkable){
        init(theTile, thePosition);
        myTile = theTile;
        myPosition = thePosition;
        myWalkable = theWalkable;
    }

    /**
     * Checks data upon construction
     * @param theTile tile type of this cell.
     * @param thePosition position of this cell on the game grid.
     */
    private void init(final Tile theTile, final Position thePosition){
        if(theTile == null || thePosition == null){
            throw new IllegalArgumentException("Cell has undefined state");
        }
    }

    /**
     * Returns the tile type of this cell.
     * @return tile type of this cell.
     */
    public Tile getTile(){
        return myTile;
    }

    /**
     * Returns the position of this cell on the game grid.
     * @return the position of this cell on the game grid.
     */
    public Position getPosition(){
        return myPosition;
    }

    /**
     * Returns a list of monsters that are in this cell.
     * @return array list of monsters that are in this cell.
     */
    public ArrayList<Enemy> getMyMonsterList(){
        return myMonsterList;
    }

    /**
     * Returns item stored in this cell.
     * @return item stored in this cell.
     */
    public Item getItem(){
        return myItem;
    }

    /**
     * Sets the tile for this cell. Used upon grid creation.
     * @param theTile type of tile on this cell.
     */
    public void setTile(final Tile theTile){
        switch(theTile){
            case WALL:
            case DOOR:
                myWalkable = false;
                break;
            case FLOOR:
                myWalkable = true;
                break;
            default:
                throw new IllegalArgumentException("Bad tile state for cell parameter.");
        }
        myTile = theTile;
    }
    /**
     * Adds monster to monster list stored in this cell.
     * @param theEntity monster to store in this cell.
     */
    public void addMonster(final Enemy theEntity){
        myMonsterList.add(theEntity);
    }

    /**
     * Removes last added monster from this cell.
     */
    public void removeLastMonster(){
        if(!myMonsterList.isEmpty()){
            myMonsterList.remove(myMonsterList.size()-1);
        }
    }

    /**
     * Sets an item in this cell. If the parameter is null - there is no item in this cell.
     * @param theItem item to store in this cell.
     */
    public void setItem(final Item theItem){
        if(theItem == null){
            myWalkable = true;
        }
        myItem = theItem;
    }
}

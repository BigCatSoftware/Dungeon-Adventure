package model;

/**
 * Enumeration representing the types of tiles in the dungeon map.
 *
 * @author Tiger Schueler
 * @version 1.2
 */
public enum Tile {
    /**
     * Wall tile, represented by the '#' symbol.
     */
    WALL('#', false),

    /**
     * Floor tile, represented by the '.' symbol.
     */
    FLOOR('.', true),

    /**
     * Door tile, represented by the 'D' symbol.
     */
    DOOR('D',false),

    /**
     * Open Door tile, represented by the 'O' symbol.
     */
    OPEN_DOOR('O',true),

    /**
     * Key tile, represented by the 'K' symbol.
     */
    KEY('K', true),

    /**
     * Exit tile, represented by the 'E' symbol.
     */
    EXIT('E', true);
    ;

    private final char mySymbol;
    private final boolean myWalkable;

    /**
     * Constructor for the Tile enum.
     *
     * @param theSymbol The character symbol representing the tile.
     */
    Tile(final char theSymbol, final boolean theWalkable) {
        mySymbol = theSymbol;
        myWalkable = theWalkable;
    }

    /**
     * Gets the symbol representing the tile.
     *
     * @return The character symbol of the tile.
     */
    public char getSymbol() {
        return mySymbol;
    }

    public boolean isWalkable() {
        return myWalkable;
    }
}

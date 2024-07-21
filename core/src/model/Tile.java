package Model;

/**
 * Enumeration representing the types of tiles in the dungeon map.
 *
 * @author Tiger Schueler
 * @version 1.2
 */
public enum Tile {
    /**
     * Wall tile, represented by the '# symbol.
     */
    WALL('#'),

    /**
     * Floor tile, represented by the '.' symbol.
     */
    FLOOR('.'),

    /**
     * Door tile, represented by the 'D' symbol.
     */
    DOOR('D');

    private final char mySymbol;

    /**
     * Constructor for the Tile enum.
     *
     * @param theSymbol The character symbol representing the tile.
     */
    Tile(final char theSymbol) {
        mySymbol = theSymbol;
    }

    /**
     * Gets the symbol representing the tile.
     *
     * @return The character symbol of the tile.
     */
    public char getSymbol() {
        return mySymbol;
    }
}

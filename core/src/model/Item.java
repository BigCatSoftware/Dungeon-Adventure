package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Class representing an item in the game.
 * Items can be both collectible and usable, providing specific effects or values
 * when used or collected by the player.
 *
 * @author Tiger Schueler
 */
public class Item extends GameObject implements Usable, Collectible {

    private final static int TILE_SIZE = 16;

    private final String myName;
    private final String myType;
    private final String myEffect;
    private final int myValue;
    private Texture myTexture;
    private Position myPosition;

    /**
     * Constructs an Item with the specified position and texture.
     * Default item properties are set, including a name, type, effect, and value.
     *
     * @param thePosition The position of the item in the game world.
     */
    public Item(Position thePosition) {
        super(thePosition);
        myTexture = new Texture("item.png");
        myName = "Item";
        myType = "Item";
        myEffect = "";
        myValue = -10;
    }

    /**
     * Renders the item on the screen using the specified SpriteBatch.
     * The item's texture is drawn at its position in the game world.
     *
     * @param theSpriteBatch The SpriteBatch used for drawing the texture.
     */
    void render(final SpriteBatch theSpriteBatch) {
        theSpriteBatch.draw(myTexture, myPosition.getMyX() * TILE_SIZE,
                myPosition.getMyY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Defines the behavior when the item is collected.
     * This method should be implemented to specify actions taken when the item is collected,
     * such as adding it to the player's inventory or triggering an event.
     */
    @Override
    public void collect() {
        // Implementation for collecting the item
    }

    /**
     * Defines the behavior that occurs when the object is used.
     * Implementing classes should specify the actions taken when the object is used,
     * such as applying effects, altering game state, or interacting with other game elements.
     *
     * @return int
     */
    @Override
    public int use() {
        return myValue;
    }
}

package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class representing a game object in the dungeon.
 * This class encapsulates a game object's position and texture,
 * and provides methods for rendering and interaction.
 *
 * @author Tiger Schueler
 * @version 1.1
 */
public class GameObject {

    private final Position myPosition;
    private final Texture myTexture = new Texture("item.png");
    private static final int TILE_SIZE = 16;

    /**
     * Constructs a GameObject with the specified position and texture.
     *
<<<<<<< HEAD
     */
    public GameObject() {
        myPosition = new Position (1, 1);
=======
     * @param thePosition The position of the object in the game world.
     */
    public GameObject(final Position thePosition) {
        myPosition = thePosition;
>>>>>>> nazarii_branch
    }

    /**
     * Defines the interaction behavior for the game object.
     * This method should be overridden by subclasses to specify
     * the unique interaction behavior for different types of game objects.
     */
    public void interact() {
        // Default implementation (intended to be overridden by subclasses)
    }

    /**
     * Renders the game object on the screen using the specified SpriteBatch.
     * The object's texture is drawn at its position in the game world.
     *
     * @param theBatch The SpriteBatch used for drawing the texture.
     */
    void render(final SpriteBatch theBatch) {
        theBatch.draw(myTexture, myPosition.getMyX() * TILE_SIZE,
                myPosition.getMyY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }


}

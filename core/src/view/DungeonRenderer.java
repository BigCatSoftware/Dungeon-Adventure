package view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import model.Cell;
import model.GameMaster;
import model.Tile;

/**
 * Class responsible for rendering the dungeon map using textures.
 * It draws the walls, floors, and doors on the screen based on the dungeon layout.
 */
public class DungeonRenderer {
    private final Texture myWallTexture;
    private final Texture myFloorTexture;
    private final Texture myDoorTexture;
    private final int TILE_SIZE = 16;

    /**
     * Constructor for creating a DungeonRenderer.
     *
     */
    public DungeonRenderer() {
        myWallTexture = new Texture("wall.png");
        myFloorTexture = new Texture("floor.png");
        myDoorTexture = new Texture("door.png");
    }

    /**
     * Renders the dungeon map using the provided SpriteBatch.
     * It iterates through the dungeon map and draws the appropriate texture
     * for walls, floors, and doors at each tile location.
     *
     * @param theBatch The SpriteBatch used for drawing textures.
     */
    public void render(final SpriteBatch theBatch) {
        Tile[][] map = GameMaster.getInstance().getMap(); //Tile[][] to Cell[][]
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Texture texture = null;
                if (map[i][j] == Tile.WALL) {//added .getTile()
                    texture = myWallTexture;
                } else if (map[i][j] == Tile.FLOOR) { //.getTile()
                    texture = myFloorTexture;
                } else if (map[i][j] == Tile.DOOR) { //.getTile()
                    texture = myDoorTexture;
                }
                if (texture != null) {
                    theBatch.draw(texture, i * TILE_SIZE, j * TILE_SIZE, //fixed typo where x is j and y is i resulting in mirrored dungeon on x axis.
                            TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    /**
     * Disposes of the textures used by the renderer.
     * This should be called when the renderer is no longer needed to free up resources.
     */
    public void dispose() {
        myWallTexture.dispose();
        myFloorTexture.dispose();
        myDoorTexture.dispose();
    }
}
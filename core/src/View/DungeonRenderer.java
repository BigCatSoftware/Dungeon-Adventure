package View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import model.Dungeon;
import model.Tile;

public class DungeonRenderer {
    private final Dungeon myDungeon;
    private final Texture myWallTexture;
    private final Texture myFloorTexture;
    private final Texture myDoorTexture;
    private final int TILE_SIZE = 16;

    public DungeonRenderer(final Dungeon theDungeon) {
        myDungeon = theDungeon;
        myWallTexture = new Texture("wall.png");
        myFloorTexture = new Texture("floor.png");
        myDoorTexture = new Texture("door.png");
    }

    public void render(final SpriteBatch theBatch) {
        Tile[][] map = myDungeon.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Texture texture = null;
                if (map[i][j] == Tile.WALL) {
                    texture = myWallTexture;
                } else if (map[i][j] == Tile.FLOOR) {
                    texture = myFloorTexture;
                } else if (map[i][j] == Tile.DOOR) {
                    texture = myDoorTexture;
                }
                if (texture != null) {
                    theBatch.draw(texture, j * TILE_SIZE, i * TILE_SIZE,
                            TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
    public void dispose() {
        myWallTexture.dispose();
        myFloorTexture.dispose();
        myDoorTexture.dispose();
    }
}

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import model.Cell;
import model.Dungeon;
import model.NameGenerator;
import model.Tile;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class EntityMovementTests {
    Dungeon myDungeon = new Dungeon();
    @Test
    void getMap(){
        myDungeon.printMap();
    }
    void printMap(final Cell[][] map){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Texture texture = null;
                if (map[i][j].getTile() == Tile.WALL) {//added .getTile()
                    sb.append('#');
                } else if (map[i][j].getTile() == Tile.FLOOR) { //.getTile()
                    sb.append('.');
                } else if (map[i][j].getTile() == Tile.DOOR) { //.getTile()
                    sb.append('D');
                }
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb.toString());
    }
}

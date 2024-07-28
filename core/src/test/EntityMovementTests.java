import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.Cell;
import model.Dungeon;
import model.NameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class EntityMovementTests {
    Dungeon myDungeon = new Dungeon();
    @Test
    void getMap(){
        Cell[][] myMap = myDungeon.getMap();

    }
}

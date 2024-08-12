//import model.DungeonCharacter;
//import model.Position;
//import model.Warrior;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//
//import static org.junit.jupiter.api.Assertions.*;

//public class TestItem {
//
//    private DungeonCharacter myDungeonCharacter;
//    private Position myItemPosition;
//
//    @BeforeEach
//    public void setUp() {
//        myDungeonCharacter = new Warrior("Aragon", 0, 0);
//        myItemPosition = new Position(1, 1);
//    }
//
//    @Test
//    public void testConstructor() {
//        // Ensure the item is instantiated with default values
//    }
//
//    @Test
//    public void testUseItemOnWarrior() {
//        int initialHealth = myDungeonCharacter.getCurrentHealth();
//        int poisonAmount = -10;
//        myDungeonCharacter.getCurrentHealth() += myPoisonItem.use();
//        assertEquals(initialHealth + poisonAmount,
//                myDungeonCharacter.getCurrentHealth());
//    }
//}

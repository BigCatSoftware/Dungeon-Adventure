import static org.junit.jupiter.api.Assertions.assertFalse;

import Model.NameGenerator;
import org.junit.jupiter.api.Test;

/**
 * This class tests the correctness of implementation of name generating class.
*/
public class TestNameGenerator {
    //TODO: revert constructor back to package-protected.
    private final NameGenerator myNameGenerator = new NameGenerator();
    @Test
    void testNameGeneratorHasDataAfterBeingInstantiated(){
        String strNameGeneratorData = myNameGenerator.toString();
        assertFalse(strNameGeneratorData.isEmpty());
        System.out.println(strNameGeneratorData);
    }
}

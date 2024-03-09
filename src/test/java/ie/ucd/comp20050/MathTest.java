package ie.ucd.comp20050;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MathTest {

    @Test
    void testMath() {
        int x1 = 5;
        int y1 = 5;
        int x2 = 10;
        int y2 = 10;

        assertEquals(7, Common.pointsDistance(5, 5, 10, 10));
        assertEquals(35, Common.pointsDistance(13, 47, 28, 15));
    }

}

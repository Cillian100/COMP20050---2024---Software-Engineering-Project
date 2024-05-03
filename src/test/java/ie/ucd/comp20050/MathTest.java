package ie.ucd.comp20050;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {

    @Test
    void testDistanceFormula() {
        assertEquals(7, MathUtils.pointsDistance(5, 5, 10, 10));
        assertEquals(35, MathUtils.pointsDistance(13, 47, 28, 15));
        assertEquals(47.378, MathUtils.pointsDistance(32.1, 53.2, 65.3, 19.4), 0.0001);
    }

    @Test
    void testModuloRange() {
        assertEquals(23, MathUtils.calculateModuloRange(20, -40, 3));
        assertEquals(20, MathUtils.calculateModuloRange(20, 20, 3));
        assertEquals(20, MathUtils.calculateModuloRange(20, 40, 3));
        assertEquals(4, MathUtils.calculateModuloRange(20, 19, 3));
    }

    @Test
    void testSquare() {
        assertEquals(25, MathUtils.squareDouble(5));
        assertEquals(100, MathUtils.squareDouble(10));
        assertEquals(153.76, MathUtils.squareDouble(12.4), 0.0001);
        assertEquals(4121.64, MathUtils.squareDouble(64.2), 0.0001);
    }

}

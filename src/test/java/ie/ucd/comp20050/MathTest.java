package ie.ucd.comp20050;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {

    @Test
    void testDistanceFormula() {
        assertEquals(7, MathUtils.pointsDistance(5, 5, 10, 10));
        assertEquals(35, MathUtils.pointsDistance(13, 47, 28, 15));
    }

    @Test
    void testModuloRange() {
        assertEquals(23, MathUtils.calculateModuloRange(20, -40, 3));
        assertEquals(20, MathUtils.calculateModuloRange(20, 20, 3));
        assertEquals(20, MathUtils.calculateModuloRange(20, 40, 3));
        assertEquals(4, MathUtils.calculateModuloRange(20, 19, 3));
    }

}

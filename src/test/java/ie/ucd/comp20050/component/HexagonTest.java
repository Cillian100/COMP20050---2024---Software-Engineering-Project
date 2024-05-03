package ie.ucd.comp20050.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HexagonTest {

    @Test
    void testCreation() {
        Hexagon alpha = new Hexagon(1000, 1000, 5, 60);
        assertEquals(6, alpha.getNumberOfPoints());
        assertEquals(1433, alpha.getMiddleX(), 0.1);
    }

}

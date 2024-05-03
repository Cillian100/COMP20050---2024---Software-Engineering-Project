package ie.ucd.comp20050.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    /**
     * Ensures that players are successfully created, and scores properly initialised
     */
    @Test
    void testCreation() {
        Player alpha = new Player(0);
        Player beta = new Player(13);

        assertEquals(0, alpha.getScore());
        assertEquals(13, beta.getScore());
    }

    /**
     * Ensures that player's scores are properly updated
     */
    @Test
    void testUpdateScore() {
        Player alpha = new Player(0);

        alpha.incrementScore();
        assertEquals(1, alpha.getScore());

        alpha.add5ToScore();
        assertEquals(6, alpha.getScore());
    }

}

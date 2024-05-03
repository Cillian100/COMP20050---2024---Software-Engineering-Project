package ie.ucd.comp20050.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaserRayTest {

    /**
     * Ensures that a Laser Ray can be properly initialised and manipulated
     */
    @Test
    void testMovement() {
        LaserRay ray = new LaserRay(0, 0, 5, 5);
        assertTrue(ray.getPosX() == 0 && ray.getPosY() == 0);

        ray.setPosX(5);
        ray.setPosY(-7);
        assertTrue(ray.getPosX() == 5 && ray.getPosY() == -7);

        ray.changeDirection(60);
        assertEquals(60, ray.getDirection());

        ray.changeDirection(180);
        assertEquals(180, ray.getDirection());
    }

    /**
     * Ensures that a laser ray's collision state properly updates
     */
    @Test
    void testCollide() {
        LaserRay ray = new LaserRay(0, 0, 5, 5);
        assertEquals(LaserRay.CollideState.never, ray.getCollideStatus());

        ray.setCollideStatus(LaserRay.CollideState.absorb);
        assertEquals(LaserRay.CollideState.absorb, ray.getCollideStatus());
    }

}

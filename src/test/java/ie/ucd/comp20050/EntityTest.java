package ie.ucd.comp20050;

import ie.ucd.comp20050.entity.Atom;
import ie.ucd.comp20050.entity.LaserRay;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityTest {

    @Test
    void testAtom() {
        Atom atom = new Atom(5);
        assertEquals(5, atom.getHexagon());
        atom.setHexagon(10);
        assertEquals(10, atom.getHexagon());
    }

    @Test
    void testRay() {
        LaserRay ray = new LaserRay(0, 0, 5, 5);
        assertTrue(ray.getPosX() == 0 && ray.getPosY() == 0);
        ray.setPosX(5);
        ray.setPosY(-7);
        assertTrue(ray.getPosX() == 5 && ray.getPosY() == -7);
    }

}

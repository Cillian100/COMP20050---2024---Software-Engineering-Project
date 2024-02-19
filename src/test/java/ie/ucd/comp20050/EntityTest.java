package ie.ucd.comp20050;

import ie.ucd.comp20050.entity.Atom;
import ie.ucd.comp20050.entity.Ray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntityTest {

    @Test
    void testAtom() {
        Atom atom = new Atom(0, 0);
        assertTrue(atom.getPosX() == 0 && atom.getPosY() == 0);
        atom.setPosX(5);
        atom.setPosY(-7);
        assertTrue(atom.getPosX() == 5 && atom.getPosY() == -7);
    }

    @Test
    void testRay() {
        Ray ray = new Ray(0, 0);
        assertTrue(ray.getPosX() == 0 && ray.getPosY() == 0);
        ray.setPosX(5);
        ray.setPosY(-7);
        assertTrue(ray.getPosX() == 5 && ray.getPosY() == -7);
    }

}

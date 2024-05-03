package ie.ucd.comp20050.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomTest {

    @Test
    void testAtom() {
        Atom atom = new Atom(5);
        assertEquals(5, atom.getHexagon());
        atom.setHexagon(10);
        assertEquals(10, atom.getHexagon());
    }

}

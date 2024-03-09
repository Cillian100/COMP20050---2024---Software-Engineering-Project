package ie.ucd.comp20050;

import ie.ucd.comp20050.entity.Atom;

import java.util.ArrayList;
import java.util.Random;

/*
This class should be renamed.
 */
public class Logic {

    /**
     * Generates Atoms to be placed on the board
     * @param count integer, amount of Atoms to be generated
     * @param hexagons integer, count of hexagons on the board
     * @return ArrayList<Atom>, new list of generated Atoms
     */
    public static ArrayList<Atom> generateAtoms(int count, int hexagons) {
        ArrayList<Atom> list = new ArrayList<Atom>();
        Random random = new Random();
        for(int i = 0; i < count; i++) {
            int hex = random.nextInt(hexagons);
            list.add(new Atom(hex));
        }
        return list;
    }

}

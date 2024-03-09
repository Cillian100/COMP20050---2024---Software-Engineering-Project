package ie.ucd.comp20050.entity;

/*
This is the new Atom class, which can be used for abstraction.
Everywhere the Atom's "real" position (X, Y) is needed (e.g., drawing), position can be obtained from the hexagon
Detection should be abstracted to compound based on index; it is likely that this class will need to be
expanded to support good detections.
 */
/**
 * Atom entity.
 */
public class Atom {

    /**
     * Index of hexagon where Atom is located.
     */
    private int hexagon;

    /**
     * Constructor.
     * @param hexagonInput integer, index of Atom's hexagon
     */
    public Atom(int hexagonInput) {
        hexagon = hexagonInput;
    }

    /**
     * Retrieve the index of the Atom's hexagon
     * @return integer, hexagon index
     */
    public int getHexagon() {
        return hexagon;
    }

    /**
     * Set the index of the Atom's hexagon
     * @param value integer, new hexagon index
     */
    public void setHexagon(int value) {
        hexagon = value;
    }

}

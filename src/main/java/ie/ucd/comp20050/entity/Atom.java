package ie.ucd.comp20050.entity;

/**
 * Atom entity. Implements IEntity
 * @see ie.ucd.comp20050.entity.IEntity
 */
public class Atom implements IEntity {

    /**
     * X-position of the Atom
     */
    private int posX;

    /**
     * Y-position of the Atom
     */
    private int posY;

    /**
     * Constructor. Atoms must be initialised with an X and Y position.
     * @param inputX integer x-position
     * @param inputY integer y-position
     */
    public Atom(int inputX, int inputY) {
        posX = inputX;
        posY = inputY;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosX(int value) {
        posX = value;
    }

    @Override
    public void setPosY(int value) {
        posY = value;
    }

}

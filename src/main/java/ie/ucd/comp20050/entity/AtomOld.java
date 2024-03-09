package ie.ucd.comp20050.entity;

/**
 * Atom entity. Implements IEntity
 * @see ie.ucd.comp20050.entity.IEntity
 * @deprecated Please use {@link Atom}
 */
public class AtomOld implements IEntity {

    /**
     * X-position of the Atom
     */
    private int posX;

    /**
     * Y-position of the Atom
     */
    private int posY;

    /**
     * Width of the Atom
     */
    private int width;

    /**
     * Height of the Atom
     */
    private int height;

    /**
     * Constructor. Atoms must be initialised with an X and Y position, as well as a width and height.
     * @param inputX integer x-position
     * @param inputY integer y-position
     * @param inputWidth integer width
     * @param inputHeight integer height
     */
    public AtomOld(int inputX, int inputY, int inputWidth, int inputHeight) {
        posX = inputX;
        posY = inputY;
        width = inputWidth;
        height = inputHeight;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int value) {
        width = value;
    }

    public void setHeight(int value) {
        height = value;
    }

    public int getCircleWidth() {
        return width * 2;
    }

    public int getCircleHeight() {
        return height * 2;
    }

    public int getMiddleX() {
        return posX + width;
    }

    public int getMiddleY() {
        return posY + height;
    }

}

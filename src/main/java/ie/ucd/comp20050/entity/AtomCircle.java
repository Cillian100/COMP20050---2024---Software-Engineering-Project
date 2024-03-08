package ie.ucd.comp20050.entity;

/**
 * Atom Circle entity. Implements IEntity
 * @see ie.ucd.comp20050.entity.IEntity
 */
public class AtomCircle implements IEntity {

    /**
     * X-position of the Atom Circle
     */
    private int posX;

    /**
     * Y-position of the Atom Circle
     */
    private int posY;

    /**
     * Width of the Atom Circle
     */
    private int width;

    /**
     * Height of the Atom Circle
     */
    private int height;

    /**
     * Constructor. Atom Circles must be initialised with an X and Y position, as well as a width and height.
     * @param inputX integer x-position
     * @param inputY integer y-position
     * @param inputWidth integer width
     * @param inputHeight integer height
     */
    public AtomCircle(int inputX, int inputY, int inputWidth, int inputHeight) {
        posX = inputX;
        posY = inputY;
        width = inputWidth;
        height = inputHeight;
    }

    /**
     * Method to retrieve the X-position of an Atom Circle
     * @return integer, X-position of Atom Circle
     */
    @Override
    public int getPosX() {
        return posX;
    }

    /**
     * Method to retrieve the Y-position of an Atom Circle
     * @return integer, Y-position of Atom Circle
     */
    @Override
    public int getPosY() {
        return posY;
    }

    /**
     * Method to set the X-position of an Atom Circle
     * @param input integer, X-position of Atom Circle
     */
    @Override
    public void setPosX(int input) {
        posX = input;
    }

    /**
     * Method to set the Y-position of an Atom Circle
     * @param input integer, Y-position of Atom Circle
     */
    @Override
    public void setPosY(int input) {
        posY = input;
    }

    /**
     * Method to retrieve the width of an Atom Circle
     * @return integer, width of Atom Circle
     */
    public int getWidth() {
        return width;
    }

    /**
     * Method to retrieve the height of an Atom Circle
     * @return integer, height of Atom Circle
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method to set the width of an Atom Circle
     * @param input integer, new width of Atom Circle
     */
    public void setWidth(int input) {
        width = input;
    }

    /**
     * Method to set the height of an Atom Circle
     * @param input integer, new height of Atom Circle
     */
    public void setHeight(int input) {
        height = input;
    }

    /**
     * Method to retrieve the middle X-position of an Atom Circle
     * @return integer, middle X-position of Atom Circle
     */
    public int getMiddleX() {
        return posX + (width/2);
    }

    /**
     * Method to retrieve the middle Y-position of an Atom Circle
     * @return integer, middle Y-position of Atom Circle
     */
    public int getMiddleY() {
        return posY + (height/2);
    }

}

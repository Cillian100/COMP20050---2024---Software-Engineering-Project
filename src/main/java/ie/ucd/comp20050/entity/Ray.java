package ie.ucd.comp20050.entity;

/**
 * Ray entity. Implements IEntity
 * @see ie.ucd.comp20050.entity.IEntity
 */
public class Ray implements IEntity {

    /**
     * X-position of the Ray
     */
    private int posX;

    /**
     * Y-position of the Ray
     */
    private int posY;

    /**
     * Constructor. Rays must be initialised with an X and Y position.
     * @param inputX integer x-position
     * @param inputY integer y-position
     */
    public Ray(int inputX, int inputY) {
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

package ie.ucd.comp20050.entity;

/**
 * Laser Ray entity
 */
public class LaserRay {

    public enum CollideState {never, bounce, absorb};

    private CollideState collideStatus = CollideState.never;

    /**
     * X-position of entity
     */
    private double posX;

    /**
     * Y-position of entity
     */
    private double posY;

    /**
     * Width of entity, for drawing
     */
    private int width;

    /**
     * Height of entity, for drawing
     */
    private int height;

    private double angle;

    private double hold;

    private double cos;

    private double sin;

    /**
     * Constructor
     * @param inputX int, starting X position of the ray
     * @param inputY int, starting Y position of the ray
     * @param inputWidth int, width of the ray
     * @param inputHeight int, height of the ray
     */
    public LaserRay(int inputX, int inputY, int inputWidth, int inputHeight) {
        posX = inputX;
        posY = inputY;
        width = inputWidth;
        height = inputHeight;
    }

    /**
     * Method to get the current collide status
     * @return Ray's current collide status
     */
    public CollideState getCollideStatus() {
        return collideStatus;
    }

    /**
     * Method to set the current collide status
     * @param inputState new CollideState for ray
     */
    public void setCollideStatus(CollideState inputState) {
        collideStatus = inputState;
    }

    public void move() {
        posX = posX - (cos * 20);
        posY = posY + (sin * 20);
    }

    public void stop() {
        posX = 1000;
        posY = 1000;
    }

    public void set(int inputX, int inputY, double inputAngle) {
        posX = inputX;
        posY = inputY;
        angle = inputAngle;
        hold = Math.toRadians(angle);
        cos = Math.cos(hold);
        sin = Math.sin(hold);
    }

    public double getDirection() {
        return angle;
    }

    public void changeDirection(double inputAngle) {
        angle = inputAngle;
        hold = Math.toRadians(angle);
        cos = Math.cos(hold);
        sin = Math.sin(hold);
    }

    public void addBounce(double modifier) {
        changeDirection(angle + modifier);
    }

    /**
     * Method to get the X-position of the ray
     * @return double, X-position of ray
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Method to set the X-position of the ray
     * @param inputX double, new X-position of ray
     */
    public void setPosX(double inputX) {
        posX = inputX;
    }

    /**
     * Method to get the Y-position of the ray
     * @return double, Y-position of the ray
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Method to set the Y-position of the ray
     * @param inputY double, new Y-position of ray
     */
    public void setPosY(double inputY) {
        posY = inputY;
    }

    public int getRadius() {
        return width / 2;
    }

    public double getMidX() {
        return posX + width/2;
    }

    public double getMidY() {
        return posY + height/2;
    }

}
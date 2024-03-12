package ie.ucd.comp20050.entity;

// @TODO Java docs, merging with Lazer 2 and Ray
public class Lazer{
    double x;
    double y;
    int width;
    int height;
    double angleA;
    double hold;
    double cos;
    double sin;

    private collideState collideStatus = collideState.never;

    public enum collideState {never,bounce ,absorb};

    public collideState getCollideStatus() {return collideStatus;}
    public void setCollideStatus(collideState x) {collideStatus = x;}
    public Lazer(int apple, int banana, int cranberry, int durian) {
        x=(double)apple;
        y=(double)banana;
        width=cranberry;
        height=durian;
    }

    public void move(){
        x=x-(cos * (20));
        y=y+(sin * (20));
    }
    public void stop(){
        y=1000;
        x=1000;
    }
    public void set(int apple, int banana, double angle){
        x=apple;
        y=banana;
        angleA = angle;
        hold = Math.toRadians(angle);
        cos = Math.cos(hold);
        sin = Math.sin(hold);
    }

    public void changeDirection(double angle){
        angleA = angle;
        hold = Math.toRadians(angle);
        cos = Math.cos(hold);
        sin = Math.sin(hold);
    }

    public void addBounce(double bounce)
    {
       changeDirection(angleA + bounce);
    }

    public double getMidX(){
        return (this.x + this.width/2);
    }
    public double getMidY(){
        return (this.y + this.height/2);
    }
    public double getDirection(){
        return angleA;
    }

    //easier to work with
    public double getX() {return this.x;}
    public double getY() {return this.y;}
    //probably add a set position
    public void setX(double x) {this.x = x;}
    public void setY(double y) { this.y = y;}

    public int getRadius() {return width / 2;}

}

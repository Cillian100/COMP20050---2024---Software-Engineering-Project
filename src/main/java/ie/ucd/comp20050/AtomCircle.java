package ie.ucd.comp20050;

import java.awt.geom.Point2D;

public class AtomCircle {
    double x;
    double y;
    int width;
    int height;
    
    AtomCircle(int apple, int banana, int cranberry, int durian) {
        x=apple;
        y=banana;
        width=cranberry;
        height=durian;
    }  

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    } 
    public int getMiddleX(){
        return (int)x+(width/2);
    }
    public int getMiddleY(){
        return (int)y+(height/2);
    }
}

package ie.ucd.comp20050;
public class Atom{
    double x;
    double y;
    int width;
    int height;
    
    Atom(int apple, int banana, int cranberry, int durian) {
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
}
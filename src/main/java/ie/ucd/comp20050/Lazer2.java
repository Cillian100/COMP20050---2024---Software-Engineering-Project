package ie.ucd.comp20050;

public class Lazer2{
    int[] x = new int[100];
    int[] y = new int[100];
    int numberOfPoints=0;

    public void set(int a, int b){
        x[numberOfPoints]=a;
        y[numberOfPoints]=b;
        numberOfPoints++;
    }
    public int[] getX(){
        return x;
    }
    public int[] getY(){
        return y;
    }
    public int getNum(){
        return numberOfPoints;
    }
}

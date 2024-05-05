package ie.ucd.comp20050.component;

public class Hexagon {
    private double a=60;
    private double b = Math.toRadians(a);
    private double sinSixty=Math.sin(b);
    private double cosSixty=Math.cos(b);
    private int numberOfPoints=1;
    private double x2[] = new double[10];
    private double x[] = new double[10];
    private int xInt[] = new int[10];
    private double y2[] = new double[10];
    private double y[] = new double[10];
    private int yInt[] = new int[10];
    private double modifier;

    public Hexagon(double apple, double banana, double cranberry, int calibrate) {
        x2[0]=apple;
        y2[0]=banana;
        modifier=cranberry;

        for(int a=1;a<6;a++){
            b = Math.toRadians(calibrate);
            sinSixty=Math.sin(b);
            cosSixty=Math.cos(b);
            x2[a]=x2[a-1] + (sinSixty*100*modifier);
            y2[a]=y2[a-1] - (cosSixty*100*modifier);
            calibrate=calibrate+60;
            numberOfPoints++;
        }

        int point = calibrate / 60;
        for(int a=0;a<6;a++){
            point = point % 6;
            x[point]=x2[a];
            y[point]=y2[a];
            point=point+1;
        }
    }


    public double[] getX() {
        return x;
    }

    public int[] getXInteger() {
        xInt[0]=(int)x[0];
        xInt[1]=(int)x[1];
        xInt[2]=(int)x[2];
        xInt[3]=(int)x[3];
        xInt[4]=(int)x[4];
        xInt[5]=(int)x[5];
        return xInt;
    }

    public int[] getYInteger() {
        yInt[0]=(int)y[0];
        yInt[1]=(int)y[1];
        yInt[2]=(int)y[2];
        yInt[3]=(int)y[3];
        yInt[4]=(int)y[4];
        yInt[5]=(int)y[5];
        return yInt;
    }

    public double[] getY() {
        return y;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public double getMiddleX() {
        return (x[1] + x[4])/2;
    }

    public double getMiddleY() {
        return (y[1] + y[4])/2;
    }

}

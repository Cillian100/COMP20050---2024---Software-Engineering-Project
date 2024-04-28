package ie.ucd.comp20050;

public class Arrow{
    private int[] x = new int[2];
    private int[] y = new int[2];
    private int[] lineX = new int[2];
    private int[] lineY = new int[2];
    private int[] triangleX = new int[3];
    private int[] triangeY = new int[3];
    private double modifier=0;
    private double angle;
    private double cos;
    private double sin;
    private double hold;
    
    public Arrow(int apple, int banana, int cucumber, int durian, double mod){
        x[0]=apple;
        x[1]=cucumber;
        y[0]=banana;
        y[1]=durian;
        modifier=mod;
        calculateAngle();
        calculateLine();
    }

    public int[] getLineX() {
        return lineX;
    }

    public int[] getLineY() {
        return lineY;
    }

    public double getAngle() {
        return angle;
    }

    public void calculateAngle(){
        angle = Math.toDegrees(Math.atan2(x[1]-x[0], y[1]-y[0]));
        hold = Math.toRadians(angle);
        cos = Math.cos(hold);
        sin = Math.sin(hold);
    }

    public void calculateLine(){
        lineX[0]=(x[0]+x[1])/2;
        lineY[0]=(y[0]+y[1])/2;
        lineX[1]=lineX[0] + (int)(cos * (20*modifier));
        lineY[1]=lineY[0] - (int)(sin * (20*modifier));
    }
}

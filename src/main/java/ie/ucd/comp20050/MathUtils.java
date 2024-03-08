package ie.ucd.comp20050;

import java.lang.Math;

public class MathUtils {
    static double a=60;
    static double b = Math.toRadians(a);
    static double sinSixty=Math.sin(b);
    static double cosSixty=Math.cos(b);
    
    public int NumberOfHexagonsX(double modifier, int SCREEN_WIDTH){
        int x=0;
        int y=SCREEN_WIDTH;
        while(y>(modifier*sinSixty*100*4)){
            y=(int) (y-(modifier*sinSixty*100*2));
            x++;
        }
        return x;
    }

    public int NumberOfHexagonsY(double modifier, int SCREEN_HEIGHT){
        int x=0;
        int y=SCREEN_HEIGHT/2;
        while(y>(modifier*100*2)){
            y=(int)(y-(modifier*150));
            x++;
        }
        return x;
    }

    public double getModifier(int SCREEN_HEIGHT, int SCREEN_WIDTH){
        double returnValue=SCREEN_HEIGHT/18;
        returnValue=returnValue/100;
        return returnValue;
    }

    public int distanceBetweenTwoPoints(int X1, int Y1, int X2, int Y2){
        int x1=X1;
        int y1=Y1;
        int x2=X2;
        int y2=Y2;
        return (int)(Math.sqrt((y2-y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));
    }

    public int moduloRange(int dividend, int modulus, int range){
        int toss = dividend % modulus;
        if(toss==dividend){
            return toss;
        }else{
            return toss+range;    
        }
    }
}

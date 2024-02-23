package ie.ucd.comp20050;

import java.lang.Math;

public class QuickMaff{
    static double a=60;
    static double b = Math.toRadians(a);
    static double sinSixty=Math.sin(b);
    static double cosSixty=Math.cos(b);

    public int[] xHexagon(int constant, double modifier){
        int [] x = {
                constant,                                //1
                (int) (constant+(sinSixty*100*modifier)),          //2
                (int) (constant+(sinSixty*100*2*modifier)),        //3
                (int) (constant+(sinSixty*100*2*modifier)),        //4
                (int) (constant+(sinSixty*100*modifier)),          //5
                constant};                               //6
        return x;
    }

    public int xHexagonNext(int constant, double modifier, int position){
        int constantNext=constant;
        if(position==2){
            constantNext=(int) (constant+(sinSixty*100*modifier));
        }else if(position==3){
            constantNext=(int) (constant+(sinSixty*100*2*modifier));
        }else if(position==4){
            constantNext=(int) (constant+(sinSixty*100*2*modifier));
        }else if(position==5){
            constantNext=(int) (constant+(sinSixty*100*modifier));
        }
        return constantNext;
    }

    public int yHexagonNext(int constant, double modifier, int position){
        int constantNext=constant;
        if(position==2){
            constantNext=(int)(constant-(cosSixty*100*3*modifier));
        }else if(position==3){
            constantNext=constant;
        }else if(position==4){
            constantNext=(int) (constant+(cosSixty*100*2*modifier));
        }else if(position==5){
            constantNext=(int)(constant+(cosSixty*100*3*modifier));
        }
        return constantNext;
    }

    public int[] yHexagon(int constant, double modifier){
        int[] y = {
                (int) (constant-(cosSixty*100*modifier)),         //1
                (int) (constant-(cosSixty*100*2*modifier)),        //2
                (int) (constant-(cosSixty*100*modifier)),         //3
                (int) (constant+(cosSixty*100*modifier)),         //4
                (int) (constant+(cosSixty*100*2*modifier)),        //5
                (int) (constant+(cosSixty*100*modifier))};        //6

        return y;
    }

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
        while(y>(modifier*150*2)){
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
}

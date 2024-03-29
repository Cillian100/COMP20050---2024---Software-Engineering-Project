package ie.ucd.comp20050;

import java.lang.ref.Reference;
import java.util.TreeSet;

/**
 * Common math utilities.
 */
public final class MathUtils {

    /**
     * Calculates the distance between two points.
     *
     * @param x1 X-position of point 1
     * @param y1 Y-position of point 1
     * @param x2 X-position of point 2
     * @param y2 Y-position of point 2
     * @return integer, rough distance between the points
     */
    public static int pointsDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.hypot(x1 - x2, y1 - y2);
    }

    /**
     * Calculates the window modifier, which adjusts element sizing for screen resolution.
     *
     * @param height integer, window height size
     * @return double, modifier for window elements
     * @implNote Only creates modifier for a 'square' window.
     */
    public static double calculateWindowModifier(int height) {
        return ((double) height / 18) / 100;
    }

    /*
    Steven: I don't understand what the use for this method is.
    Modulo operator returns original input if modulo is higher than input. Eg., 21 % 100 = 21.
    Only difference this does from modulo is that, if mod is not equal to or higher than div, it adds range.
     */

    /**
     * Unknown function.
     *
     * @param dividend integer, number to perform modulus on
     * @param modulus  integer, modulus number
     * @param range    integer, unknown use
     * @return mod result + range if dividend < modulus; else, dividend.
     */
    public static int calculateModuloRange(int dividend, int modulus, int range) {
        return modulus >= dividend ? dividend : (dividend % modulus) + range;
    }

    public static double convertToNormal(double angle)
    {
       if (angle <= 360 && angle >= 0) return angle;
       angle %= 360;
       if (angle > -1 && angle < 0 ) return 0;
       else if (angle <0 ) angle = 360 + angle;
       return angle;
    }



    public static boolean twoCircleColl(double x1,double y1,double x2,double y2,double r1,double r2)
    {
        double g = squ(x1 - x2)  + squ(y1 - y2);
        double r = squ(r1 + r2 );
        return r >= g;

    }

    public static double closestValue(double x,TreeSet<Double> set)
    {
        Double clx,clx2;
        clx = set.floor(x);
        if (clx == null) clx =Double.MAX_VALUE;
        clx2 = set.ceiling(x);
        if (clx2 == null) clx2 = Double.MAX_VALUE;
        return Math.abs(clx - x ) < Math.abs(clx2-x)? clx:clx2;

    }



    public static double squ(double x)
    {
        return x * x;
    }

}

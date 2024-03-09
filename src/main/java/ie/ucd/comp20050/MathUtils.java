package ie.ucd.comp20050;

/**
 * Common math utilities.
 */
public final class MathUtils {

    /**
     * Calculates the distance between two points.
     * @param x1 X-position of point 1
     * @param y1 Y-position of point 1
     * @param x2 X-position of point 2
     * @param y2 Y-position of point 2
     * @return integer, rough distance between the points
     */
    public static int pointsDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.hypot(x1-x2, y1-y2);
    }

    /**
     * Calculates the window modifier, which adjusts element sizing for screen resolution.
     * @implNote Only creates modifier for a 'square' window.
     * @param height integer, window height size
     * @return double, modifier for window elements
     */
    public static double calculateWindowModifier(int height) {
        return ((double)height / 18) / 100;
    }

    /*
    Steven: I don't understand what the use for this method is.
    Modulo operator returns original input if modulo is higher than input. Eg., 21 % 100 = 21.
    Only difference this does from modulo is that, if mod is not equal to or higher than div, it adds range.
     */
    /**
     * Unknown function.
     * @param dividend integer, number to perform modulus on
     * @param modulus integer, modulus number
     * @param range integer, unknown use
     * @return mod result + range if dividend < modulus; else, dividend.
     */
    public static int calculateModuloRange(int dividend, int modulus, int range) {
        return modulus >= dividend ? dividend : (dividend % modulus) + range;
    }

}

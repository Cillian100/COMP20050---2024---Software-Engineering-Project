package ie.ucd.comp20050;

/**
 * Common utilities
 */
public final class Common {

    /**
     * Calculates the distance between two points
     * @param x1 X-position of point 1
     * @param y1 Y-position of point 1
     * @param x2 X-position of point 2
     * @param y2 Y-position of point 2
     * @return integer, rough distance between the points
     */
    public static int pointsDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.hypot(x1-x2, y1-y2);
    }

}

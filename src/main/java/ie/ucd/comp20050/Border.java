package ie.ucd.comp20050;

public class Border { // @TODO Refactor using streamlined data structure

    /**
     * Array of X coordinates for all points. Identical index of 'y' forms border point.
     */
    private int[] x = new int[100];

    /**
     * Array of Y coordinates for all points. Identical index of 'x' forms border point.
     */
    private int[] y = new int[100];

    /**
     * Amount of total points which the border contains.
     */
    private int countPoints = 0;

    /**
     * Constructs game border; collects two points from each 'border hexagon', and total amount of border points
     * Game is organised into 60 independent hexagons, with the board representing a large hexagon when combined
     * There are 24 'border hexagons', counting from hexagon 37 to hexagon 60
     * Border combines sides of each border hexagon which has no neighbour hexagon
     *
     * @param hexagons Hexagon2[], array of board's hexagons
     */
    public Border(Hexagon2[] hexagons) {
        for(int i = 0, position = 37; i < 6; i ++, position--, countPoints--) { // Loops board hexagon's 6 sides
            for(int j = 0; j < 5; j++, position++) { // Loops hexagons per side, for which there are 5
                if(position == 61) position = 37; // Wrap around

                // Collects 'top middle' point of border hexagon (facing out)
                x[countPoints] = (int) hexagons[position].getX()[i];
                y[countPoints] = (int) hexagons[position].getY()[i];
                countPoints++;

                // Collects 'top right' point of border hexagon (facing out)
                x[countPoints] = (int) hexagons[position].getX()[(1+i) % 6];
                y[countPoints] = (int) hexagons[position].getY()[(1+i) % 6];
                countPoints++;
            }
        }
    }

    /**
     * Method to obtain X coordinates for all points
     * @return array of X coordinates for points in the border
     */
    public int[] getX() {
        return x;
    }

    /**
     * Method to obtain Y coordinates of all points
     * @return array of Y coordinates for points in the border
     */
    public int[] getY() {
        return y;
    }

    /**
     * Method to obtain the total amount of points the border contains
     * @return total count of points border is composed of
     */
    public int getCountPoints() {
        return countPoints;
    }

    public Boolean passedBorder() { // @TODO Implement detection
        return false;
    }
}

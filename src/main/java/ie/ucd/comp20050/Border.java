package ie.ucd.comp20050;

public class Border {
    int[] x;
    int[] y;
    int numberOfPoints;

    Border(int[] xray, int[] yellow, int number) {
        x = xray;
        y = yellow;
        numberOfPoints = number;
    }

    public Boolean passedBorder() {
        return false;
    }

}

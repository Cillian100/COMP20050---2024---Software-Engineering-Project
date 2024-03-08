package ie.ucd.comp20050;

public class Border {
    private int[] x;
    private int[] y;
    private int countPoints;

    public Border(int[] xray, int[] yellow, int number) {
        x = xray;
        y = yellow;
        countPoints = number;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public int getCountPoints() {
        return countPoints;
    }

    public Boolean passedBorder() { // @TODO Implement detection
        return false;
    }

}

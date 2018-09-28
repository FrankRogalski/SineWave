package sample;

class Point {
    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    void setX(final double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }
}

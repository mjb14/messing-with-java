/*************************************************************************
 * Name: Mike Brennan
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // Compare points by the slopes they make with the invoking point (x0, y0). Formally, the point (x1, y1) is less than the point (x2, y2)
    // if and only if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
    // Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    // compare points according to their x-coordinate
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q1, Point q2) {

            double slopeToQ1 = slopeTo(q1);
            double slopeToQ2 = slopeTo(q2);

            //System.out.println("Slope order: " + slopeToQ1 + " -- " + slopeToQ2);

            if (slopeToQ1 < slopeToQ2) return -1;
            if (slopeToQ1 > slopeToQ2) return +1;
            return 0;
        }
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // method should return the slope between the invoking point (x0, y0)
        // and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).
        // Treat the slope of a horizontal line segment as positive zero;
        // treat the slope of a vertical line segment as positive infinity;
        // treat the slope of a degenerate line segment (between a point and itself) as negative infinity.

        // Case: Same point (Degenerate line segment)
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;

        // Case: Same x-axis
        if (this.x == that.x) return Double.POSITIVE_INFINITY;

        // Case: Same y-axis
        if (this.y == that.y) return 0;

        double numerator = that.y - this.y;
        double denominator = that.x - this.x;
        double result = numerator / denominator;
        return result;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        // compare points by their y-coordinates, breaking ties by their x-coordinates.
        // Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
        // if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
        if( this.y == that.y && this.x == that.x) return 0;
        if (this.y < that.y) return -1;
        if (this.y == that.y && this.x < that.x) return -1;
        return +1;
    }

    // ---------------------------- provided code --------------------------

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }


    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {

    }
}
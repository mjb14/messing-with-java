import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point[] points = new Point[N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points[index++] = p;
            // System.out.println(p);
        }

        // loop over our points
        Point p, q, s, r;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        p = points[i];
                        q = points[j];
                        r = points[k];
                        s = points[m];

                        if (doLieOnSameLine(p, q, r, s)) {
                            //System.out.println("On same line!");
                        } else {

                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);
    }

    private static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0; j--)
                if (a[j].compareTo(a[j - 1]) < 0)
                    excha(a, j, j - 1);
                else break;

    }

    private static void excha(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean doLieOnSameLine(Point p, Point q, Point r, Point s) {

        Point[] points = new Point[4];
        points[0] = p;
        points[1] = q;
        points[2] = r;
        points[3] = s;

        // find first non -Infinity and compare to last item, if equal, all on same line
        Arrays.sort(points, p.SLOPE_ORDER);

        // Case: all same point
        if (points[0] == points[points.length - 1]) return true;

        int firstNonNegativeInfinity = 0;
        for (int i = 0; i < points.length; i++) {
            if (p.slopeTo(points[i]) == Double.NEGATIVE_INFINITY) continue;
            firstNonNegativeInfinity = i;
            break;
        }

        if (p.slopeTo(points[firstNonNegativeInfinity]) == p.slopeTo(points[points.length - 1])) {
            // draw the line
            Arrays.sort(points);
            int pentultimate = points.length - 1;
            for (int i = 0; i < points.length; i++) {
                System.out.print(points[i]);
                if (i < pentultimate) System.out.print(" -> ");
                else System.out.println();
            }
            points[0].drawTo(points[points.length - 1]);
            return true;
        } else {
            //System.out.println("Different slopes: " + Points[firstNonNegativeInfinity] + " : " + Points[Points.length-1] );
        }

        return false;

    }
}

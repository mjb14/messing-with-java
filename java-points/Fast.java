import java.util.ArrayList;
import java.util.Arrays;


public class Fast {

    private static class Line {
        private ArrayList<Point> points;

        public Line() {
            this.points = new ArrayList<Point>();
        }

        public void addPoint(Point p) {
            this.points.add(p);
        }

        public Double getSlope() {
            Point[] arrayOfPoints = this.getPoints();
            Arrays.sort(arrayOfPoints);
            return arrayOfPoints[0].slopeTo(arrayOfPoints[arrayOfPoints.length - 1]);
        }

        public Point getFirst() {
            Point[] points = this.getPoints();
            Arrays.sort(points);
            return points[0];
        }

        public Point getLast() {
            Point[] points = this.getPoints();
            Arrays.sort(points);
            return points[points.length - 1];
        }

        public void printLine() {
            Object[] points = this.getPoints();
            Arrays.sort(points);

            Point start = (Point) points[0];
            Point end = (Point) points[points.length - 1];
            start.drawTo(end);

            Point p;
            for (int i = 0; i < points.length; i++) {
                p = (Point) points[i];
                System.out.print(p);
                if (i < points.length - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }

        public Point[] getPoints() {
            Object[] arrayOfObjects = this.points.toArray();
            Point[] arrayOfPoints = new Point[arrayOfObjects.length];
            for (int i = 0; i < arrayOfObjects.length; i++) {
                arrayOfPoints[i] = (Point) arrayOfObjects[i];
            }
            return arrayOfPoints;
        }

        public Integer getSize() {
            return this.points.size();
        }
    }

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
            points[index] = p;
            index++;
        }

        doFormLine(points);

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

    private static boolean doFormLine(Point[] points) {
        Point p;
        int numSame = 1;
        Point[] pCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pCopy[i] = points[i];
        }

        Line line = new Line();
        ArrayList<Line> lines = new ArrayList<Line>();

        for (int i = 0; i < points.length; i++) {
            //for(int i = 0; i< 2; i++){

            p = points[i];
            Arrays.sort(pCopy, p.SLOPE_ORDER);


            // Handle end case in event last x were all identical slope
            if (numSame >= 3) {
                //System.out.println("Had a valid line from " + p + " to " + pCopy[pCopy.length]);
                //line.printLine();
                lines.add(line);
            }

            numSame = 1;
            line = new Line();
            line.addPoint(p);
            if (points.length > 1) {
                line.addPoint(pCopy[1]);
            }
            //System.out.println(p + ": ==================");

            // j=0: this is the point in question, since slopeTo itself is -Infinity

            for (int j = 1; j < pCopy.length - 1; j++) {
                //System.out.println(p.slopeTo(pCopy[j])+ ": " + pCopy[j]);

                if (p.slopeTo(pCopy[j]) == p.slopeTo(pCopy[j + 1])) {
                    line.addPoint(pCopy[j + 1]);
                    numSame++;
                } else {
                    // if numSame was 3+, then we had a valid segment
                    if (numSame >= 3) {
                        //System.out.println("Had a valid line from " + p + " to " + pCopy[j]);
                        //line.printLine();
                        lines.add(line);
                    }

                    // reset our counter in case other lines form from the origin point
                    numSame = 1;
                    line = new Line();
                    line.addPoint(p);
                    line.addPoint(pCopy[j + 1]);
                }
            }
        }

        // We have array of all our lines, need to pull out distinct ones
        ArrayList<Line> distinctLines = new ArrayList<Line>();

        int counter = 0;
        boolean isDistinct;
        for (Line l : lines) {

            //l.printLine();

            if (counter == 0) {
                distinctLines.add(l);
            } else {
                // loop over all distinct lines and be sure that this line isn't already added
                isDistinct = true;


                for (int i = 0; i < distinctLines.size(); i++) {
                    //System.out.println("First: " + l.getFirst() + " :: " + distinctLines.get(i).getFirst());
                    //System.out.println("Last: " + l.getLast() + " :: " + distinctLines.get(i).getLast());
                    if (l.getFirst() != distinctLines.get(i).getFirst() || l.getLast() != distinctLines.get(i).getLast()) {
                    } else {
                        isDistinct = false;
                        break;
                    }
                }

                if (isDistinct) {
                    distinctLines.add(l);
                }

            }
            counter++;
        }

        for (Line l : distinctLines) {
            l.printLine();
        }

        return true;
    }

}

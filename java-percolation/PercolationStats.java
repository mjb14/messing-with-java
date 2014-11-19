/**
 * Created by mjb14 on 6/20/2014.
 */
public class PercolationStats {

    private double confidenceLo;
    private double confidenceHi;
    private double[] countOfOpen;

    public PercolationStats(int N, int T) {
        // perform T independent computational experiments on an N-by-N grid
        if (N <= 0) {
            throw new IllegalArgumentException("N was <= 0");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T was <= 0");

        }

        countOfOpen = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            openSitesUntilPercolates(p, N);
            // once percolates, need to go through all our squares and count the open ones

            int numOpen = 0;
            for (int j = 1; j <= N; j++) {
                for (int x = 1; x <= N; x++) {
                    if (p.isOpen(j, x)) {
                        numOpen++;
                    }
                }
            }

            countOfOpen[i] = (double) numOpen / (N * N);
        }

        double appStddev = stddev();
        double appMean = mean();
        double stdevMean = appStddev / Math.sqrt(N);
        confidenceLo = appMean - (stdevMean * 1.962);
        confidenceHi = appMean + (stdevMean * 1.962);

        System.out.println("mean                    = " + appMean);
        System.out.println("stddev                  = " + appStddev);
        System.out.println("95% confidence interval = " + confidenceLo + ", " + confidenceHi);
    }

    private static int getRandomNumber(int N) {
        return StdRandom.uniform(N) + 1;
    }

    private static boolean openRandomSite(Percolation p, int N) {

        int i = getRandomNumber(N);
        int j = getRandomNumber(N);

        if (!p.isOpen(i, j)) {
            p.open(i, j);
            return true;
        } else
            return openRandomSite(p, N);
    }

    private static void openSitesUntilPercolates(Percolation p, int N) {
        while (!p.percolates()) {
            openRandomSite(p, N);
            //p.printGrid();
        }
    }


    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(countOfOpen);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(countOfOpen);
    }

    public double confidenceLo() {
        // returns lower bound of the 95% confidence interval
        return confidenceLo;
    }

    public double confidenceHi() {
        // returns upper bound of the 95% confidence interval
        return confidenceHi;
    }

    public static void main(String[] args) {
        // test client, described below
        if (args.length != 2) {
            System.out.println("You must pass 2 args, N and T to this application.");
        } else {
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            new PercolationStats(N, T);
        }
    }


}


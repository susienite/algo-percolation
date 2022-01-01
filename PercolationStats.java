/* *****************************************************************************
 *  Name:              Susan Tan
 *  Coursera User ID:  123456
 *  Last modified:     06/25/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private final double[] fractions;
    private final int trials;
    private double sum;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        fractions = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            double fraction = (double) p.numberOfOpenSites() / (n * n);
            sum += fraction;
            fractions[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return (sum / trials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double xbar = mean();
        double sumdiff = 0;
        for (int i = 0; i < trials; i++) {
            double diff = (fractions[i] - xbar);
            sumdiff += (diff * diff);
        }
        return (sumdiff / (trials - 1));
    }

    // private int 1.96s/sqrt(T)
    private double diff() {
        double s = Math.sqrt(stddev());
        return (1.96 * s) / Math.sqrt(trials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - diff());
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + diff());
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);        // n-by-n percolation system
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() +
                                   ", " + ps.confidenceHi() + "]");
    }

}

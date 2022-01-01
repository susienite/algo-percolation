/* *****************************************************************************
 *  Name:              Susan Tan
 *  Coursera User ID:  123456
 *  Last modified:     06/23/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // fields
    private boolean[][] site;
    private final int virtualFirstRow;
    private final int virtualLastRow;
    private int openSites;
    private final int len;
    private WeightedQuickUnionUF tree;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        site = new boolean[n][n];  // create new boolean array
        tree = new WeightedQuickUnionUF(n * n + 2);  // create new WQU
        virtualFirstRow = n * n;
        virtualLastRow = n * n + 1;
        len = n;

    }

    // Corner cases.  By convention, the row and column indices are integers
    // between 1 and n, where (1, 1) is the upper-left site: Throw an IllegalArgumentException
    // if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
    private void validate(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {

            // shift (row, col) to Java's indexing of (0,0)
            int srow = row - 1;
            int scol = col - 1;
            site[srow][scol] = true;
            int site1D = to1D(srow, scol);
            openSites += 1;

            // connect first row to virtual first row
            if (row == 1) tree.union(site1D, virtualFirstRow);
            if (row == len) tree.union(site1D, virtualLastRow);

            // union with neighbors
            if ((srow - 1 >= 0) && site[srow - 1][scol])
                tree.union(to1D(srow - 1, scol), site1D);
            if ((srow + 1 < len) && site[srow + 1][scol])
                tree.union(to1D(srow + 1, scol), site1D);
            if ((scol - 1 >= 0) && site[srow][scol - 1])
                tree.union(to1D(srow, scol - 1), site1D);
            if ((scol + 1 < len) && site[srow][scol + 1])
                tree.union(to1D(srow, scol + 1), site1D);

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return (site[row - 1][col - 1]);
    }


    // private method to convert (row,col) to a number label
    private int to1D(int row, int col) {
        return (row * len + col);
    }

    // is the site (row, col) full? is the site connected to the first row?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int site1D = to1D(row - 1, col - 1);
       /* if (row == len && tree.find(site1D) == tree.find(virtualFirstRow)) {

            tree.union(site1D, virtualLastRow);
        } */
        return (tree.find(site1D) == tree.find(virtualFirstRow));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return (openSites);
    }

    // does the system percolate? is the virtual first row connected to virtual
    // last row?
    public boolean percolates() {
        return (tree.find(virtualFirstRow) == tree.find(virtualLastRow));
    }

    public static void main(String[] args) {

    }
}


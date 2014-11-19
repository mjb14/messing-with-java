public class Percolation {
    private int[] sites;
    private int gridSideLength;
    private WeightedQuickUnionUF withVirtual;
    private WeightedQuickUnionUF sansVirtual;
    
    public Percolation(int N) {
        // N must be 1 or greater
        if (N <= 0) throw new IllegalArgumentException("N cannot be less than 1");

        // Track size of grid
        this.gridSideLength = N;

        // Init our sites array
        this.sites = new int[N * N + 2];

        // 3:  0, 1, 2, 3, 4
        // Populate all sites as closed (0)
        for (int x = 0; x < this.sites.length; x++) {
            this.sites[N] = 0;
        }
       
        this.sites[0] = 1;
        this.sites[this.gridSideLength * this.gridSideLength + 1] = 1;

        // Set up our weighted union quick finds
        this.withVirtual = new WeightedQuickUnionUF(N * N + 2);
        this.sansVirtual = new WeightedQuickUnionUF(N * N + 1);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i < 1 || i > this.gridSideLength) throw new IndexOutOfBoundsException();
        if (j < 1 || j > this.gridSideLength) throw new IndexOutOfBoundsException();

        if (!isOpen(i, j)) {
            this.openSite(i, j);

            // if i = 1, connect to top virtual node
            if (i == 1) {
                this.withVirtual.union(this.convert2dTo1d(i, j), 0);
                //this.withVirtual.union(this.convert2dTo1d(i, j), this.virtualTopNode);
                //this.sansVirtual.union(this.convert2dTo1d(i, j), this.virtualTopNode);
                this.sansVirtual.union(this.convert2dTo1d(i, j), 0);
            }

            // if i = gridSideLength, connect to bottom virtual node
            if (i == this.gridSideLength) {
                //this.withVirtual.union(this.convert2dTo1d(i, j), this.virtualBottomNode);
                this.withVirtual.union(this.convert2dTo1d(i, j), this.gridSideLength * this.gridSideLength + 1);
            }

            // if i > 1, connect to node above
            if (i > 1) {
                if (this.isOpen(i - 1, j)) {
                    this.withVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i - 1, j));
                    this.sansVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i - 1, j));
                }
            }

            // if i < gridSideLength, connect to node below
            if (i < this.gridSideLength) {
                if (this.isOpen(i + 1, j)) {
                    this.withVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i + 1, j));
                    this.sansVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i + 1, j));
                }
            }

            // if j > 1, connect to left node
            if (j > 1) {
                if (this.isOpen(i, j - 1)) {
                    this.withVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i, j - 1));
                    this.sansVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i, j - 1));
                }
            }

            // if j < gridSideLength, connect to right node
            if (j < this.gridSideLength) {
                if (this.isOpen(i, j + 1)) {
                    this.withVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i, j + 1));
                    this.sansVirtual.union(this.convert2dTo1d(i, j), this.convert2dTo1d(i, j + 1));
                }
            }
        }
    }

    public boolean isOpen(int i, int j) {
        if (i < 1 || i > this.gridSideLength) throw new IndexOutOfBoundsException();
        if (j < 1 || j > this.gridSideLength) throw new IndexOutOfBoundsException();

        return this.sites[this.convert2dTo1d(i, j)] == 1;
    }

    public boolean isFull(int i, int j) {
        if (i < 1 || i > this.gridSideLength) throw new IndexOutOfBoundsException();
        if (j < 1 || j > this.gridSideLength) throw new IndexOutOfBoundsException();
        return sansVirtual.connected(0, this.convert2dTo1d(i, j));
        //return sansVirtual.connected(this.virtualTopNode, this.convert2dTo1d(i, j));
    }

    public boolean percolates() {
        return withVirtual.connected(0, this.gridSideLength * this.gridSideLength + 1);
        //return withVirtual.connected(this.virtualTopNode, this.virtualBottomNode);
    }

    /* --------------------------------
        Private Methods
     -------------------------------- */
    private void openSite(int i, int j) {
        this.sites[this.convert2dTo1d(i, j)] = 1;
    }

    // Take i,j coordinates and get the single array position
    private int convert2dTo1d(int i, int j) {
        return (this.gridSideLength * (i - 1)) + j;
    }

}

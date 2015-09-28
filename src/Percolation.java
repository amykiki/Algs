
public class Percolation {
    private int[][] data;
    private int pNUM;
    private WeightedQuickUnionUF UF;
    private int top;
    private int bottom;
    
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid length " + N
                                                + " is lower than 0");
        }
        pNUM = N+1;
        UF = new WeightedQuickUnionUF(pNUM*pNUM);
        data = new int[pNUM][pNUM];
        for (int i = 1; i < pNUM; i++) {
            for (int j = 1; j < pNUM; j++) {
                data[i][j] = 0;
            }
        }
        top = pNUM;
        bottom = pNUM*pNUM;
        for (int j = 1; j < pNUM; j++) {
            UF.union(top, getIndex(1, j));
        }
    }
    private int getIndex(int i, int j) {
        int index = i*pNUM + j;
        return index;
    }
    
    /**
     * Merge the given point with the four direction point (i - 1, j),
     * (i + 1, j), (i, j -1), (i, j + 1)
     * @param i the horizontal axis of the point
     * @param j the vertical axis of the first point
     */
    public void open(int i, int j) {
        if (isOpen(i, j)) return;
        data[i][j] = i * pNUM + j;
        if (i == (pNUM - 1)) {
            data[i][j] = bottom;
        }
        int[] yAxis = new int[] {j-1, j+1};
        int[] xAxis = new int[] {i-1, i+1};
        
        for (int y: yAxis) {
            if (!validPoint(y)) continue;
            if (!isOpen(i, y)) continue;
            union(i, j, i, y);
        }
        
        for (int x: xAxis) {
            if (!validPoint(x)) continue;
            if (!isOpen(x, j)) continue;
            union(i, j, x, j);
        }
        int root = UF.find(getIndex(i, j));
        int[] coords = getCoords(root);
        int pX = coords[0];
        int pY = coords[1];
        
        setSize(pX, pY, i, j);
        
    }
    
    private void union(int i, int j, int p, int q) {
        int root1 = UF.find(getIndex(i, j));
        int[] coords1 = getCoords(root1);
        int r1X = coords1[0];
        int r1Y = coords1[1];
        
        int root2 = UF.find(getIndex(p, q));
        int[] coords2 = getCoords(root2);
        int r2X = coords2[0];
        int r2Y = coords2[1];
        
        setSize(r1X, r1Y, r2X, r2Y);
        UF.union(root1, root2);
    }
    
    private void setSize(int i, int j, int p, int q) {
        if (data[i][j] < data[p][q]) {
            data[i][j] = data[p][q];
        }
        else {
            data[p][q] = data[i][j];
        }
    }
    private int[] getCoords(int p) {
        int i = p / pNUM;
        int j = p % pNUM;
        return new int[] {i, j};
    }
    private boolean validPoint(int p) {
        return (p > 0 && p < pNUM);
    }
    
    private void validate(int p, int q) {
        if (!validPoint(p) || !validPoint(q)) {
            throw new IndexOutOfBoundsException("index [" + p + ", " + q + "] "
                    + "is not between 1 and " + (pNUM - 1));
        }
    }
    
    /**
     * @param i the horizontal axis of the point
     * @param j the vertical axis of the point
     * @return true if the point is full, false if not
     */
    public boolean isFull(int i, int j) {
        if (!isOpen(i, j)) return false;
        return (UF.connected(getIndex(i, j), top));
    }
    
    
    /**
     * check whether the point is open
     * @param i the horizontal axis of the point
     * @param j the vertical axis of the point
     * @return true if the point is open, false if not
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return (data[i][j] > 0);
    }
    
    /**
     * @return true if the <i>N-by-N</i> grid is percolated, false if not
     *          check the last row point is full or not
     */
    public boolean percolates() {
        int root = UF.find(top);
        int[] coords = getCoords(root);
        int pX = coords[0];
        int pY = coords[1];
        return (data[pX][pY] == bottom);
    }
    
}

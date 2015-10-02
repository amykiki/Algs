import java.awt.Color;
import java.util.Arrays;
import edu.princeton.cs.algs4.*;
public class PercolationForTest {
    private int[][] data;
    private int[][] size;
    private int NUM;
    
    public PercolationForTest(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid length " + N + " is lower than 0");
        }
        NUM = N+1;
        data = new int[NUM][NUM];
        size = new int[NUM][NUM];
        for (int i = 1; i < NUM; i++) {
            for (int j = 1; j < NUM; j++) {
                data[i][j] = 0;
                size[i][j] = 1;
            }
        }
    }
    private int[] getCoord(int value) {
        int i = value / NUM;
        int j = value % NUM;
        return new int[] {i, j};
    }
    
    /**
     * Merge the given point with the four direction point (i - 1, j), 
     * (i + 1, j), (i, j -1), (i, j + 1)
     * @param i the horizontal axis of the point
     * @param j the vertical axis of the first point
     */
    public void open(int i, int j) {
        if (isOpen(i, j)) return;
        data[i][j] = i * NUM + j;
        int[] yAxis = new int[] {j-1, j+1};
        int[] xAxis = new int[] {i-1, i+1};
        
        for (int y: yAxis) {
            if (!validPoint(y)) continue;
            union(i, j, i, y);
        }
        
        for (int x: xAxis) {
            if (!validPoint(x)) continue;
            union(i, j, x, j);
        }
    }
    
    private boolean validPoint(int p) {
        return (p > 0 && p < NUM);
    }
    
    private void validate(int p, int q) {
        if (!validPoint(p) || !validPoint(q)) {
            throw new IndexOutOfBoundsException("index [" + p + ", " + q + "] "
                    + "is not between 1 and " + (NUM - 1));
        }
    }
    /**
     * @param i the horizontal axis of the point
     * @param j the vertical axis of the first point
     * @return the horizontal and vertical axis of the given point' root
     */
    private int[] find(int i, int j) {
        int resultX = i;
        int resultY = j;
        int value = data[resultX][resultY];
        int[] coords = getCoord(value);
        int iRoot = coords[0];
        int jRoot = coords[1];
        while ((resultX != iRoot) || (resultY != jRoot)) {
            data[resultX][resultY] = data[iRoot][jRoot];
            resultX = iRoot;
            resultY = jRoot;
            value = data[resultX][resultY];
            coords = getCoord(value);
            iRoot = coords[0];
            jRoot = coords[1];
        }
        return new int[] {resultX, resultY};
    }
    
    /**
     * @param i the horizontal axis of the point
     * @param j the vertical axis of the point
     * @return true if the point is full, false if not
     */
    public boolean isFull(int i, int j) {
        if (!isOpen(i, j)) return false;
        int[] coords = find(i, j);
        return (coords[0] == 1);
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
     * Merge two points together
     * @param i the horizontal axis of first point
     * @param j the vertical axis of the first point
     * @param iNext the horizontal axis of second point
     * @param jNext the vertical axis of the second point
     */
    private void union(int i, int j, int iNext, int jNext) {
        
        if (!isOpen(i, j) || !isOpen(iNext, jNext)) return;
        
        int[] coords1 = find(i, j);
        int[] coords2 = find(iNext, jNext);
        
        int xRoot1 = coords1[0];
        int yRoot1 = coords1[1];
        int sRoot1 = size[xRoot1][yRoot1];
        
        int xRoot2 = coords2[0];
        int yRoot2 = coords2[1];
        int sRoot2 = size[xRoot2][yRoot2];
        
        if (xRoot1 == xRoot2 && yRoot1 == yRoot2) return;
        
        if (isFull(xRoot1, yRoot1) && isFull(xRoot2, yRoot2)
            || !isFull(xRoot1, yRoot1) && !isFull(xRoot2, yRoot2)) {
            if (sRoot1 >= sRoot2) {
                data[xRoot2][yRoot2] = data[xRoot1][yRoot1];
                size[xRoot1][yRoot1] += sRoot2;
            }
            else {
                data[xRoot1][yRoot1] = data[xRoot2][yRoot2];
                size[xRoot2][yRoot2] += sRoot1;
            }
        }
        else if (isFull(xRoot1, yRoot1) && !isFull(xRoot2, yRoot2)) {
            data[xRoot2][yRoot2] = data[xRoot1][yRoot1];
            size[xRoot1][yRoot1] += sRoot2;
        }
        else if (!isFull(xRoot1, yRoot1) && isFull(xRoot2, yRoot2)) {
            data[xRoot1][yRoot1] = data[xRoot2][yRoot2];
            size[xRoot2][yRoot2] += sRoot1;
        }
        
    }
    
    /**
     * @return true if the <i>N-by-N</i> grid is percolated, false if not
     *          check the last row point is full or not
     */
    public boolean percolates() {
        int i = NUM - 1;
        boolean result = false;
        
        for (int j = 1; j < NUM; j++) {
            if (isFull(i, j)) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public double getThreshold() {
        double sum = (NUM - 1) * (NUM - 1);
        int open = 0;
        for (int i = 1; i < NUM; i++) {
            for (int j = 1; j < NUM; j++) {
                if (isOpen(i, j)) open++;
            }
        }
        StdOut.println("Open sites are: " + open);
        double threshold = open/sum;
        return threshold;
    }
    public void printGrid() {
        for (int i = 1; i < NUM; i++) {
            StdOut.println(Arrays.toString(data[i]));
        }
    }
    public void printGridPic() {
        StdDraw.setCanvasSize(1024, 1024);
//        StdDraw.setXscale(0, 100);
//        StdDraw.setYscale(0, 100);
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 1000);
        StdDraw.setPenRadius(.003);
        int xInit = 5;
        int yInit = 995;
//        int yInit = 95;
        int radius = 5;
        for (int i = 1; i < NUM; i++) {
            xInit = 5;
            for (int j = 1; j < NUM; j++) {
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.square(xInit, yInit, radius);
                if (!isOpen(i, j)) {
                    StdDraw.setPenColor(Color.BLACK);
                }
                else {
                    if (!isFull(i, j)) {
                        StdDraw.setPenColor(Color.RED);
                    }
                    else {
                        StdDraw.setPenColor(Color.GREEN);
                    }
                }
                StdDraw.filledSquare(xInit, yInit, radius);
                xInit += radius*2;
            }
            yInit -= radius*2;
        }
    }
    
    public static void main(String[] args) {
        int N = StdIn.readInt();
//        int N = Integer.parseInt(args[0]);
        PercolationForTest pla = new PercolationForTest(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            pla.isFull(p, q);
//            pla.open(p, q);
        }
//        if (pla.percolates()) {
//            StdOut.println("The grid is percolated");
//        }
//        else {
//            StdOut.println("The grid is NOT percolated");
//        }
//        int x = 0;
//        int y = 0;
//        int i = 1;
//        int[][] count = new int[N][N];
//        for (int m = 0; m < N; m++) {
//            for (int n = 0; n < N; n++) {
//                count[m][n] = 0;
//            }
//        }
//        while (!pla.percolates()) {
//            x = StdRandom.uniform(1, N+1);
//            y = StdRandom.uniform(1, N+1);
////            if (count[x][y] != 0) continue;
////            count[x][y] = 1;
////            StdOut.print("[" + x + ", " + y + "]");
//            if (i % N == 0) {
////                StdOut.print("\n");
//                i = 1;
//            }
//            else {
//                i++;
//            }
//            pla.open(x, y);
////            pla.printGridPic();
//        }
//        StdOut.println("\n==========================");
//        double threshold = pla.getThreshold();
//        StdOut.printf("Treshold is %.6f\n", threshold);
//        pla.printGrid();
//        pla.printGridPic();
        
    }
}

package eightPuzzel;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Board {
    private int[][] blocks;
    private int dimension;
    public Board (int[][] blocks) {
        int N = blocks.length;
        dimension = N;
        this.blocks = new int[N][N];
        copyArrs(this.blocks, blocks);
    }
    public int dimension() {
        return dimension;
    }
    private void copyArrs(int[][]dest, int[][]src) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }
    public static void main(String[] args) {
        String dataFile = args[0];
        dataFile = System.getProperty("user.dir") + "/" + dataFile;
        System.out.println("file is " + dataFile);
        In in = new In(dataFile);
        int N = in.readInt();
        StdOut.println("Dimension is " + N);
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        StdOut.println("blocks Dimension is " + blocks.length);
        Board initial = new Board(blocks);
    }
}

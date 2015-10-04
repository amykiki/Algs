package eightPuzzel;

import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
public class Board {
    private int[][] blocks;
    private int dimension;
    private int move;
    private int hamm;
    private int man;
    class Index {
        private int x;
        private int y;
        public Index(int aX, int aY) {
            x = aX;
            y = aY;
        }
        public void setIndex(int aX, int aY) {
            x = aX;
            y = aY;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }
    private Index zeorIndex;
    public Board (int[][] blocks) {
        int N = blocks.length;
        dimension = N;
        move = 0;
        zeorIndex = new Index(0, 0);
        this.blocks = blocks;
        hamm = hamming();
        man = manhattan();
    }
    public int dimension() {
        return dimension;
    }
    public int hamming() {
        int hamming = 0;
        int value = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                value = i*dimension + j + 1;
                if (value != blocks[i][j] && blocks[i][j] != 0) {
                    hamming++;
                }
            }
        }
        return hamming;
    }
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int value = blocks[i][j];
                if (value != 0) {
                    int blockX = (value -1) / dimension;
                    int blockY = (value -1) % dimension;
                    manhattan += Math.abs(blockX - i);
                    manhattan += Math.abs(blockY - j);
                }
            }
        }
        return manhattan;
    }
    public Board twin() {
        int[][] twinBlocks = new int[dimension][dimension];
        copyArrs(twinBlocks, blocks);
        Random r = new Random();
        int b1X = 0;
        int b1Y = 0;
        int b2X = 0;
        int b2Y = 0;
        while (b1X == b2X && b1Y == b2Y) {
            b1X = r.nextInt(dimension);
            b1Y = r.nextInt(dimension);
            b2X = r.nextInt(dimension);
            b2Y = r.nextInt(dimension);
        }
        System.out.format("b1X = %d, b1Y = %d, b2X = %d, b2Y = %d\n", b1X, b1Y, b2X, b2Y);
        int tmp = twinBlocks[b1X][b1Y];
        twinBlocks[b1X][b1Y] = twinBlocks[b2X][b2Y];
        twinBlocks[b2X][b2Y] = tmp;
        Board twinBoard = new Board(twinBlocks);
        return twinBoard;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d\n", dimension));
        for (int i = 0; i < dimension; i++) {
            sb.append(" ");
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format("%-4d", blocks[i][j]));
            }
            sb.append("\n");
        }
        String result = sb.toString();
        return result;
    }
    private void copyArrs(int[][]dest, int[][]src) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }
    public void lookZero() {
        boolean flag = false;
        for (int i = 0; i < dimension && !flag ; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    zeorIndex.setIndex(i, j);
                    System.out.format("zeroX = %d, zeroY = %d\n", zeorIndex.getX(), zeorIndex.getY());
                    flag = true;
                    break;
                }
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
        StdOut.println("hamming is " + initial.hamming());
        StdOut.println("manhattan is " + initial.manhattan());
        StdOut.println(initial.toString());
        initial.lookZero();
        Board twinBoard = initial.twin();
        StdOut.println(twinBoard.toString());
        twinBoard.lookZero();
    }
}

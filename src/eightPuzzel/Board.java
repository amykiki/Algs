package eightPuzzel;

import java.util.ArrayList;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private class Node {
        private int x;
        private int y;
        public Node(int aX, int aY) {
            x = aX;
            y = aY;
        }
        public Node() {
            x = 0;
            y = 0;
        }
        public void setNode(int aX, int aY) {
            x = aX;
            y = aY;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public String toString() {
            return "X =" + x + " Y=" + y + "\n";
        }
        public boolean equals(Object otherObject) {
            if (otherObject == null) return false;
            if (this == otherObject) return true;
            if (getClass() != otherObject.getClass()) return false;
            
            Node other = (Node) otherObject;
            return (this.x == other.x && this.y == other.y);
        }
    }
    private int[][] blocks;
    private int dimension;
    private int curHamm;
    private int curMan;
    private Node curZero;
    private Node lastZero;
    public Board(int[][] blocks) {
        int N = blocks.length;
        dimension = N;
        this.blocks = new int[N][N];
        copyArrs(this.blocks, blocks);
        curHamm = -1;
        curMan = -1;
        curZero = null;
        lastZero = null;
    }
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board other = (Board) y;
        if (this.dimension != other.dimension) return false;
        boolean result = true;
        for (int i = 0; i < dimension && result; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != other.blocks[i][j]) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
    public int dimension() {
        return dimension;
    }
    public int hamming() {
        if (curHamm == -1) {
            int hamming = 0;
            int value = 0;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    value = i*dimension + j + 1;
                    if (blocks[i][j] != 0 && value != blocks[i][j]) {
                        hamming++;
                    }
                }
            }
            curHamm = hamming;
            return hamming;
        }
        return curHamm;
    }
    public int manhattan() {
        if (curMan == -1) {
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
            curMan = manhattan;
            return manhattan;
        }
        return curMan;
    }
    public Board twin() {
        Board twinBoard = new Board(blocks);
        Random r = new Random();
        int b1X = 0;
        int b1Y = 0;
        int b2X = 0;
        int b2Y = 0;
        int b1 = 0;
        int b2 = 0;
        while (b1 == b2 || b1 == 0 || b2 == 0) {
            b1X = r.nextInt(dimension);
            b1Y = r.nextInt(dimension);
            b2X = r.nextInt(dimension);
            b2Y = r.nextInt(dimension);
            b1 = twinBoard.blocks[b1X][b1Y];
            b2 = twinBoard.blocks[b2X][b2Y];
        }
//        System.out.format("b1X = %d, b1Y = %d, b2X = %d, b2Y = %d\n", 
//        b1X, b1Y, b2X, b2Y);
        int tmp = twinBoard.blocks[b1X][b1Y];
        twinBoard.blocks[b1X][b1Y] = twinBoard.blocks[b2X][b2Y];
        twinBoard.blocks[b2X][b2Y] = tmp;
//        StdOut.println(this.toString());
//        StdOut.println(twinBoard.toString());
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
    
    public Iterable<Board> neighbors() {
        if (this.curZero == null) curZero = lookZero();
        int x = curZero.getX();
        int y = curZero.getY();
        ArrayList<Node> nodes = new ArrayList<>(4);
        nodes.add(new Node(x-1, y));
        nodes.add(new Node(x+1, y));
        nodes.add(new Node(x, y-1));
        nodes.add(new Node(x, y+1));
        ArrayList<Board> boards = new ArrayList<>();
        for (Node node : nodes) {
            int i = node.getX();
            int j = node.getY();
            if (i >= 0 && i < dimension && j >= 0 && j < dimension) {
                if (node.equals(this.lastZero)) continue;
                int value = blocks[i][j];
                int nextMan = curMan - nodeManhattan(node, value) 
                              + nodeManhattan(curZero, value);
                int nextHamm = curHamm - nodeHamming(node, value) 
                               + nodeHamming(curZero, value);
                Board nextBoard = new Board(blocks);
                nextBoard.curHamm = nextHamm;
                nextBoard.curMan = nextMan;
                nextBoard.curZero = node;
                nextBoard.lastZero = this.curZero;
                exchangeBlock(curZero, node, nextBoard.blocks);
                boards.add(nextBoard);
            }
        }
        return boards;
    }
    public boolean isGoal() {
        if (this.hamming() == 0) {
            return true;
        }
        return false;
    }
    
    private void exchangeBlock(Node src, Node dest, int[][]arr) {
        int tmp = arr[src.getX()][src.getY()];
        arr[src.getX()][src.getY()] = arr[dest.getX()][dest.getY()];
        arr[dest.getX()][dest.getY()] = tmp;
    }
    private int nodeHamming(Node node, int value) {
        int i = node.getX();
        int j = node.getY();
        int hammValue = i*dimension + j + 1;
        if (value != 0 && value != hammValue) return 1;
        return 0;
    }
    
    private int nodeManhattan(Node node, int value) {
        int i = node.getX();
        int j = node.getY();
        int manhattan = 0;
        if (value != 0) {
            int blockX = (value -1) / dimension;
            int blockY = (value -1) % dimension;
            manhattan += Math.abs(blockX - i);
            manhattan += Math.abs(blockY - j);
        }
        return manhattan;
    }
    private void copyArrs(int[][]dest, int[][]src) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                dest[i][j] = src[i][j];
            }
        }
    }
    private Node lookZero() {
        boolean flag = false;
        Node zero = new Node();
        for (int i = 0; i < dimension && !flag; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    zero.setNode(i, j);
//                    System.out.format("zeroX = %d, zeroY = %d\n", 
//                    curZero.getX(), curZero.getY());
                    flag = true;
                    break;
                }
            }
        }
        return zero;
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
        initial.twin();
//        initial.isGoal();
    }
}

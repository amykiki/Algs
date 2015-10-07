import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int[] blocks;
    private int blockLength;
    private int dimension;
    private int curHamm;
    private int curMan;
    private int curZero;
//    private int lastZero;
    private int printLength;
    private Board parent;
    public Board(int[][] blocks) {
        int N = blocks.length;
        dimension = N;
        blockLength = N*N;
        printLength = String.valueOf(blockLength - 1).length() + 2;
        this.blocks = new int[blockLength];
        copyArrs1D(this.blocks, blocks);
        curHamm = -1;
        curMan = -1;
        curZero = -1;
        parent = null;
    }
    private Board(int[] blocks, int dimension) {
        this.dimension = dimension;
        this.blockLength = dimension * dimension;
        this.printLength = String.valueOf(blockLength - 1).length() + 2;
        this.blocks = new int[blockLength];
        copyArrs(this.blocks, blocks);
        curHamm = -1;
        curMan = -1;
        curZero = -1;
        parent = null;
    }
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board other = (Board) y;
        if (this.dimension != other.dimension) return false;
        if (this.lookZero() != other.lookZero()) return false;
        if (this.parent != null && this.parent.parent == other) return true;
        if (other.parent != null && other.parent.parent == this) return true;
        if (this.hamming() != other.hamming()) return false;
        if (this.manhattan() != other.manhattan()) return false;
        boolean result = true;
        for (int i = 0; i < blockLength; i++) {
            if (this.blocks[i] != other.blocks[i]) {
                result = false;
                break;
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
            for (int i = 0; i < blockLength; i++) {
                value = i + 1;
                if (blocks[i] != 0 && value != blocks[i]) {
                    hamming++;
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
                    int value = blocks[i*dimension + j];
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
        Board twinBoard = new Board(this.blocks, this.dimension);
        Random r = new Random();
        int b1 = 0;
        int b2 = 0;
        while (b1 == b2 || twinBoard.blocks[b1] == 0 || twinBoard.blocks[b2] == 0) {
            b1 = r.nextInt(blockLength);
            b2 = r.nextInt(blockLength);
        }
//        System.out.format("b1 = %d, b2 = %d, b1Value = %d, b2Value = %d\n", 
//                           b1, b2, twinBoard.blocks[b1], twinBoard.blocks[b2]);
        exchangeBlock(b1, b2, twinBoard.blocks);
//        System.out.println(this.toString());
//        System.out.println(twinBoard.toString());
        return twinBoard;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d\n", dimension));
        for (int i = 0; i < dimension; i++) {
            sb.append(" ");
            for (int j = 0; j < dimension; j++) {
                int curIndex = i * dimension + j;
                sb.append(String.format("%-" + printLength + "d", blocks[curIndex]));
            }
            sb.append("\n");
        }
        String result = sb.toString();
        return result;
    }
    
    public Iterable<Board> neighbors() {
        if (this.curZero == -1) curZero = lookZero();
        int x = curZero / dimension;
        int y = curZero % dimension;
        int[] nodes = {curZero - dimension, curZero + dimension, 
                       curZero - 1, curZero + 1};
        ArrayList<Board> boards = new ArrayList<>();
        for (int node : nodes) {
            int i = node / dimension;
            int j = node % dimension;
            if (i != x && j != y) continue;
            if (i >= 0 && i < dimension && j >= 0 && j < dimension) {
                int value = blocks[node];
                int nextMan = this.manhattan() - nodeManhattan(node, value) 
                              + nodeManhattan(curZero, value);
                int nextHamm = this.hamming() - nodeHamming(node, value) 
                               + nodeHamming(curZero, value);
                Board nextBoard = new Board(this.blocks, this.dimension);
                nextBoard.curHamm = nextHamm;
                nextBoard.curMan = nextMan;
                nextBoard.curZero = node;
                nextBoard.parent = this;
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
    
    private void exchangeBlock(int src, int dest, int[]arr) {
        int tmp = arr[src];
        arr[src] = arr[dest];
        arr[dest] = tmp;
    }
    private int nodeHamming(int node, int value) {
        int hammValue = node + 1;
        if (value != 0 && value != hammValue) return 1;
        return 0;
    }
    
    private int nodeManhattan(int node, int value) {
        int i = node / dimension;
        int j = node % dimension;
        int manhattan = 0;
        if (value != 0) {
            int blockX = (value -1) / dimension;
            int blockY = (value -1) % dimension;
            manhattan += Math.abs(blockX - i);
            manhattan += Math.abs(blockY - j);
        }
        return manhattan;
    }
    private void copyArrs1D(int[] dest, int[][]src) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                dest[i*dimension + j] = src[i][j];
            }
        }
    }
    private void copyArrs(int[] dest, int[] src) {
        for (int i = 0; i < blockLength; i++) {
            dest[i] = src[i];
        }
    }
    private int lookZero() {
        if (this.curZero == -1) {
            for (int i = 0; i < blockLength; i++) {
                if (blocks[i] == 0) {
                    this.curZero = i;
                    break;
                }
            }
        }
        return this.curZero;
    }
    
}
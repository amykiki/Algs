import java.util.ArrayList;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Board initial;
    private Board twinInitial;
    private int curMove;
    private int solveResult;
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int parent;
        private int priority;
        private int move;
        private int num;
        public SearchNode(Board aBoard, int aNum, int aPriority, int aParent, int aMove) {
            board = aBoard;
            num = aNum;
            priority = aPriority;
            parent = aParent;
            move = aMove;

        }
        public int compareTo(SearchNode otherNode) {
            if (this.priority > otherNode.priority) return +1;
            if (this.priority < otherNode.priority) return -1;
            if (this.board.manhattan() < otherNode.board.manhattan()) return -1;
            if (this.board.manhattan() > otherNode.board.manhattan()) return +1;
            return 0;
        }
    }
    private ArrayList<SearchNode> usedNodes = new ArrayList<>();
    public Solver(Board aInitial) {
        initial = aInitial;
        twinInitial = initial.twin();
        curMove = 0;
        solveResult = 0;
    }

    public boolean isSolvable() {
        if (solveResult == 0) {
            MinPQ<SearchNode> solvePQ = new MinPQ<>();
            SearchNode node1 = new SearchNode(initial, 1, initial.manhattan(), -1, 0);
            SearchNode node2 = new SearchNode(twinInitial, 2, twinInitial.manhattan(), -1, 0);
            solvePQ.insert(node1);
            solvePQ.insert(node2);
            SearchNode node = solvePQ.delMin();
            
            while (!node.board.isGoal()) {
                usedNodes.add(node);
                addPQ(node.board, solvePQ);
                node = solvePQ.delMin();
            }
            if (node.num == 1) {
                solveResult = 1;
                usedNodes.add(node);
                this.curMove = node.move;
            }
            else {
                solveResult = -1;
            }
        }
        if (solveResult == 1) return true;
        return false;
    }
    private void addPQ(Board board, MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = board.neighbors();
        int parent = usedNodes.size() - 1;
        SearchNode pNode = usedNodes.get(parent);
        int move = pNode.move;
        int pParent = pNode.parent;
        move++;
        for (Board neighborBoard : neighbors) {
            if (pParent != -1 && neighborBoard.equals(usedNodes.get(pParent).board))
                continue;
            int priority = neighborBoard.manhattan() + move;
            SearchNode node = new SearchNode(neighborBoard, pNode.num, priority, parent, move);
            pq.insert(node);
        }
    }
    public int moves() {
        if (isSolvable()) return curMove;
        return -1;
    }
    public Iterable<Board> solution() {
        if (isSolvable()) {
            LinkedList<Board> solutions = new LinkedList<>();
            int i = usedNodes.size() - 1;
            while (i >= 0) {
                solutions.addFirst(usedNodes.get(i).board);
                i = usedNodes.get(i).parent;
            }
            return solutions;
        }
        return null;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}

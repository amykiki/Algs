import java.util.ArrayList;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Board initial;
    private Board twinInitial;
    private int curMove;
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int parent;
        private int priority;
        private int move;
        
        public SearchNode(Board aBoard, int aPriority, int aParent, int aMove) {
            board = aBoard;
            priority = aPriority;
            parent = aParent;
            move = aMove;

        }
        public SearchNode(Board aBoard, int aPriority) {
            this(aBoard, aPriority, -1, 0);
        }
        public int compareTo(SearchNode otherNode) {
            if (this.priority == otherNode.priority) return 0;
            if (this.priority > otherNode.priority) return +1;
            return -1;
        }
    }
    private ArrayList<SearchNode> initUsedNodes = new ArrayList<>();
    private MinPQ<SearchNode> initPQ = new MinPQ<>();
    private ArrayList<SearchNode> twinUsedNodes = new ArrayList<>();
    private MinPQ<SearchNode> twinPQ = new MinPQ<>();
    
    public Solver(Board aInitial) {
        initial = aInitial;
        twinInitial = initial.twin();
        curMove = 0;
    }
    public boolean isSolvable() {
        if (initial.isGoal()) return true;
        if (twinInitial.isGoal()) return false;
        initUsedNodes.add(new SearchNode(initial, initial.manhattan()));
        twinUsedNodes.add(new SearchNode(twinInitial, twinInitial.manhattan()));
        Board board = initial;
        Board board2 = twinInitial;
        do {
            SearchNode node = goNext(board, initUsedNodes, initPQ);
            board  = node.board;
            curMove = node.move;
            board2 = goNext(board2, twinUsedNodes, twinPQ).board;
        } while (!board.isGoal() && !board2.isGoal());
        
        if (board.isGoal()) return true;
        return false;
    }
    private SearchNode goNext(Board board, ArrayList<SearchNode> usedNodes, 
                              MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = board.neighbors();
        int parent = usedNodes.size() - 1;
        int move = usedNodes.get(parent).move;
        move++;
        for (Board neighborBoard : neighbors) {
            int priority = neighborBoard.manhattan() + move;
            SearchNode node = new SearchNode(neighborBoard, priority, parent, move);
            pq.insert(node);
        }
        SearchNode nextNode = pq.delMin();
        usedNodes.add(nextNode);
        return nextNode;
    }
    public int moves() {
        if (isSolvable()) return curMove;
        return -1;
    }
    public Iterable<Board> solution() {
        LinkedList<Board> solutions = new LinkedList<>();
        int i = initUsedNodes.size() - 1;
        while (i >= 0) {
            solutions.addFirst(initUsedNodes.get(i).board);
            i = initUsedNodes.get(i).parent;
        }
        curMove = solutions.size();
        return solutions;
    }
    public static void main(String[] args) {
//        String dataFile = args[0];
//        dataFile = System.getProperty("user.dir") + "/" + dataFile;
//        In in = new In(dataFile);
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

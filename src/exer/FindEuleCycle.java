package exer;
import edu.princeton.cs.algs4.*;
public class FindEuleCycle {
    public boolean euleCycle;
    private ST<Integer, Boolean>[] marked;
    private Stack<Integer> vers;
    private boolean[] connected;
    private int count;
    private int edges;
    private int startV;
    public FindEuleCycle(Graph G) {
        euleCycle = true;
        if (!evenDegree(G)) {
            euleCycle = false;
            return;
        }
        connected = new boolean[G.V()];
        if (!connected(G)) {
            euleCycle = false;
            return;
        }
        marked = (ST<Integer, Boolean>[]) new ST[G.V()];
        for (int v = 0; v < G.V(); v++) {
            marked[v] = new ST<Integer, Boolean>();
        }
        vers = new Stack<>();
        edges = 0;
        startV = 0;
        findCycle(G, startV, -1);
    }
    private void findCycle(Graph G, int v, int u) {
        vers.push(v);
        for (int w: G.adj(v)) {
            if (!marked[v].contains(w)) {
                marked[v].put(w, true);
                marked[w].put(v, true);
                edges++;
                findCycle(G, w, v);
            }
        }
        if (edges == G.E() && v == startV) {
            return;
        }
        else {
            vers.pop();
        }
    }
    private void dfs (Graph G, int v) {
        connected[v] = true;
        for (int w: G.adj(v)) {
            if (!connected[w]) {
                dfs(G, w);
            }
        }
    }
    private boolean connected(Graph G) {
        count = 0;
        for (int v = 0; v < G.V(); v++) {
            if (!connected[v]) {
                if (count > 0) {
                    return false;
                }
                dfs(G, v);
                count++;
            }
        }
        return true;
    }
    private boolean evenDegree(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if ((G.degree(v) < 2) || (G.degree(v) % 2 != 0)) return false;
        }
        return true;
    }
    public boolean isEuleCycle() {
        return euleCycle;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G  = new Graph(in);
        FindEuleCycle fec = new FindEuleCycle(G);
        if (fec.isEuleCycle()) {
            
        }
        else {
            System.out.println("The Graph is not Eule Cycle");
        }
    }

}

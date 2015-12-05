package exer;

import java.util.Arrays;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class findCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    public findCycle(Graph g) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) {
                dfs(g, v, -1);
            }
        }
       
    }
    private void dfs (Graph g, int v, int u) {
        marked[v] = true;
        for (int w: g.adj(v)) {
            if (cycle != null) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w, v);
            }
            else {
                if (w != u) {
                    System.out.println("v = " + v);
                    System.out.println("w = " + w);
                    System.out.println(Arrays.toString(edgeTo));
                    cycle = new Stack<Integer>();
                    for (int vi = v; vi != w; vi = edgeTo[vi]) {
                        System.out.println("vi = " + vi);
                        cycle.push(vi);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }
        }
    }
    public Iterable<Integer> cycle() {
        return cycle;
    }
    public boolean hasCycle() {
        return cycle != null;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph g = new Graph(in);
        StdOut.println(g);
        findCycle fc = new findCycle(g);
        if (fc.hasCycle()) {
            for (int v: fc.cycle) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        else {
            System.out.println("The grapsh is acyclic");
        }
    }
}

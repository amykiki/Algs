package exer;

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class FindBipartite {
    private int[] marked;
    private boolean bipartite;
    
    public FindBipartite(Graph G) {
        marked = new int[G.V()];
        bipartite = true;
        for (int v = 0; v < G.V(); v++) {
            if (marked[v] == 0) {
                dfs(G, v, -1);
            }
        }
    }
    private void dfs (Graph G, int v, int n) {
        marked[v] = n * -1;
        for (int w: G.adj(v)) {
            if (!bipartite) return;
            if (marked[w] == 0) {
                dfs(G, w, marked[v]);
            }
            else {
                if (marked[w] != marked[v]*-1) {
                    bipartite = false;
                }
            }
        }
    }
    public boolean isBipatite () {
        return bipartite;
    }
    public int[] marked() {
        return Arrays.copyOf(marked, marked.length);
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G  = new Graph(in);
        FindBipartite fb = new FindBipartite(G);
        if (fb.isBipatite()) {
            System.out.println("Graph is bipatite");
            int[] marked = fb.marked();
            int n = marked[0];
            for (int i = 0; i < marked.length; i++) {
                if (marked[i] == n) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
            for (int i = 0; i < marked.length; i++) {
                if (marked[i] != n) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }
        else {
            System.out.println("Graph can not be bipatite");
        }
    }

}

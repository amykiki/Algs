import java.util.Arrays;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class SAP {
    private Digraph g;
    private int V;
    private int ancestor;
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }
        g = new Digraph(G);
        V = g.V();
        ancestor = -1;
    }
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if (v == w) {
            return 0;
        }
        int len = -1;
        ancestor = -1;
        int [] stepsv = dfs(v);
        int [] stepsw = dfs(w);
        for (int i = 0; i < V; i++) {
            if (stepsv[i] > 0) {
                if (stepsw[i] > 0) {
                    int tmpLen = stepsv[i] + stepsw[i] - 2;
                    if (len > tmpLen || len < 0) {
                        len = tmpLen;
                        ancestor = i;
                    }
                }
            }
        }
        return len;
        
    }
    
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return ancestor;
    }
    private int[] dfs(int v) {
        int[] steps = new int[V];
        steps[v] = 1;
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int n = queue.dequeue();
            for (int u: g.adj(n)) {
                if (steps[u] == 0) {
                    steps[u] = steps[n] + 1;
                    queue.enqueue(u);
                }
            }
        }
//        System.out.println(Arrays.toString(steps));
        return steps;
    }
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and  " + (V - 1));
        }
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
                    
        }
    }

}

package exer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.*;

public class FindEuleCycle {
    public boolean euleCycle;
    private List<ST<Integer, Boolean>> marked;
    private Stack<Integer> vers;
    private boolean[] connected;
    private int count;
    private int edges;
    private int startV;
    public FindEuleCycle(Graph G, int start) {
        euleCycle = false;
        if (!evenDegree(G)) {
            System.out.println("The Graph degress is not even");
            return;
        }
        connected = new boolean[G.V()];
        if (!connected(G)) {
            System.out.println("the Graph is not Connected");
            return;
        }
        marked = new ArrayList<ST<Integer, Boolean>>();
        for (int v = 0; v < G.V(); v++) {
            marked.add(v, new ST<Integer, Boolean>());
        }
        vers = new Stack<>();
        edges = 0;
        startV = start;
        findCycle(G, startV, -1);
    }
    private void findCycle(Graph G, int v, int u) {
        vers.push(v);
        ST<Integer, Boolean> item = marked.get(v);
        for (int w: G.adj(v)) {
            if (!item.contains(w) || !item.get(w)) {
                item.put(w, true);
                marked.get(w).put(v, true);
                edges++;
                findCycle(G, w, v);
            }
        }
        if (euleCycle || (edges == G.E() && v == startV)) {
            euleCycle = true;
            return;
        }
        else {
            vers.pop();
            marked.get(u).put(v, false);  
            item.put(u, false);
            edges--;
            return;
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
    public Iterator<Integer> path() {
        return vers.iterator();
    }
    public static void main(String[] args) {
//        In in = new In(args[0]);
        In in = new In("D:\\WorkSpace\\Algs\\data\\eulecycle1.txt");
        Graph G  = new Graph(in);
//        FindEuleCycle fec = new FindEuleCycle(G, Integer.parseInt(args[1]));
        FindEuleCycle fec = new FindEuleCycle(G, 0);
        if (fec.isEuleCycle()) {
            Iterator<Integer> path = fec.path();
            while(path.hasNext()) {
                System.out.print(path.next() + " ");
            }
            System.out.println();
        }
        else {
            System.out.println("The Graph is not Eule Cycle");
        }
    }

}

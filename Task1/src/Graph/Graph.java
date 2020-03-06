package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Ali Ibrahim
 */
public class Graph {

    public Graph() {
    }

    class Edge {

        int dest;

        Edge(int dest) {

            this.dest = dest;
        }
    }

    HashMap<Integer, LinkedList<Edge>> graph;
    private int nodeCount, edgeCount;

    public Graph(int nodeCount) {
        graph = new HashMap<>();
        this.nodeCount = nodeCount;
        addNodes();
    }

    private void addNodes() {
        for (int i = 0; i < nodeCount; i++) {
            graph.put(i, new LinkedList<>());
        }
    }

    public void addEdge(int u, int v) {
        if (!graph.get(u).contains(new Edge(v))) {
            graph.get(u).add(new Edge(v));
            graph.get(v).add(new Edge(u));
            edgeCount++;
        }
    }

    public HashSet<Edge> getEdges(int u) {
        HashSet<Edge> edges = new HashSet<>();
        graph.get(u).forEach((e) -> {
            edges.add(e);
        });
        return edges;
    }

    public int getEdgesCount() {
        return edgeCount;
    }

    public int getNodesCount() {
        return nodeCount;
    }

    public void removeIncidentEdges(int u) {
        graph.get(u).clear();

    }

    public static void main(String[] args) {
        Graph g = new Graph(8);
        
        /*
        0----1----2----3
             |    |
             |    |
        4----5----6----7
        
        
        
         */
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(1, 5);
        g.addEdge(2, 3);
        g.addEdge(2, 6);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);

        Relaxation r = new Relaxation();
        ArrayList<Integer> solve = r.solve(g);
        System.out.println("Result: { ");
        for (int i : solve) {
            System.out.print(i + ", ");
        }
        System.out.println("\n } Number of nodes Selected: "+ solve.size());
    }
}

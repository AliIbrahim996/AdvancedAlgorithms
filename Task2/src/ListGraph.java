
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ali Ibrahim
 */
public class ListGraph{
    class Edge {

    int dest, w, Capacity;

    Edge(int dest, int w, int c) {

        this.dest = dest;
        this.w = w;
        this.Capacity = c;
    }

    @Override
    public String toString() {
        return "(" + dest + "," + w + "," + Capacity + ")";
    }
}
    
    List<Edge> g[];
    int n;
    
    ListGraph(int n) {
        this.n = n;
        g = new LinkedList[n];
        for (int i = 0; i < this.n; i++) {
            g[i] = new LinkedList<>();
        }
    }

    public boolean cheackEdge(int u, int v) {
        for (Edge e : g[u]) {
            if (e.dest == v) {
                return true;
            }
        }
        return false;
    }
     public void addEdge(int u, int v, int w) {
        g[u].add(new Edge(v, w, 0));
        g[v].add(new Edge(u, 0, 0));

    }

    

    public int GetCapacity(int u, int v) {
        for (Edge i : g[u]) {
            if (i.dest == v) {
                return i.w;
            }
        }
        return 0;
    }

    public void updateCapacity(Edge e, int c) {
        e.Capacity = c;
    }

    public void updateWeight(int s, int dest, int w) {
        for (Edge i : g[s]) {
            if (i.dest == dest) {
                i.w += w;
            }
        }
    }

    public void updateWeight(Edge e, int w) {
        e.w = w;
    }

    public int GetIndex(int u, int v) {
        for (Edge i : g[u]) {
            if (i.dest == v) {
                int j = g[u].indexOf(i);
                return j;
            }
        }
        return 0;
    }

    public ListGraph creatResidualNetwork() {
        ListGraph Rg = new ListGraph(n);
        for (int i = 0; i < n; i++) {
            for (Edge e : g[i]) {
                Rg.g[i].add(new Edge(e.dest, e.w, e.Capacity));
            }
        }
        return Rg;
    }

    public boolean bfs(int src, List[] parent, int dest) {
        List<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            parent[i] = new LinkedList<>();
        }
        boolean status[] = new boolean[n];
        for (int i = 0; i < n; i++) {
            status[i] = false;
        }
        status[src] = true;
        queue.add(src);
        parent[src].add(-1);
        while (!queue.isEmpty()) {
            int next = queue.remove(0);
            for (Edge e : g[next]) {
                if (!status[e.dest]) {
                    if (e.w != 0) {
                        status[e.dest] = true;
                        parent[e.dest].add(next);
                        queue.add(e.dest);
                    }
                }
            }
        }
        return (status[dest] == true);
    }

    public int fordFulkersonAlgousingBFS(ListGraph rNetwork, int s, int t) {
        int max_flow = 0;
        List[] parent = new LinkedList[n];
        long startTime, endTime, currentTime, totalTime = 0;
        startTime = System.nanoTime();
        while (rNetwork.bfs(s, parent, t)) {
            endTime = System.nanoTime();
            currentTime = (endTime - startTime) / 1000000000;
            int min = Integer.MAX_VALUE;
            int dest = 0;
            for (int cuurnode = t; cuurnode != s; cuurnode = dest) {
                dest = (int) parent[cuurnode].get(0);
                min = Math.min(min, rNetwork.GetCapacity(dest, cuurnode));
            }
            int parrent_node;
            for (int cuurnode = t; cuurnode != s; cuurnode = parrent_node) {
                parrent_node = (int) parent[cuurnode].get(0);
                int index = GetIndex(parrent_node, cuurnode);
                Edge edge1 = g[parrent_node].get(index);
                Edge edge2 = rNetwork.g[parrent_node].get(index);
                if(edge1.w>0){
                    updateCapacity(edge1, edge1.Capacity + min);
                    rNetwork.updateWeight(edge2, edge2.w - min);
                    rNetwork.updateWeight(edge2.dest, parrent_node, edge2.w+min);
                }
                else{
                    updateCapacity(edge1, edge1.Capacity - min);
                }
            }
            max_flow += min;
            totalTime += currentTime;
            startTime = System.nanoTime();
        }
        JOptionPane.showMessageDialog(null, "Total time to find augmenting path " + totalTime + " s");
        return max_flow;
    }
    public static  void main(String args[]){
        
//        ListGraph g = new ListGraph(6);
//         
//        g.addEdge(0, 1, 16);
//        g.addEdge(0, 2, 13);
//        g.addEdge(1, 3, 12);
//        g.addEdge(2, 1, 4);
//        g.addEdge(2, 4, 14);
//        g.addEdge(4, 3, 7);
//        g.addEdge(3, 2, 9);
//        g.addEdge(3, 5, 20);
//        g.addEdge(4, 5, 4);
//        
//        int flow=g.fordFulkersonAlgousingBFS(g.creatResidualNetwork(), 0, 5);
//        System.out.println("The max flow is "+flow);
//        for(int i=0;i<6;i++)
//        for(Edge e:g.g[i]){
//            if(e.Capacity>0)
//            System.out.println(i+"--"+e.Capacity+"-->"+e.dest);  
//        }
    }
}

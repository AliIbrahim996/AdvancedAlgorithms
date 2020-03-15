package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ali Ibrahim
 */
public class VertexcoverApproximation {

    private int selectedEdge;

    public String calculate(Graph g) {

        boolean[] visited = new boolean[g.getNodesCount()];
        String re = "";
        Set<Integer> keySet = g.graph.keySet();
        for (Integer i : keySet) {
            if (!visited[i]) {
                HashSet<Graph.Edge> edges = g.getEdges(i);
                for (Graph.Edge e : edges) {
                    if (!visited[e.dest]) {
                        visited[i] = true;
                        visited[e.dest] = true;
                        g.removeIncidentEdges(i);
                        g.removeIncidentEdges(e.dest);
                        re += "( " + i + ", " + e.dest + " )\n";
                        selectedEdge++;
                        break;
                    }
                }
            }
        }
        return re;
    }

    public int getNumofSelectedEdges() {
        return selectedEdge;
    }

//    public static void main(String[] args) {
//        VertexcoverApproximation v = new VertexcoverApproximation();
//        GraphBuilder gr = new GraphBuilder(6);
//        gr.addEdge(0,3);
//        gr.addEdge(0,4);
//        gr.addEdge(0,5);
//        gr.addEdge(2, 5);
//        gr.addEdge(1, 5);
//
//        ArrayList<Integer> a = v.calculate(gr);
//        System.out.print("{");
//        for (int i = 0; i < a.size(); i++) {
//            System.out.print(a.get(i) + ", ");
//        }
//        System.out.println("}");
//    
    //}
}

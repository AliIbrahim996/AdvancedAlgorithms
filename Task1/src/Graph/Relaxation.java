package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.problems.LinearProgram;

/**
 *
 * @author Ali Ibrahim
 */
public class Relaxation {

    public ArrayList<Integer> solve(Graph graph) {
        Map<Integer, Double> solutionRelaxation = relaxation(graph);
        ArrayList<Integer> cover = new ArrayList<>();
        for (int i = 0; i < graph.getNodesCount(); i++) {
            double sol = solutionRelaxation.get(i);
            /*Vertex Cover via LP: 
            *Solve LP to obtain an optimal fractional solution x
            ∗ Let S ={v | x_v ≥ 0.5}
            Output S
             */
            if (sol >= 0.5) {
                cover.add(i);
            }
        }
        return cover;
    }

    private Map<Integer, Double> relaxation(Graph graph) {

        double[] lpRelaxation = new double[graph.getNodesCount()];

        Map<Integer, Integer> variableVertexMap = new HashMap<>();
        int i = 0;
        for (int v = 0; v < graph.getNodesCount(); v++) {
            lpRelaxation[i] = 1;
            variableVertexMap.put(i, v);
            i++;
        }
        System.out.println("Step1 done! Initialize complete!");
        i = 0;

        LinearProgram lp = new LinearProgram(lpRelaxation);
        lp.setMinProblem(true);
        for (Integer node : graph.graph.keySet()) {
            for (Graph.Edge e : graph.getEdges(node)) {

                int v1 = MapHelper.getKey(variableVertexMap, node);
                int v2 = MapHelper.getKey(variableVertexMap, e.dest);

                double[] constraint = new double[graph.getNodesCount()];

                constraint[v1] = 1.0;
                constraint[v2] = 1.0;

                //x_u + x_v ≥ 1 ∀e = (u,v)∈ E 
                lp.addConstraint(new LinearBiggerThanEqualsConstraint(constraint, 1.0, "c" + i));
                i++;
            }
        }

        System.out.println("Step2 done!\n Constraint: x_u + x_v ≥ 1  added!\n");
        variableVertexMap.keySet().forEach((j) -> {

            double[] constraint = new double[graph.getNodesCount()];

            constraint[j] = 1.0;
            //we relax the constraint x_v ∈{0,1} to x_v ∈[0,1] : 1 ≥ x_v ≥ 0
            lp.addConstraint(new LinearBiggerThanEqualsConstraint(constraint, 0, "c" + (graph.getEdgesCount() + j)));
            constraint = new double[graph.getNodesCount()];

            constraint[j] = 1.0;
            lp.addConstraint(new LinearSmallerThanEqualsConstraint(constraint, 1, "c" + (graph.getEdgesCount() + j)));
        });

        System.out.println("Step3 done!\n Constraint:  1 ≥ x_v ≥ 0 added!\n");
        LinearProgramSolver solver = SolverSingleton.getSolver();
        System.out.println("Solving!");
        double[] solution = solver.solve(lp);
        Map<Integer, Double> solutionMap = new HashMap<>();
        for (int j = 0; j < solution.length; j++) {
            solutionMap.put(variableVertexMap.get(j), solution[j]);
        }
        System.out.println("All done!");
        return solutionMap;

    }
}

package Graph;

import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;

public class SolverSingleton {

    private static LinearProgramSolver solver;

    public synchronized static LinearProgramSolver getSolver() {
        if (solver == null) {

            solver = SolverFactory.getSolver("GLPK");

        }
        return solver;
    }
}

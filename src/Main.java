import com.jsoft.jeuler.solver.EulerSolverFactory;
import com.jsoft.jeuler.utils.Logger;

public class Main {

    public static final int PROBLEM_NUMBER = 686;

    public static void main(String[] args) {
        EulerSolverFactory solver = new EulerSolverFactory();
        solver.getSolver(PROBLEM_NUMBER).solve(new Logger());
    }
}

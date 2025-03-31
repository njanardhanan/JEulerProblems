import com.jsoft.jeuler.solver.EulerSolverFactory;
import com.jsoft.jeuler.utils.Logger;

public class Main {
    public static final int PROBLEM_NUMBER = 938;
    public static final boolean IN_PROGRESS = false;

    public static void main(String[] args) {
        try {
            EulerSolverFactory solver = new EulerSolverFactory();
            solver.getSolver(PROBLEM_NUMBER, IN_PROGRESS).solve(new Logger());
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}

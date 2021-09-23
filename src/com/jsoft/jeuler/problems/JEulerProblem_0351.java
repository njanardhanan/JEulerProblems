package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.EulerHelper;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_0351 extends EulerSolver {

    public JEulerProblem_0351(int problemNumber) {
        super(problemNumber);
    }

    /**
     * https://oeis.org/A216453
     * https://oeis.org/A063985
     * https://oeis.org/A000010
     * http://oeis.org/A002088
     *
     */
    @Override
    public String solve() {
        /**
         *   Refer the document PE_351.pdf.
         *
         *   As per https://oeis.org/A216453 and https://oeis.org/A063985
         * 	 Number of points hidden from the central point by a closer point in a hexagonal orchard of order n
         * 	 is given by
         * 	 = 6 * Sum_{x=1..n} (x - phi(x))
         *
         * 	 which is equal to
         * 	 = 6 * (Sum(x) - Sum(phi(x)))
         *
         */
        long LIMIT = 100_000_000;
        long ans = 6 * (NumericHelper.sumOfN(LIMIT) - EulerHelper.totientSummatory(LIMIT, new HashMap<>()));
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=351";
    }
}

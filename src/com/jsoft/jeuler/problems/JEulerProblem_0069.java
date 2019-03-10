package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Map;

public class JEulerProblem_0069 extends EulerSolver {

    public JEulerProblem_0069(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Refer- https://en.wikipedia.org/wiki/Euler%27s_totient_function

        double maxValue = 0.0;
        int maxN = 0;

        for(int i=2; i<=NumericHelper.ONE_MILLION_INT; i++) {
            Map<Long, Integer> coprimes = NumericHelper.getPrimeFactors(i);
            double v = i * 1.0;
            for(long n : coprimes.keySet()) {
                v = v * (1 - (1/(n*1.0)));
            }
            if(maxValue < (i/v)) {
                maxValue = (i/v);
                maxN = i;
            }
        }
        System.out.printf("%d - %f\n", maxN, maxValue);
        return Integer.toString(maxN);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=69";
    }
}

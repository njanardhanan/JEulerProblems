package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.Permutations;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;
import java.util.Map;

public class JEulerProblem_0070 extends EulerSolver {

    public JEulerProblem_0070(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Refer- https://en.wikipedia.org/wiki/Euler%27s_totient_function

        double minValue = Double.MAX_VALUE;
        int answerN = 0;
        double phiFunction = Double.MAX_VALUE;

        for(int i = 2; i<= NumericHelper.ONE_MILLION_INT * 10; i++) {
            Map<Integer, Integer> coprimes = NumericHelper.getPrimeFactors(i);
            double v = i * 1.0;
            for(int n : coprimes.keySet()) {
                v = v * (1 - (1/(n*1.0)));
            }
            if(Permutations.isPermutation(i, (long)v)) {
                //System.out.printf("%d - %d\n", i, (long) v);

                if (minValue > (i / v)) {
                    phiFunction = v;
                    minValue = (i / v);
                    answerN = i;
                }
            }
        }
        System.out.printf("%d - %f - %f\n", answerN, minValue, phiFunction);
        return Integer.toString(answerN);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=70";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0114 extends EulerSolver {

    public JEulerProblem_0114(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * F(n. m) = No. of combination
         *
         * F({3, 4, 5, 6, 7}, 3) = {2, 4, 7, 11, 17}
         *
         * Search the series 2, 4, 7, 11, 17
         * https://oeis.org/A005252
         *
         * f(n) = Sum_{k=0..floor(n/4)} binomial(n-2k,2k).
         *
         */
        int n = 50 + 1;
        long answer = 0;
        for (int i = 0; i <= (int) Math.floor(n / 4); i++) {
            answer += NumericHelper.binomialCoeff(n - (2 * i), 2 * i);
        }
        System.out.printf("n = %d, answer = %d\n", n-1, answer);
        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=114";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

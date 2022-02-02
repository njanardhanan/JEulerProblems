package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0137 extends EulerSolver {

    public JEulerProblem_0137(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         *  Search for the 10th nugget number 74049690 in OEIS and found
         *  https://oeis.org/A081018
         *  which have a formula for generating golden nugget.
         */
        int LIMIT = 15;
        long ans = getGoldenNugget(LIMIT);
        return Long.toString(ans);
    }

    private long getGoldenNugget(int n) {
        //https://oeis.org/A081018
        if (n == 1) return 2L;
        if (n == 2) return 15L;
        if (n == 3) return 104L;
        return    (8L * getGoldenNugget(n-1))
                - (8L * getGoldenNugget(n-2))
                + getGoldenNugget(n-3);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=137\n";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

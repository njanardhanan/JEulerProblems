package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0120 extends EulerSolver {

    public JEulerProblem_0120(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * Solving the equation will give you
         *
         * rMax = 2a[(a-1)/2]
         *
         */

        int total = 0;

        for(long a=3; a<=1000; a++) {
            total += 2 * a * ((a-1) / 2);
        }
        return Long.toString(total);
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=120";
    }
}

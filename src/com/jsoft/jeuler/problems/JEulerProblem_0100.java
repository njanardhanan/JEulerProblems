package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;


public class JEulerProblem_0100 extends EulerSolver {

    public JEulerProblem_0100(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * The equation turns into
         *
         * x * (x-1) / y * (y-1) = 1/2
         *
         * which is equal to
         *
         * 2x^2 - 2x - y^2 + y = 0
         *
         * This is quadratic diophantine equation
         *
         * Refer:
         * https://www.alpertron.com.ar/QUAD.HTM
         * https://oeis.org/A011900
         * https://oeis.org/A046090
         *
         * Algo:
         *
         * x(i+1) = 3x(i) + 2y(i) - 2
         * y(i+1) = 4x(i) + 3y(i) - 3
         *
         * Example (values from problem 100):
         *
         * x = 15
         * y = 21
         *
         * x(i+1) = 3*15 + 2*21 - 2
         * y(i+1) = 4*15 + 3*21 - 3
         *
         * x(i+1) = 45 + 42 - 2 = 85
         * y(i+1) = 60 + 63 - 3 = 120
         *
         */

        long TARGET = (long) Math.pow(10, 12);
        long x = 15l;
        long y = 21l;

        while(y < TARGET) {
            long x1 = 3 * x + 2 * y - 2;
            long y1 = 4 * x + 3 * y - 3;
            x=x1;
            y=y1;
        }

        return Long.toString(x);
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=100";
    }
}

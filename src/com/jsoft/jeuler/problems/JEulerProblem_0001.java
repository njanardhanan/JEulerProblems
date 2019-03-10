package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0001 extends EulerSolver {

    public JEulerProblem_0001(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int sum = 0;
        for(int i =1; i<1000; i++) {
            if (i%3 == 0 || i%5==0) {
                sum += i;
            }
        }
        return Integer.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.\n" +
                "\n" +
                "Find the sum of all the multiples of 3 or 5 below 1000.";
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0028 extends EulerSolver {

    public JEulerProblem_0028(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 1001;
        int MAX_LIMIT = LIMIT * LIMIT;

        int sumTotal = 0;
        int currentNo = 1;
        int skipNumner = 2;
        int cornerCount = 0;
        while(currentNo <= MAX_LIMIT) {
            sumTotal += currentNo;
            currentNo += skipNumner;
            cornerCount++;

            if(cornerCount == 4) {
                cornerCount = 0;
                skipNumner += 2;
            }
        }


        return Integer.toString(sumTotal);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=28";
    }
}

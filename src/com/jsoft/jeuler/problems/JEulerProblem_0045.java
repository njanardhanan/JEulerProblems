package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_0045 extends EulerSolver {

    public JEulerProblem_0045(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long n = 144l;
        while(true) {
            long hexa = n * (2 * n - 1);
            if (NumericHelper.isTriangleNumber(hexa) && NumericHelper.isPentagonalNumber(hexa)) {
                //System.out.println("Index : " + n);
                n = hexa;
                break;
            } else {
                n++;
            }

        }
        return Long.toString(n);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=45";
    }
}

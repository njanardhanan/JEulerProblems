package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0056 extends EulerSolver {

    public JEulerProblem_0056(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int maxSum = 0;
        for(int a=1; a<100; a++) {
            BigInteger n = BigInteger.valueOf(a);
            for(int b=1; b<100; b++) {
                BigInteger m = n.pow(b);
                int s = (int)NumericHelper.sumOfDigits(m.toString());
                if (maxSum < s) {
                    //System.out.printf("%d to the power of %d contains %d digits sum\n", a, b, maxSum);
                    maxSum = s;
                }
            }
        }

        return Integer.toString(maxSum);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=56";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

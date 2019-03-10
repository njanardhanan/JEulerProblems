package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;

public class JEulerProblem_0016 extends EulerSolver {

    public JEulerProblem_0016(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        BigInteger n = new BigInteger("2");
        String twoPowerThousand = n.pow(1000).toString();

        return Long.toString(NumericHelper.sumOfDigits(twoPowerThousand));

    }

    @Override
    public String getProblemStatement() {
        return "What is the sum of the digits of the number 2^1000?";
    }
}

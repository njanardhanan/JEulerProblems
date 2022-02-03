package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0193 extends EulerSolver {

    public JEulerProblem_0193(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long n = BigInteger.valueOf(2).pow(50).longValue();
        long ans = NumericHelper.noOfSquareFreeNumber(n);
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "A positive integer n is called squarefree, if no square of a prime divides n, thus 1, 2, 3, 5, 6, 7, 10, 11 are squarefree, but not 4, 8, 9, 12.\n" +
                "\n" +
                "How many squarefree numbers are there below 2^50?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("squarefree", "mobius");
    }
}

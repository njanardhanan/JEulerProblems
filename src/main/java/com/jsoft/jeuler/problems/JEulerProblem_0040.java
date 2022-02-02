package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0040 extends EulerSolver {

    public JEulerProblem_0040(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i< NumericHelper.ONE_MILLION_INT; i++) {
            sb.append(i);
        }
        char[] c =sb.toString().toCharArray();
        int n = 1;
        int ans = 1;
        while(n<=NumericHelper.ONE_MILLION_INT) {
            ans = ans * atoi(c[n-1]);
            n = n*10;
        }
        return Integer.toString(ans);
    }

    private Integer atoi(char c) {
        return Character.getNumericValue(c);
    }

    @Override
    public String getProblemStatement() {
        return "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.\n" +
                "\n" +
                "Find the sum of all the multiples of 3 or 5 below 1000.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0030 extends EulerSolver {

    public JEulerProblem_0030(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long maxLimit = (long)Math.pow(9,5) * 5;
        long ans = 0;
        for(long i=2; i<maxLimit; i++) {
            if (i == digitsFifthPower(i)) {
                //System.out.println(i);
                ans += i;
            }
        }
        return Long.toString(ans);
    }

    long digitsFifthPower(long n) {
        long power = 5;
        long result = 0;
        while(n>=10) {
            long x = n%10;
            result = result + (long) Math.pow(x, power);
            n = n/10;
        }
        result = result + (long) Math.pow(n, power);
        return result;
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

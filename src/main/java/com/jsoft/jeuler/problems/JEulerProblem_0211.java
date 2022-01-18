package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0211 extends EulerSolver {

    public JEulerProblem_0211(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 64 * NumericHelper.ONE_MILLION_INT;
        long[] sigma2 = new long[LIMIT];
        for(int i=1; i<LIMIT; i++) {
            for(int j=i; j<LIMIT; j = j+i) {
                sigma2[j] += ((long)i * (long)i);
            }
        }

        long sum = 0;
        for(int i=1; i<LIMIT; i++) {
            if(isPerfectSquare(sigma2[i])) {
                //System.out.printf("%d - %d\n", i, sigma2[i]);
                sum += i;
            }
        }
        return Long.toString(sum);
    }

    public String bruteForce() {
        long LIMIT = 64 * NumericHelper.ONE_MILLION_INT;
        long sum = 0;
        for(long i=1l; i<LIMIT; i++) {
            long sigma2 = NumericHelper.getSigma2(i);
            if(isPerfectSquare(sigma2)) {
                //System.out.printf("%d, ", i);
                //System.out.printf("%d - %d\n", i, sigma2);
                sum += sigma2;
            }
            if(i%NumericHelper.ONE_MILLION_INT == 0) {
                System.out.printf("%d - %d\n", i, sum);
            }
        }
        return Long.toString(sum);
    }

    private boolean isPerfectSquare(long n) {
        long x = (long)Math.sqrt(n);
        return x*x == n;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=211";
    }
}

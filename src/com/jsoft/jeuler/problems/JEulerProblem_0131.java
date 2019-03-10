package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0131 extends EulerSolver {

    public JEulerProblem_0131(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * Given : n^3 + n^2 * p = x^3
         *       : 8^3 + 8^2 * 19 = 12^3
         *
         *  => n^2 (n + p) = x^3
         *  => cuberoot(n^2) * cuberoot(n+p) = x
         *
         *  Therefore, if x is int then n and (n+p) must be a perfect cube.
         *
         */

        int LIMIT = NumericHelper.ONE_MILLION_INT;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);
        int no = 1;
        int count = 0;
        while(true) {
            int x = (no+1) * (no+1) * (no+1);
            int y = no * no * no;
            if(x-y > LIMIT) {
                break;
            }
            if(primes[x-y]) {
                count++;
            }
            no++;
        }
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=131";
    }
}

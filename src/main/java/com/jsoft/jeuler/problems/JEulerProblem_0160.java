package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0160 extends EulerSolver {

    public JEulerProblem_0160(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT = 1_000_000_000_000L;
        long MOD = 100000L;

        /**
         *   When n is a positive multiple of 2500 then, f(n) = f(n * 5^x) for all x >= 0.
         *   i.e., f(5^k * n) = f(n) till n%100000;
         */
        while(LIMIT % MOD == 0) {
            LIMIT /= 5;
        }

        System.out.println("New limit : " + LIMIT);

        long f = 1L;
        for (long x = 2; x<=LIMIT; x++) {
            f *= x;

            /*eliminate the zero's at the end*/
            while(f%10 == 0 ) {
                f /= 10L;
            }

            /**
             * Why * 100?
             * The reason is, we need to have enough digits to get correct factorial value with enough digits.
             * Found this by trial and error.
             *
             * Refer : https://www.wolframalpha.com/input/?i=1000000000000%21
             *
             */
            f %= (MOD*100);

        }
        return String.valueOf(f%MOD);
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

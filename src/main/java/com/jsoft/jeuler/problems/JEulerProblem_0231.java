package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0231 extends EulerSolver {

    public JEulerProblem_0231(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * Using Legendre's formula
         *
         * https://en.wikipedia.org/wiki/Legendre%27s_formula
         *
         * Legendre's formula gives an expression for the exponent of the
         * largest power of a prime p that divides the factorial n!.
         *
         */

        int n = NumericHelper.ONE_MILLION_INT * 20;
        int r = NumericHelper.ONE_MILLION_INT * 15;
        int nMinusr = n - r;
        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList(n);

        long answer = 0;
        for(long p : primes) {
            long factorialExpo = 0;

            long pp = p;
            while(n/pp > 0) {
                factorialExpo += (n/pp);
                factorialExpo -= (r/pp);
                factorialExpo -= (nMinusr/pp);
                pp = pp * p;
            }

            answer += (p * factorialExpo);
        }

        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=231";
    }
}

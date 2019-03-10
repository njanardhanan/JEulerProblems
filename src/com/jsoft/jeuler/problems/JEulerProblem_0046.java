package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0046 extends EulerSolver {

    public JEulerProblem_0046(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int LIMIT = 10000;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);

        int answer = 0;

        for(int n=9; n<LIMIT; n+=2) {
            // n is an odd composite, so it is not prime.
            if(primes[n]) continue;

            boolean found = true;
            int x = 1;
            while(found) {

                if(n <= (2 * x * x)) break;

                //Given n = p + 2 * x * x
                //Therefore p = n - 2 * x * x
                int p = n - 2 * x * x;
                if(primes[p]) {
                    found = false;
                    break;
                }

                x++;
            }

            if(found) {
                answer = n;
                break;
            }


        }

        return Integer.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=45";
    }
}

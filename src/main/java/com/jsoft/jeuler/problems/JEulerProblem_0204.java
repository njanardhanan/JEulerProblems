package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0204 extends EulerSolver {

    public JEulerProblem_0204(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int HUMMING_NUMBER_LIMIT = 100;
        int LIMIT = (int)Math.pow(10, 9);
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);
        int count=0;
        for(int i=HUMMING_NUMBER_LIMIT+1; i<LIMIT; i++) {
            if(!primes[i] && isGeneralizedHammingNumber(i, HUMMING_NUMBER_LIMIT, primes)) {
                count++;
            }
        }
        return Integer.toString(count+HUMMING_NUMBER_LIMIT+1);
    }

    private boolean isGeneralizedHammingNumber(int n, int limit, boolean[] p) {

        while (n%2==0) {
            n = n/ 2;
        }

        if(n>limit && p[n]) {
            return false;
        }

        // n must be odd at this point.  So we can skip 2
        for (int i=3; i<=limit; i+=2) {
            while (n%i==0) {
                n = n/ i;
            }
            if(n == 1) return true;
            if(n>limit && p[n]) {
                return false;
            }
        }

        if (n>limit) {
            return false;
        }

        return true;
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=204";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

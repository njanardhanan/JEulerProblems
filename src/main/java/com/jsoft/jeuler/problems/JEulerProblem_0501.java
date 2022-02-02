package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0501 extends EulerSolver {

    public JEulerProblem_0501(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * TOO SLOW FOR 10^12
         */
        long limit = 1_000_000_000L;
        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList((int)limit/6);
        System.out.println(primes.get(primes.size() - 1));
        int count = 0;

        long maxPrime = 0;

        //Check p^7
        for (long p : primes) {
            if ((long) Math.pow(p, 7) > limit) break;
            if (maxPrime < p) maxPrime = p;
            count++;
        }

        //Check p1^3 * p2
        for (int i = 0; i < primes.size(); i++) {
            long p1 = primes.get(i);
            if(p1 * p1 * p1 > limit) break;
            for (int j = i + 1; j < primes.size(); j++) {
                long p2 = primes.get(j);
                //Check > 0 because of overflow
                if ((p1 * p1 * p1 * p2) > 0 && (p1 * p1 * p1 * p2) < limit) {
                    count++;
                }

                if ((p2 * p2 * p2 * p1) > 0 && (p2 * p2 * p2 * p1) < limit) {
                    count++;
                }
                if (maxPrime < p1) maxPrime = p1;
                if (maxPrime < p2) maxPrime = p2;
            }
        }

        //Check p1 * p2 * p3
        for (int i = 0; i < primes.size(); i++) {
            long p1 = primes.get(i);
            if(p1 * p1 * p1 > limit) break;

            for (int j = i + 1; j < primes.size(); j++) {
                long p2 = primes.get(j);
                if(p1 * p2 * p2 > limit) break;

                for (int k = j + 1; k < primes.size(); k++) {
                    long p3 = primes.get(k);
                    if (p1 * p2 * p3 < limit) {
                        count++;
                    } else {
                        break;
                    }
                    if (maxPrime < p1) maxPrime = p1;
                    if (maxPrime < p2) maxPrime = p2;
                    if (maxPrime < p3) maxPrime = p3;
                }
            }
        }

        System.out.println(maxPrime);

        return String.valueOf(count);
    }

    @Override
    public String getProblemStatement() {
        return "NOT COMPLETED YET :: https://projecteuler.net/problem=501";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "sieve", "unsolved");
    }
}

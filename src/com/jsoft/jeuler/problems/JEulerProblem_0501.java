package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

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
        long limit = 1000000;
        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList((int)limit);
        int count = 0;

        System.out.println("Prime count : " + PrimeNumberHelper.primeCount(10L));

        //Check p^7
        for (long p : primes) {
            if ((long) Math.pow(p, 7) > limit) break;
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
            }
        }

        //Check p1^3 * p2
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
                }
            }

        }

        return String.valueOf(count);
    }

    @Override
    public String getProblemStatement() {
        return "NOT COMPLETED YET :: https://projecteuler.net/problem=501";
    }
}

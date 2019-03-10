package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.*;

public class JEulerProblem_0221 extends EulerSolver {

    public JEulerProblem_0221(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Given:
         *  the first 6 Alexandrian integers being: 6, 42, 120, 156, 420 and 630.
         *
         * From the given series, we can easily find that this series belongs to
         * https://oeis.org/A147811
         *
         * As per OEIS
         *
         * The numbers are of the form p(p+d)(p+(p^2+1)/d), where d runs over
         * divisors of p^2+1 and p runs over all positive integers.
         *
         */

        int TARGET = 150000;

        Set<BigInteger> alexandrian = new HashSet();
        BigInteger ans = new BigInteger("1884161251122450");
        for(long p=1; ;p++) {
            Set<Long> divisors = NumericHelper.getDivisors(p*p+1);
            for(long d : divisors) {
                BigInteger bigP = BigInteger.valueOf(p);
                BigInteger bigD = BigInteger.valueOf(d);
                BigInteger bigAdash = bigP.add(bigP.multiply(bigP).add(BigInteger.ONE).divide(bigD));
                BigInteger bigA = bigP.multiply(bigP.add(bigD)).multiply(bigAdash);
                //long A = p * (p + d) * (p + ((p * p) + 1) / d);
                if(bigA.compareTo(ans) == 0) {
                    System.out.println("Got it at p : " + p);
                }
                alexandrian.add(bigA);
            }

            //The value 7000000 is determined by trail and error.
            //starting from 1M and reducing.
            if(alexandrian.size() > 700000) {
                break;
            }

        }

        List<BigInteger> sortedAlexandrian = new ArrayList(alexandrian);
        Collections.sort(sortedAlexandrian);
        System.out.println(sortedAlexandrian.get(TARGET-1).toString());

//        int i=1;
//        for(long a : sortedAlexandrian) {
//            System.out.printf("%d - %d\n", i, a);
//            i++;
//        }


        return Long.toString(0);
    }

    public String solve1() {
        /**
         * Given:
         *  the first 6 Alexandrian integers being: 6, 42, 120, 156, 420 and 630.
         *
         * From the given series, we can easily find that this series belongs to
         * https://oeis.org/A147811
         *
         * As per OEIS
         *
         * The numbers are of the form p(p+d)(p+(p^2+1)/d), where d runs over
         * divisors of p^2+1 and p runs over all positive integers.
         *
         */

        int TARGET = 150000;

        Set<Long> alexandrian = new HashSet();
        for(long p=1; ;p++) {
            Set<Long> divisors = NumericHelper.getDivisors(p*p+1);
            for(long d : divisors) {
                long A = p * (p + d) * (p + ((p * p) + 1) / d);
                alexandrian.add(A);
            }

            if(alexandrian.size() > TARGET * 10) {
                break;
            }

        }

        List<Long> sortedAlexandrian = new ArrayList(alexandrian);
        Collections.sort(sortedAlexandrian);
        System.out.println(sortedAlexandrian.get(TARGET-1));

//        int i=1;
//        for(long a : sortedAlexandrian) {
//            System.out.printf("%d - %d\n", i, a);
//            i++;
//        }


        return Long.toString(0);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=221";
    }
}

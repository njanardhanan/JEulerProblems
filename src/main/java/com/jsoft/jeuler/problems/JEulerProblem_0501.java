package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class JEulerProblem_0501 extends EulerSolver {

    public JEulerProblem_0501(int problemNumber) {
        super(problemNumber);
    }

    private final boolean DEBUG = false;
    private Map<Long, Long> memoization = new HashMap<>();

    @Override
    public String solve() {
        /**
         * Caution : This will take around 45 mins to throw the answer.
         */
        long LIMIT = (long)Math.pow(10, 2);
        int sqrtLimit = (int)Math.pow(10, 3);

        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList(sqrtLimit);
        if (DEBUG) System.out.println(primes.get(primes.size() - 1));
        long count = 0;

        //Check p^7
        for (long p : primes) {
            if ((long) Math.pow(p, 7) > LIMIT) break;
            if (DEBUG) System.out.println(p + " ^7");
            count++;
        }

        //Check p1^3 * p2
        for (long p : primes) {
            long pCube = (long) Math.pow(p, 3);
            if (pCube > LIMIT) break;
            long v = LIMIT/pCube;
            if(v <= 1) break;
            if (DEBUG) System.out.printf("%d^3, %d-%d\n", p, getPrimeCount(v), getPrimeCount(p));
            count += getPrimeCount(v);
            if (v >= p) count--;
        }

        //Check p1 * p2 * p3
        for (int i = 0; i < primes.size(); i++) {
            long p1 = primes.get(i);
            long pCube = (long) Math.pow(p1, 3);
            if (pCube > LIMIT) break;
            for (int j = i+1; j < primes.size(); j++) {
                long p2 = primes.get(j);
                long pProduct = p1 * p2;
                if (pProduct * p2 > LIMIT) break;
                long v = LIMIT/pProduct;
                if(v <= p2) break;
                if (DEBUG) System.out.printf("%d*%d, %d-%d\n", p1, p2, getPrimeCount(v), getPrimeCount(p2));
                count += getPrimeCount(v);
                count -= getPrimeCount(p2);
            }
        }

        return String.valueOf(count);
    }

    public String solveByBruteFore() {
        /**
         * TOO SLOW FOR 10^12
         */
        long limit = 1_000_000L;
        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList((int)limit/6);
        System.out.println(primes.get(primes.size() - 1));
        long count = 0;

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
                }
            }
        }

        return String.valueOf(count);
    }

    public long getPrimeCount(long num) {

        if (memoization.containsKey(num)) {
            return memoization.get(num);
        }

        int sqrtNum = (int)Math.sqrt(num);
        List<Long> V = LongStream.range(1, sqrtNum+1).map(i -> num/i).boxed().collect(Collectors.toList());
        long lastItem = V.get(V.size() - 1);
        for (long i = lastItem-1; i > 0; --i)
            V.add(i);

        Map<Long, Long> C = new HashMap<>();
        for(long n : V) {
            C.put(n, n-1);
        }

        for (long p = 2; p <= sqrtNum; p++) {
            if (C.get(p) > C.get(p-1)) {   //p is prime
                long cp = C.get(p-1);      // number of primes smaller than p
                long p2 = p*p;
                for(long v : V) {
                    if (v < p2) break;

                    long count = C.get(v);
                    count -= C.get(v/p) - cp;
                    C.put(v, count);
                }
            }
        }

        for (Map.Entry<Long, Long> e : C.entrySet()) {
            memoization.putIfAbsent(e.getKey(), e.getValue());
        }
        return C.get(num);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=501";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "sieve", "slowmode", "prime counting");
    }
}

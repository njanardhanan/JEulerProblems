package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class JEulerProblem_0500 extends EulerSolver {

    private final long MOD = 500500507L;
    private final int LIMIT = 500500;
    private final int MAX_PRIME = 7376507 + 1;

    public JEulerProblem_0500(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Explanation :
         * To find the smallest number with 16 divisors (2^4 divisors), one need to select
         * smallest 4 numbers from the list. And their product is the answer.
         *
         *   | PRIMES/FACTORS | 1 |  2 |   4 |
         *   ---------------------------------
         *   |      2         | 2 |  4 |  16 |
         *   |      3         | 3 |  9 |  81 |
         *   |      5         | 5 | 25 | 625 |
         *
         *   The smallest 4 numbers in the above table are 2, 3, 4 and 5
         *   i.e., 2^1, 3^1, 2^2 and 5^1
         *   i.e., 2^3, 3^1 and 5^1
         *   And their product is 120, which is the answer for smallest number with 16 divisors
         *
         *   To find the answer for smallest number with 2^500500 divisors
         *   form a similar table and select 500500 smallest number among those.
         *   And their product with MOD will be the answer.
         *
         */
        //Generate upto to 500500 primes
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(MAX_PRIME);
        PriorityQueue<Long> maxQ = new PriorityQueue<>((a , b) -> b - a > 0 ? 1 : -1);
        //Found the upper limit of factor loop by trail and error.
        for(int f=0; f<=4; f++) {
            int factor = (int)Math.pow(2, f);
            for(int p : primes) {
                long b = (long)Math.pow(p, factor);
                //Optimization
                // Need to use BigInteger without this optimization.
                if (b > MAX_PRIME) break;
                maxQ.add(b);
                if(maxQ.size() > LIMIT) {
                    maxQ.poll();
                }
            }
        }
        long ans = 1;
        for(long b : maxQ) {
            ans *= b;
            ans %= MOD;
        }

        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=500\n\n" +
                "The number of divisors of 120 is 16.\n" +
                "In fact 120 is the smallest number having 16 divisors.\n" +
                "\n" +
                "Find the smallest number with 2^500500 divisors.\n" +
                "Give your answer modulo 500500507.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "factor", "sieve");
    }
}

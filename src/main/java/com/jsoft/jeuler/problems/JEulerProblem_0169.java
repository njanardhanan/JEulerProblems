package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0169 extends EulerSolver {

    public JEulerProblem_0169(int problemNumber) {
        super(problemNumber);
    }

    private Map<Integer, Integer> memoization = new HashMap();

    private Map<BigInteger, BigInteger> memoizationBigInteger = new HashMap();

    private final static BigInteger ONE = BigInteger.ONE;
    private final static BigInteger TWO = BigInteger.valueOf(2);

    @Override
    public String solve() {

        /**
         * This is Stern’s Diatomic Series
         *
         * Refer :
         * https://www.geeksforgeeks.org/find-n-th-element-from-sterns-diatomic-series/
         * http://oeis.org/A002487
         *
         * As per OEIS
         * a(n+1) = number of ways of writing n as a sum of powers of 2,
         * each power being used at most twice (the number of hyperbinary representations of n)
         *
         */

//        memoization.put(0, 0);
//        memoization.put(1, 1);

        memoizationBigInteger.put(BigInteger.ZERO, BigInteger.ZERO);
        memoizationBigInteger.put(ONE, ONE);

        BigInteger answer = findSDSRecurWithMemoization(BigInteger.valueOf(10).pow(25).add(BigInteger.ONE));

        return answer.toString();
    }

    private BigInteger findSDSRecurWithMemoization(BigInteger n) {
        if (memoizationBigInteger.containsKey(n)) {
            return memoizationBigInteger.get(n);
        }

        if(n.mod(TWO).equals(BigInteger.ZERO)) {
            memoizationBigInteger.put(n.divide(TWO), findSDSRecurWithMemoization(n.divide(TWO)));
            return memoizationBigInteger.get(n.divide(TWO));
        }

        BigInteger x = n.subtract(ONE).divide(TWO);
        memoizationBigInteger.put(x, findSDSRecurWithMemoization(x));

        BigInteger y = n.add(ONE).divide(TWO);
        memoizationBigInteger.put(y, findSDSRecurWithMemoization(y));

        return memoizationBigInteger.get(x).add(memoizationBigInteger.get(y));

    }

    private int findSDSRecurWithMemoization(int n) {
        if (memoization.containsKey(n)) {
            return memoization.get(n);
        }

        if(n%2 == 0) {
            memoization.put(n/2, findSDSRecurWithMemoization(n/2));
            return memoization.get(n/2);
        }

        memoization.put((n - 1) / 2, findSDSRecurWithMemoization((n - 1) / 2));
        memoization.put((n + 1) / 2, findSDSRecurWithMemoization((n + 1) / 2));
        return memoization.get((n - 1) / 2) + memoization.get((n + 1) / 2);

    }

    private int findSDSRecurison(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
        if(n%2 == 0) return findSDSRecurison(n/2);
        return findSDSRecurison((n - 1) / 2) + findSDSRecurison((n + 1) / 2);
    }

    private int findSDS(int n) {

        // Initializing the DP array
        int DP[] = new int[n+1];

        // SET the Base case
        DP[0] = 0;
        DP[1] = 1;

        // Traversing the array from
        // 2nd Element to nth Element
        for (int i = 2; i <= n; i++)
        {

            // Case 1: for even n
            if (i % 2 == 0)
                DP[i] = DP[i / 2];

                // Case 2: for odd n
            else
                DP[i] = DP[(i - 1) / 2] +
                        DP[(i + 1) / 2];
        }

        return DP[n];
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=169";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

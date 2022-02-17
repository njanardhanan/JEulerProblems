package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0171 extends EulerSolver {

    public JEulerProblem_0171(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Test cases:
        if (!solveWithDPBigIntArray(10).equals("67950212")) {
            throw new IllegalStateException("Test case #1 failed");
        }
        if (!solveWithDPBigIntArray(13).equals("996743464")) {
            throw new IllegalStateException("Test case #2 failed");
        }
        return solveWithDPBigIntArray(20);
    }

    private String solveWithDPBigIntArray(int LIMIT) {
        BigInteger MOD = BigInteger.TEN.pow(9);
        BigInteger ans = BigInteger.ZERO;

        //For limit=10^4, max digit square will be 9*9 + 9*9 + 9*9 + 9*9
        int maxDigitSquare = LIMIT * 9 * 9;
        //0 to 9
        int maxDigit = 9;

        //Sieve all the squares till maxDigitSquare
        Set<Integer> sq = new HashSet<>();
        for (int i=1; ; i++) {
            if (i*i > maxDigitSquare) break;
            sq.add(i*i);
        }

        Map<Integer, BigInteger> dpSum = new HashMap<>();
        Map<Integer, BigInteger> dpCount = new HashMap<>();

        for (int i=1; i<=maxDigit; i++) {
            dpSum.put(i*i, BigInteger.valueOf(i));
            dpCount.put(i*i, BigInteger.ONE);
        }

        for (int digitSize=2; digitSize<=LIMIT; digitSize++) {
            Map<Integer, BigInteger> currentDPSum = new HashMap<>();
            Map<Integer, BigInteger> currentDPCount = new HashMap<>();

            for (int digitSqSum = 0; digitSqSum <= maxDigitSquare; digitSqSum++) {
                if (!dpSum.containsKey(digitSqSum)) continue;

                BigInteger prevSum = dpSum.get(digitSqSum);
                BigInteger prevCount = dpCount.get(digitSqSum);

                //Calculate the answer
                if (sq.contains(digitSqSum)) {
                    ans = ans.add(prevSum).mod(MOD);
                }

                for (int digit = 0; digit <= maxDigit; digit++) {
                    int currDigitSqSum = digitSqSum + (digit * digit);

                    BigInteger currSum = currentDPSum.getOrDefault(currDigitSqSum, BigInteger.ZERO);
                    BigInteger newSum = prevSum.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit).multiply(prevCount));
                    currentDPSum.put(currDigitSqSum, currSum.add(newSum));

                    BigInteger currCount = currentDPCount.getOrDefault(currDigitSqSum, BigInteger.ZERO);
                    currentDPCount.put(currDigitSqSum, currCount.add(prevCount));
                }
            }

            dpSum = currentDPSum;
            dpCount = currentDPCount;
        }

        for (int digitSqSum = 0; digitSqSum <= maxDigitSquare; digitSqSum++) {
            if (!dpSum.containsKey(digitSqSum)) continue;
            BigInteger prevSum = dpSum.get(digitSqSum);
            //Calculate the answer
            if (sq.contains(digitSqSum)) {
                ans = ans.add(prevSum).mod(MOD);
            }
        }

//        for (int digitSqSum = 0; digitSqSum <= maxDigitSquare; digitSqSum++) {
//            if (dpSum.containsKey(digitSqSum)) {
//                System.out.println(digitSqSum + " : " + dpSum.get(digitSqSum) + " - " + dpCount.get(digitSqSum));
//            }
//        }

        return ans.toString();
    }

    /**
     *  For some reason this is not giving correct answer, seems like some long overflow issue.
     *  The same logic with BigInteger works, check solveWithDPBigIntArray
     *
     */
    private String solveWithDPLongArray() {
        final long MOD = 1_000_000_000L;
        int LIMIT = 12;
        long ans = 0;

        //For limit=10^4, max digit square will be 9*9 + 9*9 + 9*9 + 9*9
        int maxDigitSquare = LIMIT * 9 * 9;
        //0 to 9
        int maxDigit = 9;

        Set<Integer> sq = new HashSet<>();
        for (int i=1; ; i++) {
            if (i*i > maxDigitSquare) break;
            sq.add(i*i);
        }


        /**
         *  dpSum is a DP array to store Digits square sum
         *  Example:
         *  f(34) = 3^2 + 4^2 = 25
         *  f(43) = 4^2 + 3^2 = 25
         *  f(50) = 5^2 + 0^2 = 25
         *  so, dpSum[2][25] = 34+43+50
         */
        long[][] dpSum = new long[LIMIT + 1][maxDigitSquare + 1];
        /**
         *  dpCount is a DP array to store Digits square sum
         *  Example:
         *  f(34) = 3^2 + 4^2 = 25
         *  f(43) = 4^2 + 3^2 = 25
         *  f(50) = 5^2 + 0^2 = 25
         *  so, dpCount[2][25] = 3
         */
        long[][] dpCount = new long[LIMIT + 1][maxDigitSquare + 1];

        //Init value for digit size 1
        //First digit cannot have 0, so start with 1
        for (int i=1; i<=maxDigit; i++) {
            dpSum[1][i*i] = i;
            dpCount[1][i*i] = 1;
        }

        for (int digitSize=2; digitSize<=LIMIT; digitSize++) {
            for (int digitSqSum=0; digitSqSum<=maxDigitSquare; digitSqSum++) {

                long prevSum = dpSum[digitSize-1][digitSqSum];
                if (prevSum == 0) continue;
                for (int digit = 0; digit <= maxDigit; digit++) {
                    int currDigitSqSum = digitSqSum + (digit * digit);
                    dpSum[digitSize][currDigitSqSum] += (prevSum * 10) + (digit * dpCount[digitSize-1][digitSqSum]);
                    dpSum[digitSize][currDigitSqSum] %= MOD;
                    dpCount[digitSize][currDigitSqSum] += dpCount[digitSize-1][digitSqSum];
                    dpCount[digitSize][currDigitSqSum] %= MOD;
                }
            }
        }

        for (int i=1; i<=LIMIT; i++) {
            for (int j=0; j<dpSum[0].length; j++) {
                if (sq.contains(j)) {
                    ans += dpSum[i][j];
                    ans %= MOD;
                }
            }
        }

        return Long.toString(ans%MOD);
    }

    /**
     * This is a recursive method to return sum of all values for the given starting digit and digitcount.
     */
    private BigInteger search(int num, int digitCount, BigInteger value) {
        if (num < 0) return BigInteger.ZERO;
        if (num == 0 && digitCount == 0) {
            return value;
        }
        if (digitCount == 0) {
            return BigInteger.ZERO;
        }

        BigInteger result = BigInteger.ZERO;
        for (int i=0; i<=9; i++) {
            result = result.add(search(num - (i * i), digitCount - 1, value.multiply(BigInteger.TEN).add(BigInteger.valueOf(i))));
        }

        return result;
    }

    //A bruteforce method.
    public String solveBruteForce() {
        int LIMIT = 4;
        int ans = 0;
        Set<Integer> sq = new HashSet<>();
        for (int i=1; ; i++) {
            if (i*i > (LIMIT * 9 * 9) ) break;
            sq.add(i*i);
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        int count = 0;
        for (int i=1; i<(int)Math.pow(10, LIMIT); i++) {
            int s = sumOfDigitSquares(i);
            if (sq.contains(s)) {
                //System.out.println(i + " : " + s);
                map.putIfAbsent(s, new ArrayList<>());
                map.get(s).add(i);
                count++;
                ans+=i;
            }
        }

        for (int i=1; i<(LIMIT * 9 * 9); i++) {
            if (map.containsKey(i)) {
                System.out.println(i + " : " + map.get(i));
            }
        }

        System.out.println(count);


        /**
         *   10    - 45
         *   100   - 726
         *   1000  - 28083
         *   10000 - 1719828
         */
        return Integer.toString(ans);
    }

    private int sumOfDigitSquares(int n) {
        int ans = 0;
        while(n>0) {
            int r = n%10;
            ans += (r*r);
            n /= 10;
        }
        return ans;
    }


    @Override
    public String getProblemStatement() {
        return "For a positive integer n, let f(n) be the sum of the squares of the digits (in base 10) of n, e.g.\n" +
                "\n" +
                "f(3) = 32 = 9,\n" +
                "f(25) = 22 + 52 = 4 + 25 = 29,\n" +
                "f(442) = 42 + 42 + 22 = 16 + 16 + 4 = 36\n" +
                "\n" +
                "Find the last nine digits of the sum of all n, 0 < n < 10^20, such that f(n) is a perfect square.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("counting", "dynamic programming", "dp");
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JEulerProblem_0862 extends EulerSolver {

    public JEulerProblem_0862(int problemNumber) {
        super(problemNumber);
    }
    private Map<String, BigInteger> memo = new HashMap<>();
    private Map<Long, BigInteger> factorialMemo = new HashMap<>();
    private int memoHitCount = 0;
    private int factorialMemoHitCount = 0;

    /**
     * For every 12 digit increasing number, find the multiset permutation without leading zeros.
     * For example, take a 4 digit increasing number 0223 and list out its multiset permutation.
     * 0223 =>
     *      0223 - permutation with leading zeros
     *      0232 - permutation with leading zeros
     *      0322 - permutation with leading zeros
     *      2023
     *      2032
     *      2203
     *      2230
     *      2302
     *      2320
     *      3022
     *      3202
     *      3220
     *
     *  After ignoring permutation with leading zeros, you can easily see that
     *      2023 - have 8 strictly larger numbers
     *      2032 - have 7 strictly larger numbers
     *      2203 - have 6 strictly larger numbers
     *      2230 - have 5 strictly larger numbers
     *      2302 - have 4 strictly larger numbers
     *      2320 - have 3 strictly larger numbers
     *      3022 - have 2 strictly larger numbers
     *      3202 - have 1 strictly larger numbers
     *      3220 - have 0 strictly larger numbers
     *
     * The total number of strictly larger numbers for increasing number 0223 can be
     * calculated by n * (n+1) / 2
     *
     * so, far 0223 = 8*9/2 = 36
     */


    @Override
    public String solve() {
        //https://www.youtube.com/watch?v=1ByDmzD7jDg
        int target = 12;
        long ans = 0;
        for (int i=1; i<=9; i++) {
            Map<Integer, Integer> digitCountMap = new HashMap<>();
            digitCountMap.put(i, 1);
            ans += generateIncreasingNumber(i, digitCountMap, BigInteger.valueOf(i), target);
        }

        System.out.println("Memo hit count = " + memoHitCount);
        System.out.println("Fact Memo hit count = " + factorialMemoHitCount);

        return Long.toString(ans);
    }

    private long generateIncreasingNumber(int currDigit, Map<Integer, Integer> digitCount, BigInteger currNum, int targetDigit) {
        if (currNum.toString().length() > targetDigit) {
            return 0;
        }

        long ans;
        int zeroCount = targetDigit - currNum.toString().length();
        if (zeroCount > 0) {
            digitCount.put(0, zeroCount);
        }

        //Calculate multisetPermutation
        BigInteger multisetPermutation = calculateMultisetPermutationWithoutLeadingZero(digitCount);

        //Calculate multisetPermutation without leading zeros
        BigInteger multisetPermutationWithoutZero = BigInteger.ZERO;
        if (zeroCount > 0) {
            if (zeroCount == 1) {
                digitCount.remove(0);
            } else {
                digitCount.put(0, zeroCount - 1);
            }
            multisetPermutationWithoutZero = calculateMultisetPermutationWithoutLeadingZero(digitCount);
        }

        long permutation = multisetPermutation.subtract(multisetPermutationWithoutZero).longValue();
        ans =  (permutation * (permutation-1)) / 2;

        //Clean up with zeros
        if (zeroCount > 0 && digitCount.containsKey(0)) {
            digitCount.remove(0);
        }

        for (int i = currDigit; i <= 9; i++) {
            int c = digitCount.getOrDefault(i, 0);
            digitCount.put(i, c + 1);

            ans += generateIncreasingNumber(i, digitCount, currNum.multiply(BigInteger.TEN).add(BigInteger.valueOf(i)), targetDigit);

            if (c == 0) {
                digitCount.remove(i);
            } else {
                digitCount.put(i, c);
            }
        }
        return ans;
    }

    /**
     * Multiset permutation is given by n! / (d1! * d2! * d3!)
     * Example: {1,1,1,2,2,3} => {3-1, 2-2, 1-3}
     * then Multiset permutation is 6!/(3!*2!*1!)
     *
     * Multiset permutation without leading ZERO is given by
     * (n! / (d0! * d1! * d2! * d3!)) - ((n-1)! / ((d0-1)! * d1! * d2! * d3!))
     *
     * Example: {0,1,1,1,2,2,3} => {1-0, 3-1, 2-2, 1-3} (one zero, three ones, two twos, and one threes)
     * then Multiset permutation is
     * 7!/(1!*3!*2!*1!) - 6!/(0!*3!*2!*1!)
     *
     * @param digitCount
     * @return
     */
    private BigInteger calculateMultisetPermutationWithoutLeadingZero(Map<Integer, Integer> digitCount) {
        List<Integer> values = new ArrayList<>(digitCount.values());
        Collections.sort(values);
        String memoKey = (digitCount.containsKey(0) ? "T" : "F") + values.toString();
        if (memo.containsKey(memoKey)) {
            memoHitCount++;
            return memo.get(memoKey);
        }

        int count = values.stream().mapToInt(Integer::intValue).sum();
        BigInteger numerator = factorial(count);
        BigInteger denom = BigInteger.ONE;
        for (Map.Entry<Integer, Integer> e : digitCount.entrySet()) {
            denom = denom.multiply(factorial(e.getValue()));
        }

        BigInteger permutation = numerator.divide(denom);

        memo.put(memoKey, permutation);

        return permutation;
    }

    private BigInteger factorial(long n) {
        if (factorialMemo.containsKey(n)) {
            factorialMemoHitCount++;
            return factorialMemo.get(n);
        }

        BigInteger fact = BigInteger.ONE;
        for (long i=2; i<=n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        factorialMemo.put(n, fact);
        return fact;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=862";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("increasing number", "multiset permutation");
    }
}

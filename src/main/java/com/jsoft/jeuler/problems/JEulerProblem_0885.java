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

public class JEulerProblem_0885 extends EulerSolver {

    public JEulerProblem_0885(int problemNumber) {
        super(problemNumber);
    }

    private static final long MOD = 1123455689;
    private static final int TARGET_TOTAL_DIGIT = 18;

    private Map<List<Integer>, BigInteger> memo = new HashMap<>();
    private int memoHitCount = 0;

    @Override
    public String solve() {
        //https://www.youtube.com/watch?v=1ByDmzD7jDg
        //Map<String, Integer> bruteForceMap = solveBF();
        //System.out.println("Len : " + bruteForceMap.size());

        long ans = 0;
        for (int i=1; i<=9; i++) {
            Map<Integer, Integer> digitCountMap = new HashMap<>();
            digitCountMap.put(i, 1);
            ans += generateIncreasingNumber(i, digitCountMap, BigInteger.valueOf(i));
            ans %= MOD;
        }

        System.out.println("Memo hit count = " + memoHitCount);

        return Long.toString(ans);
    }

    private long generateIncreasingNumber(int currDigit, Map<Integer, Integer> digitCount, BigInteger currNum) {
        if (currNum.toString().length() > TARGET_TOTAL_DIGIT) {
            return 0;
        }

        long ans;
        int zeroCount = TARGET_TOTAL_DIGIT - currNum.toString().length();
        if (zeroCount > 0) {
            digitCount.put(0, zeroCount);
        }
        BigInteger multisetPermutation = calculateMultisetPermutation(digitCount);
        ans = currNum.multiply(multisetPermutation).mod(BigInteger.valueOf(MOD)).longValue();
        if (zeroCount > 0) {
            digitCount.remove(0);
        }

        for (int i = currDigit; i <= 9; i++) {
            int c = digitCount.getOrDefault(i, 0);
            digitCount.put(i, c + 1);

            ans += generateIncreasingNumber(i, digitCount, currNum.multiply(BigInteger.TEN).add(BigInteger.valueOf(i)));
            ans %= MOD;

            digitCount.put(i, c);
        }
        return ans;
    }

    /**
     * Multiset permutation is given by n! / (d1! * d2! * d3!)
     * Example: {1,1,1,2,2,3} => {3-1, 2-2, 1-3}
     * then Multiset permutation is 6!/(3!*2!*1!)
     *
     * @param digitCount
     * @return
     */
    private BigInteger calculateMultisetPermutation(Map<Integer, Integer> digitCount) {
        List<Integer> values = new ArrayList<>(digitCount.values());
        Collections.sort(values);
        if (memo.containsKey(values)) {
            memoHitCount++;
            return memo.get(values);
        }

        int count = values.stream().mapToInt(Integer::intValue).sum();
        BigInteger numerator = factorial(count);
        BigInteger denom = BigInteger.ONE;
        for (Map.Entry<Integer, Integer> e : digitCount.entrySet()) {
            denom = denom.multiply(factorial(e.getValue()));
        }

        BigInteger permutation = numerator.divide(denom);

        memo.put(values, permutation);

        return permutation;
    }

    private BigInteger factorial(long n) {
        BigInteger fact = BigInteger.ONE;
        for (long i=2; i<=n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }


    public Map<String, Integer> solveBF() {
        long total = 0;
        Map<String, Integer> bruteForceDataMap = new HashMap<>();
        for (int i=1; i<100000; i++) {
            long v = getNumber(i);
            String sv = Long.toString(v);
            if (bruteForceDataMap.containsKey(sv)) {
                bruteForceDataMap.put(sv, bruteForceDataMap.get(sv) + 1);
            } else {
                bruteForceDataMap.put(sv, 1);
            }
            //System.out.println(i + " : " + v);
            total += v;
        }
        //1543545675
        System.out.println("Answer by BF: " + (total%MOD));
        return bruteForceDataMap;
    }

    private int getNumber(int i) {
        List<Character> list = new ArrayList<>();
        for (char c : Integer.toString(i).toCharArray()) {
            if (c != '0') {
                list.add(c);
            }
        }
        list.sort(Character::compareTo);
        String v = list.stream().map(String::valueOf).collect(Collectors.joining());
        return Integer.parseInt(v);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=885";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("increasing number", "multiset permutation");
    }
}

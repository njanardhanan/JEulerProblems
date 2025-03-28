package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0788 extends EulerSolver {

    public JEulerProblem_0788(int problemNumber) {
        super(problemNumber);
    }

    private Map<List<Integer>, BigInteger> memo = new HashMap<>();
    private int memoHitCount = 0;

    private static final int TARGET_TOTAL_DIGIT = 2022;
    private static final BigInteger MOD = BigInteger.valueOf(1000000007L);
    private static final BigInteger NINE = BigInteger.valueOf(9);

    @Override
    public String solve() {
        BigInteger modulo = BigInteger.valueOf(1000000007);
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < 2022; i++) {
            if (i%100 == 0) System.out.printf("Reached $%d => %s\n", i, result);
            result = result.add(calc(BigInteger.valueOf(i))).mod(modulo);
        }

        //Output = 471745499
        return result.toString();
    }



    public BigInteger calc(BigInteger x) {
        BigInteger sum = BigInteger.ZERO;
        BigInteger nine = BigInteger.valueOf(9);

        for (BigInteger i = BigInteger.ZERO;
             i.compareTo(x.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)) < 0;
             i = i.add(BigInteger.ONE)) {
            BigInteger term = nine.pow(i.intValue()).multiply(combination(x.add(BigInteger.ONE), i));
            sum = sum.add(term);
        }

        return nine.multiply(sum);
    }

    public BigInteger combination(BigInteger n, BigInteger k) {
        if (k.compareTo(n) > 0) return BigInteger.ZERO;
        if (k.equals(BigInteger.ZERO) || k.equals(n)) return BigInteger.ONE;

        BigInteger numerator = BigInteger.ONE;
        BigInteger denominator = BigInteger.ONE;

        for (BigInteger i = BigInteger.ZERO; i.compareTo(k) < 0; i = i.add(BigInteger.ONE)) {
            numerator = numerator.multiply(n.subtract(i));
            denominator = denominator.multiply(i.add(BigInteger.ONE));
        }

        return numerator.divide(denominator);
    }

    public String solveSlow() {
        //solveBF();

        BigInteger ans = BigInteger.ZERO;

        for (int currLen = 1; currLen <= TARGET_TOTAL_DIGIT; currLen++) {
            System.out.println("Progressing : " + currLen);
            Map<Integer, Integer> numberCountMap = new HashMap<>();
            int dominatingDigit = 0;
            numberCountMap.put(dominatingDigit, currLen);
            BigInteger zeroCount = countDominationNumbers(numberCountMap, dominatingDigit, 0, currLen, TARGET_TOTAL_DIGIT);

            numberCountMap.clear();
            dominatingDigit = 1;
            numberCountMap.put(dominatingDigit, currLen);
            BigInteger oneCount = countDominationNumbers(numberCountMap, dominatingDigit, 0, currLen, TARGET_TOTAL_DIGIT);

            ans = ans.add(zeroCount).add(oneCount.multiply(NINE)).mod(MOD);

            memo.clear();
            System.out.println("Memo count: " + memoHitCount);

        }

        System.out.println();
        System.out.println("Currnet Answer: " + ans.longValue());
        return "0";
    }

    private BigInteger countDominationNumbers(Map<Integer, Integer> numberCount, int dominationDigit, int currValue, int currentLen, int targetLen) {
        if (currentLen == targetLen + 1) {
            return BigInteger.ZERO;
        }
        int minCountRequired = (currentLen/2) + 1;
        if (minCountRequired > numberCount.get(dominationDigit)) {
            return BigInteger.ZERO;
        }

        BigInteger count = getPermutation(numberCount);

        for (int i=currValue; i<=9; i++) {
            if (i == dominationDigit) continue;
            numberCount.putIfAbsent(i, 0);
            numberCount.put(i, numberCount.get(i) + 1);

            BigInteger tmpCount = countDominationNumbers(numberCount, dominationDigit, i,currentLen + 1, targetLen);
            count = count.add(tmpCount).mod(MOD);

            if (numberCount.get(i) == 1) {
                numberCount.remove(i);
            } else {
                numberCount.put(i, numberCount.get(i) - 1);
            }

        }

        return count;
    }

    private BigInteger getPermutation(Map<Integer, Integer> numberCount) {
        //System.out.printf(numberCount.toString() + ", ");
        BigInteger withZero = calculateMultisetPermutation(numberCount);

        if (!numberCount.containsKey(0)) {
            return withZero;
        }

        int zeroCount = numberCount.get(0);
        if (zeroCount >= 2) {
            numberCount.put(0, zeroCount - 1);
        } else {
            numberCount.remove(0);
        }

        BigInteger withOutZero = calculateMultisetPermutation(numberCount);

        numberCount.put(0, zeroCount);
        return withZero.subtract(withOutZero).mod(MOD);
    }

    /**
     * Multiset permutation is given by n! / (d1! * d2! * d3!)
     * Example: {1,1,1,2,2,3} => {3-1, 2-2, 1-3}
     * then Multiset permutation is 6!/(3!*2!*1!)
     *
     * This can be improved by using Memo
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
            denom = denom.mod(MOD).multiply(factorial(e.getValue()).mod(MOD)).mod(MOD);
        }

        BigInteger permutation = numerator.divide(denom);

        memo.put(values, permutation);

        return permutation;
    }

    private BigInteger factorial(long n) {
        BigInteger fact = BigInteger.ONE;
        for (long i=2; i<=n; i++) {
            fact = fact.mod(MOD).multiply(BigInteger.valueOf(i).mod(MOD)).mod(MOD);
        }
        return fact;
    }

    private Map<Integer, Map<Integer, List<Integer>>> countMap = new HashMap<>();
    private void solveBF() {
        int total = 0;
        for (int i=1; i<100000; i++) {
            if (isDominatingNumber(i)) {
                total++;
                //System.out.println(i);
            }
        }
        for (Map.Entry<Integer, Map<Integer, List<Integer>>> entry : countMap.entrySet()) {
            for (Map.Entry<Integer, List<Integer>> integerListEntry : entry.getValue().entrySet()) {
                //if (integerListEntry.getKey() == 3 || integerListEntry.getKey() == 4) {
                    System.out.printf("%s - [%d] %d %s\n", entry.getKey(), integerListEntry.getKey(), integerListEntry.getValue().size(), integerListEntry.getValue());
                //}
            }
        }
        System.out.println("Total : " + total);
    }

    private boolean isDominatingNumber(int i) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = NumericHelper.toDigits(i);
        int len = (list.size() / 2) + 1;
        for (int n : list) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        for (int k : map.keySet()) {
            if (map.get(k) >= len) {
                //String s = String.format("[%d-%d]", k, map.get(k));
                List<Integer> ll;
                Map<Integer, List<Integer>> innerMap;
                if (countMap.containsKey(map.get(k))) {
                    innerMap = countMap.get(map.get(k));
                    if (innerMap.containsKey(k)) {
                        ll = innerMap.get(k);
                    } else {
                        ll = new ArrayList<>();
                    }
                    ll.add(i);
                    innerMap.put(k, ll);
                } else {
                    innerMap = new HashMap<>();
                    ll = new ArrayList<>();
                    ll.add(i);
                    innerMap.put(k, ll);
                }
                countMap.put(map.get(k), innerMap);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=788";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("combination");
    }
}

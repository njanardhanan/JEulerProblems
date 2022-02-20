package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0725 extends EulerSolver {

    public JEulerProblem_0725(int problemNumber) {
        super(problemNumber);
    }

    BigInteger MOD = BigInteger.TEN.pow(16);

    @Override
    public String solve() {
        int LIMIT = 2020;
        BigInteger ans = BigInteger.ZERO;
        for (int d=1; d<=9; d++) {
            List<List<Integer>> listOfPartitions = partitionNumber(new ArrayList<>(), 1, d, new ArrayList<>());
            for (List<Integer> partition : listOfPartitions) {
                //Include current digit d.
                partition.add(d);

                if (partition.size() <= LIMIT) {
                    //System.out.println(partition);

                    //Convert partition list to map.
                    Map<Integer, Integer> numberMap = groupNumbers(partition);

                    //Fill with enough zeros.
                    int zeroCount = LIMIT - (partition.size());
                    if (zeroCount >= 1) {
                        numberMap.put(0, zeroCount);
                    }
                    int len = partition.size() + zeroCount;
                    int sum = partition.stream().mapToInt(Integer::intValue).sum();
                    ans = ans.add(sumOfPermutation(numberMap, len, sum));
                    //System.out.println(numberMap);

                }
            }
        }

        //4598797036650685
        return ans.mod(MOD).toString();
    }

    private Map<Integer, Integer> groupNumbers(List<Integer> ways) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : ways) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        return map;
    }

    /**
     * This function returns the sum of multiset permutation set.
     *
     * https://www.youtube.com/watch?v=MiE8b01Xjbg
     *
     * Example #1
     * Let s = {x1, x2, x3}, a set without duplicate
     * Let n = len(s)
     *
     * Then, sum of permutation of s is given by
     *
     * sum => (n!/n) * (x1+x2+x3) * (111)
     *
     * Example #2
     * Let s = {x1, x1, x2, x3, x3, x3}, a set with duplicate
     * i.e., s = {a x1, b x2, c x3}
     * Let n = len(s)
     *
     * Then, sum of permutation of s (with duplicates) is given by
     *
     * sum => (n!/n) * (a*x1+b*x2+c*x3) * (111111) / (a! * b! * c!)
     *
     * @param map
     * @return
     */
    private BigInteger sumOfPermutation(Map<Integer, Integer> map, int lenOfSet, int sumOfSet) {
        //sum => (n!/n) * (a*x1+b*x2+c*x3) * (111111) / (a! * b! * c!)
        BigInteger num1 = BigInteger.ONE;
        //We can directly reduce n! with no-of-zero!
        for (int i=map.getOrDefault(0, 0) + 1; i<lenOfSet; i++) {
            num1 = num1.multiply(BigInteger.valueOf(i));
        }

        BigInteger num2 = BigInteger.valueOf(sumOfSet);

        //With mod 10^16, maximum repunit will be 16 digits.
        int newLen = lenOfSet > 16 ? 16 : lenOfSet;
        BigInteger num3 = BigInteger.TEN.pow(newLen).subtract(BigInteger.ONE).divide(BigInteger.valueOf(9));

        BigInteger denom = BigInteger.ONE;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getKey() == 0) continue;
            denom = denom.multiply(factorial(e.getValue()));
        }

        return num1.multiply(num2).multiply(num3).divide(denom).mod(MOD);
    }

    private BigInteger factorial(long n) {
        BigInteger fact = BigInteger.ONE;
        for (long i=2; i<=n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    /**
     * Recursive function to print different
     * ways in which n can be written as
     * a sum of 2 or more positive integers
     *  Example:
     *  3 => [1, 1, 1], [1, 2], [3]
     *  4 => [1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2], [4]
     *
     * @param arr - A list to store the numbers whose sum will be n.
     * @param i - A number to include or exclude from the list.
     * @param n - A number for which we want to find the sum.
     * @param output - A list of list to store the output.
     */
    private List<List<Integer>> partitionNumber(List<Integer> arr, int i, int n, List<List<Integer>> output) {
        if (n == 0) {
            output.add(new ArrayList<>(arr));
            return output;
        }

        // Start from previous element in the representation till n
        for(int j = i; j <= n; j++) {

            // Include current element from representation
            arr.add(j);

            // Call function again with reduced sum
            partitionNumber(arr, j, n - j, output);

            // Backtrack to remove current element from representation
            arr.remove(arr.size() - 1);
        }
        return output;
    }

    public String solveBruteForce() {
        long ans = 0;
        for (int i=1; i<1000; i++) {
            int v = isDigitSumNumber(i);
            if (v != -1) {
                System.out.printf("%d (%d), ",i, v);
                ans += i;
            }
        }

        return Long.toString(ans);
    }

    private int isDigitSumNumber(int n) {
        int s = NumericHelper.sumOfDigits(n);
        if (s%2 == 1) return -1;
        if (s > 18) return -1;
        int v = s/2;
        while(n>0) {
            if (n%10 == v) return v;
            n /= 10;
        }
        return -1;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=725\n" +
                "A number where one digit is the sum of the other digits is called a digit sum number or DS-number for short. For example, 352, 3003 and 32812 are DS-numbers.\n" +
                "We define S(n) to be the sum of all DS-numbers of n digits or less.\n" +
                "You are given S(3) = 63270 and S(7) = 85499991450.\n" +
                "Find S(2020). Give your answer modulo 10^16.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("counting", "multiset", "permutation", "dynamic programming", "DP", "recursion", "no of ways");
    }
}

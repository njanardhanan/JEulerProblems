package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0217 extends EulerSolver {

    private BigInteger BMOD = BigInteger.valueOf(3).pow(15);
    private BigInteger ZERO = BigInteger.ZERO;
    private BigInteger ONE = BigInteger.ONE;
    private Map<String, Pair> memo;

    class Pair {
        BigInteger count;
        BigInteger sum;
        Pair(BigInteger count, BigInteger sum) {
            this.sum = sum;
            this.count = count;
        }
    }

    public JEulerProblem_0217(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //278902367
        //6273134
        BigInteger ans = BigInteger.ZERO;
        int TARGET = 47;
        for (int i=1; i<=TARGET; i++) {
            memo = new HashMap<>();
            Pair tmp = calcBalanceNumberSum(0, i, 0);
            ans = ans.add(tmp.sum);
            System.out.printf("%d - %s  - %s\n", i, tmp.sum, ans);

        }

        return ans.mod(BMOD).toString();
    }

    private Pair calcBalanceNumberSum(int currDigitPos, int targetDigitLen, long sumDiff) {
        if (sumDiff < 0) {
            return new Pair(ZERO, ZERO);
        }
        if (currDigitPos == targetDigitLen && sumDiff == 0) {
            return new Pair(ONE, ZERO);
        } else if (currDigitPos == targetDigitLen) {
            return new Pair(ZERO, ZERO);
        }

        String memoKey = currDigitPos + "," + sumDiff;
        if (memo.containsKey(memoKey)) {
            return memo.get(memoKey);
        }

        Pair ans = new Pair(ZERO, ZERO);
        boolean isPosInFirstHalf = false;
        boolean isPosInLastHalf = false;
        int firstHalfEnd = (targetDigitLen + 1) / 2;
        int lastHalfStart = targetDigitLen - ((targetDigitLen + 1) / 2);
        if (currDigitPos >=0 && currDigitPos < firstHalfEnd) {
            isPosInFirstHalf = true;
        }
        if (currDigitPos >=lastHalfStart && currDigitPos < targetDigitLen) {
            isPosInLastHalf = true;
        }

        for (int currDigit = (currDigitPos == 0) ? 1 : 0; currDigit <= 9; currDigit++) {
            if (isPosInFirstHalf) {
                sumDiff += currDigit;
            }
            if (isPosInLastHalf) {
                sumDiff -= currDigit;
            }

            if (sumDiff < 0) {
                break;
            }

            Pair tmpAns = calcBalanceNumberSum(currDigitPos + 1, targetDigitLen, sumDiff);

            //Reset the sumDiff
            if (isPosInFirstHalf) {
                sumDiff -= currDigit;
            }
            if (isPosInLastHalf) {
                sumDiff += currDigit;
            }

            if (tmpAns.count.compareTo(ZERO) >= 0) {
                ans.count = ans.count.add(tmpAns.count);
                int digitPosFromRight = targetDigitLen - 1 - currDigitPos;

                //ans.sum += (currDigit * (long)(Math.pow(10, digitPosFromRight) * tmpAns.count) + tmpAns.sum);
                BigInteger v = BigInteger.valueOf(currDigit).multiply(BigInteger.TEN.pow(digitPosFromRight)).multiply(tmpAns.count);
                ans.sum = ans.sum.add(v.add(tmpAns.sum));
            }
        }

        memo.put(memoKey, ans);
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=217 \n" +
                "A positive integer with $k$ (decimal) digits is called balanced if its first $\\lceil k/2 \\rceil$ digits sum to the same value as its last $\\lceil k/2 \\rceil$ digits, where $\\lceil x \\rceil$, pronounced <i>ceiling</i> of $x$, is the smallest integer $\\ge x$, thus $\\lceil \\pi \\rceil = 4$ and $\\lceil 5 \\rceil = 5$.</p>\n" +
                "<p>So, for example, all palindromes are balanced, as is $13722$.</p>\n" +
                "<p>Let $T(n)$ be the sum of all balanced numbers less than $10^n$. <br>\n" +
                "Thus: $T(1) = 45$, $T(2) = 540$ and $T(5) = 334795890$.</p>\n" +
                "<p>Find $T(47) \\bmod 3^{15}$.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("DP", "digit-sum");
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0845 extends EulerSolver {

    public JEulerProblem_0845(int problemNumber) {
        super(problemNumber);
    }

    // Memoization map
    private Map<String, Long> memo;
    private boolean[] primeArray;

    @Override
    public String solve() {
        long target = (long) 1e16;
        long ans = binarySearch(target);
        return Long.toString(ans);
    }

    private long binarySearch(long target) {
        //Binary search
        long low = (long) 1e4;
        long high = (long) 1e18;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            long v = countNumbersWithPrimeDigitSum(mid);
            if (v == target) {
                return mid;
            }
            if (v < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // Recursive DP function
    private long dp(String num, int pos, int digitSum, boolean tight) {
        if (pos == num.length()) {
            return primeArray[digitSum] ? 1 : 0;
        }

        String key = pos + "," + digitSum + "," + tight;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int limit = tight ? (num.charAt(pos) - '0') : 9;
        long count = 0L;

        for (int digit = 0; digit <= limit; digit++) {
            count += dp(num, pos + 1, digitSum + digit, tight && (digit == limit));
        }

        memo.put(key, count);
        return count;
    }

    // Function to count numbers with prime digit sum below n
    public long countNumbersWithPrimeDigitSum(long n) {
        String numStr = String.valueOf(n);
        memo = new HashMap<>();
        primeArray = PrimeNumberHelper.sieveOfEratosthenes(Long.toString(n).length() * 10);
        return dp(numStr, 0, 0, true);
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0172 extends EulerSolver {

    public JEulerProblem_0172(int problemNumber) {
        super(problemNumber);
    }
    private final int LIMIT = 18;
    private final int ALLOWED_DIGITS_COUNT = 3;
    private Map<String, Long> memo = new HashMap<>();
    private long memoHitCount = 0;

    @Override
    public String solve() {
        long ans;
        int[] map = new int[10];
        /**
         *  Every number from 0 to 9 is allowed 3 times.
         */
        for (int i = 0; i < 10; i++) {
            map[i] = ALLOWED_DIGITS_COUNT;
        }
        /**
         * We are calculating all combination with the first digit as 1,
         * hence subtract 1 for digit 1.
         */
        map[1] = ALLOWED_DIGITS_COUNT - 1;


        //long r = solve("1", map);
        long r = solve(1, map);
        //System.out.println(r);
        /**
         *  No. of combination with first digit 1 = No. of combination with any digit as first digit.
         *  so multiply by 9
         */
        ans = r * 9L;

        System.out.println("Memo hit count " + memoHitCount);

        return Long.toString(ans);
    }

    /**
     * Sweet old recursion, refer Problem 164
     */
    private long solve(int dataLen, int[] map) {
        if (dataLen == LIMIT) {
            return 1L;
        }

        /**
         * key takeaway - understand how repetitions can be cached.
         * eg: [ 2,3,3,3,3,3,3,3,3,3 ] would yield the same number of solutions regardless of which digit was used up.
         */
        String d = getData(map);
        if (memo.containsKey(d)) {
            memoHitCount++;
            return memo.get(d);
        }

        long result = 0L;

        for (int i=0; i<10; i++) {
            if (map[i] > 0) {
                map[i]--;
                result += solve(dataLen+1, map);
                map[i]++;
            }
        }

        d = getData(map);
        memo.put(d, result);

        return result;
    }

    private String getData(int[] map) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(map[i]);
        }
        return sb.toString();
    }

    private long solve(String data, int[] map) {
        if (data.length() == LIMIT) {
            System.out.println(data);
            return 1L;
        }

        long result = 0L;

        for (int i=0; i<10; i++) {
            if (map[i] > 0) {
                map[i]--;
                result += solve(data + i, map);
                map[i]++;
            }
        }

        return result;
    }

    @Override
    public String getProblemStatement() {
        return "How many 18-digit numbers n (without leading zeros) are there such that no digit occurs more than three times in n?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("problem", "164", "recursion", "digits", "generation", "memoization", "counting");
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0290 extends EulerSolver {

    public JEulerProblem_0290(int problemNumber) {
        super(problemNumber);
    }


    @Override
    public String solve() {
        int noOfDigits = 18;
        int NUM = 137;

        long ans = 0;

        /**
         *  Memo will store values in the following format
         *   Key1 -> Key2 -> Value
         *   firstDigits ->  Diff -> Count
         */
        Map<Integer, Map<Integer, Long>> memoCache = new HashMap<>();

        //Init the memoCache with values for 0 to 9;
        for (int digit = 0; digit<=9; digit++) {
            int v = digit * NUM;
            if (NumericHelper.sumOfDigits(v) == digit) {
                ans++;
            }
            int firstDigits = v/10;
            int lastDigit = v%10;
            int diff = digit - lastDigit;
            addToMemo(memoCache, firstDigits, diff, 1L);
        }

        //Process the remaining digits
        for (int digitCount = noOfDigits - 1; digitCount>=1; digitCount--) {
            Map<Integer, Map<Integer, Long>> tmpMemoCache = new HashMap<>();

            for (int digit = 0; digit <= 9; digit++) {
                //First digit should not be zero.
                if (digitCount == 1 && digit == 0) continue;

                int v = digit * NUM;

                for (Map.Entry<Integer, Map<Integer, Long>> mapEntry : memoCache.entrySet()) {
                    int carry = mapEntry.getKey();
                    int sumOfDigits = NumericHelper.sumOfDigits(v + carry);

                    for (Map.Entry<Integer, Long>  valueEntry : mapEntry.getValue().entrySet()) {
                        int prevDiff = valueEntry.getKey();
                        if (digit != 0 && sumOfDigits - digit - prevDiff == 0) {
                            ans += valueEntry.getValue();
                        }
                        int firstDigits = (v + carry) / 10;
                        int lastDigit = (v + carry) % 10;
                        int diff = (digit + prevDiff) - lastDigit;
                        addToMemo(tmpMemoCache, firstDigits, diff, valueEntry.getValue());
                    }
                }
            }
            System.out.println(digitCount + " : " + ans);

            memoCache = tmpMemoCache;

        }


        //20444710234716473
        return Long.toString(ans);
    }

    private void addToMemo(Map<Integer, Map<Integer, Long>> memo, int firstDigits, int diff, long count) {
        if (memo.containsKey(firstDigits) && memo.get(firstDigits).containsKey(diff)) {
            count += memo.get(firstDigits).get(diff);
        }
        memo.putIfAbsent(firstDigits, new HashMap<>());
        memo.get(firstDigits).put(diff, count);
    }

    @Override
    public String getProblemStatement() {
        return "How many integers 0 ≤ n < 10^18 have the property that the sum of the digits of n equals the sum of digits of 137n?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("problem 294", "dynamic programming", "dp", "count", "hard");
    }
}

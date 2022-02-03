package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0178 extends EulerSolver {

    public JEulerProblem_0178(int problemNumber) {
        super(problemNumber);
    }

    private final int LIMIT = 40;
    private Map<String, Long> memo = new HashMap<>();

    @Override
    public String solve() {
        long ans = 0;
        for (int digitCount = 1; digitCount<=LIMIT; digitCount++) {
            for (int startDigit = 1; startDigit <= 9; startDigit++) {
                BitSet pandigitalSet = new BitSet(10);
                pandigitalSet.set(startDigit);
                long r = solve(startDigit, 1, pandigitalSet, digitCount);
                ans += r;
            }
        }
        return Long.toString(ans);
    }

    private long solve(int lastDigit, int dataLen, BitSet pandigitalSet, int targetDigitLimit) {
        if (dataLen == targetDigitLimit) {
            //We found a number if all 10 bits are set
            if (pandigitalSet.cardinality() == 10) {
                //System.out.println(data);
                return 1L;
            } else {
                return 0L;
            }
        }

        //Use all the parameters as a hash to memo.
        String hash = lastDigit + Integer.toString(dataLen) + pandigitalSet.toString() + targetDigitLimit;
        if (memo.containsKey(hash)) {
            return memo.get(hash);
        }

        long result = 0;
        if (lastDigit > 0) {
            int currDigit = lastDigit - 1;
            BitSet clonedBitSet = (BitSet)pandigitalSet.clone();
            clonedBitSet.set(currDigit);
            result += solve(currDigit, dataLen + 1,clonedBitSet, targetDigitLimit);
        }
        if (lastDigit < 9) {
            int currDigit = lastDigit + 1;
            BitSet clonedBitSet = (BitSet)pandigitalSet.clone();
            clonedBitSet.set(currDigit);
            result += solve(currDigit, dataLen + 1,clonedBitSet, targetDigitLimit);
        }

        memo.put(hash, result);

        return result;
    }

    private long solveWithData(String data, BitSet pandigitalSet, int targetDigitLimit) {
        if (data.length() == targetDigitLimit) {
            //We found a number if all 10 bits are set
            if (pandigitalSet.cardinality() == 10) {
                //System.out.println(data);
                return 1L;
            } else {
                return 0L;
            }
        }

        long result = 0;
        int lastDigit = Integer.parseInt(data.substring(data.length()-1));
        if (lastDigit > 0) {
            int currDigit = lastDigit - 1;
            BitSet clonedBitSet = (BitSet)pandigitalSet.clone();
            clonedBitSet.set(currDigit);
            result += solveWithData(data + currDigit, clonedBitSet, targetDigitLimit);
        }
        if (lastDigit < 9) {
            int currDigit = lastDigit + 1;
            BitSet clonedBitSet = (BitSet)pandigitalSet.clone();
            clonedBitSet.set(currDigit);
            result += solveWithData(data + currDigit, clonedBitSet, targetDigitLimit);
        }

        return result;
    }

    @Override
    public String getProblemStatement() {
        return "Consider the number 45656.\n" +
                "It can be seen that each pair of consecutive digits of 45656 has a difference of one.\n" +
                "A number for which every pair of consecutive digits has a difference of one is called a step number.\n" +
                "A pandigital number contains every decimal digit from 0 to 9 at least once.\n" +
                "How many pandigital step numbers less than 1040 are there?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("problem", "164", "172", "recursion", "digits", "generation", "memoization", "counting");
    }
}

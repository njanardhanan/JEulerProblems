package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combination.MultiCombinationIterator;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0749 extends EulerSolver {
    public JEulerProblem_0749(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<Integer> n = new ArrayList<>();
        for(int i=9; i>=0; i--) {
            n.add(i);
        }

        long ans = 0;
        for(int i=2; i<=16; i++) {
            ans += getCombination(n, i);
        }
        return Long.toString(ans);
    }

    public long getCombination(List<Integer> nums, int d) {
        MultiCombinationIterator<Integer> combinationIterator = new MultiCombinationIterator(nums, d);
        long ans = 0;
        //Example, if d = 2, then minValue will be 10 and maxValue will be 100
        long minValue = (long)Math.pow(10, d-1);
        long maxValue = minValue * 10;
        while(combinationIterator.hasNext()) {
            List<Integer> combi = combinationIterator.next();
            for(int p=2; p<=17; p+=2) {
                long nps = 0L;
                long[] numMap = new long[10];
                for (int i : combi) {
                    nps += (long)Math.pow(i, p);
                    numMap[i] ++;
                }

                if (nps <= minValue) {
                    //skippedCount++;
                    continue;
                }

                if (nps >= maxValue) {
                    //skippedCount++;
                    break;
                }

                List<Integer> npsPlusOne = toDigits(nps + 1);
                if (isSameCombination(numMap.clone(), npsPlusOne)) {
                    if (checkNPS(nps + 1, p, nps)) {
                        System.out.println(nps + 1 + " " + d + " " + p);
                        ans += nps + 1;
                        break;
                    }
                }

                List<Integer> npsMinusOne = toDigits(nps - 1);
                if (isSameCombination(numMap, npsMinusOne)) {
                    if (checkNPS(nps - 1, p, nps)) {
                        System.out.println(nps - 1 + " " + d + " " + p);
                        ans += nps - 1;
                        break;
                    }
                }
            }
        }
        //System.out.println("Skipped count for " + d + " is " + skippedCount);
        return ans;
    }

    private List<Integer> toDigits(long n) {
        List<Integer> digits = new ArrayList<>();
        long x = n;
        while(x > 0) {
            long y = x % 10;
            digits.add((int)y);
            x = x/10;
        }
        return digits;
    }

    private boolean isSameCombination(long[] numMap, List<Integer> digits) {
        for (int n : digits) {
            numMap[n]--;
        }
        for (int i=0; i<10; i++) {
            if (numMap[i] != 0) return false;
        }
        return true;
    }

    private boolean checkNPS(long x, int p, long v) {
        long nps = 0;
        while(x > 0) {
            long y = x % 10;
            nps += (long)Math.pow(y, p);
            x = x/10;
        }
        return nps == v;
    }

    @Override
    public String getProblemStatement() {
        return "A positive integer N is a near power sum if there exists a positive integer K such that the sum of the Kth powers of the digits in its decimal representation is equal to either N+1 or N-1 . For example 35 is a near power sum number because" +
                "3^2 + 5^2 = 34.\n" +
                "Define S(d) to be the sum of all near power sum numbers of d digits or less. Then S(2)=110 and S(6) =2562701.\n" +
                "\n" +
                "Find S(16)." +
                "\n" +
                "https://projecteuler.net/problem=749";

        //13459471903176422
    }
}

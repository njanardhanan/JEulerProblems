package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0820 extends EulerSolver {

    public JEulerProblem_0820(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int target = 10000000;
        int ans = calculateS(target);
        return Integer.toString(ans);
    }

    /**
     * To find the nth digit in reciprocal (1/x).
     *
     * dn(1/x) = Floor [ (10 * (10^n-1 mod x) / x ]
     *
     * @param n - nth digit to find
     * @param x - reciprocal.
     * @return - nth digit
     */
    private int getNthDigit(int n, int x) {
        BigInteger bigN = BigInteger.valueOf(n-1);
        BigInteger bigX = BigInteger.valueOf(x);
        BigInteger b = BigInteger.TEN.modPow(bigN, bigX).multiply(BigInteger.TEN).divide(bigX);
        return b.intValue();
    }

    private int calculateS(int n) {
        int ans = 0;
        for(int k=1; k<=n; k++) {
            int v = getNthDigit(n, k);
            ans += v;
        }
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=820";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("reciprocal", "nth digit", "easy");
    }
}

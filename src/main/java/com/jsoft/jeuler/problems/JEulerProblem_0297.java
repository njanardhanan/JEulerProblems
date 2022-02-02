package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0297 extends EulerSolver {

    public JEulerProblem_0297(int problemNumber) {
        super(problemNumber);
    }

    private final long LIMIT = 100_000_000_000_000_000L;
    //84th Fibanocci number crosses 17 digits - 160500643816367088
    private final int MAX_FIB_NUM = 84;

    private long[] fib = new long[MAX_FIB_NUM + 1];
    private long[] fibSum = new long[MAX_FIB_NUM + 1];


    @Override
    public String solve() {
        generateFibonacci();
        generateZeckendorfSum();
        //-1 because the problem statement says less than limit.
        long ans = getSum(LIMIT - 1);
        return Long.toString(ans);
    }

    private long getSum(long limit) {
        int i = 0;
        while (fib[i+1] <= limit) {
            i++;
        }
        if (limit == fib[i]) {
            return fibSum[i];
        }

        long n = limit - fib[i];

        return fibSum[i] + n + getSum(n);
    }

    private void generateZeckendorfSum() {
        fibSum[1] = 1;
        fibSum[2] = 2;

        for(int i = 3; i <= MAX_FIB_NUM; i++) {
            fibSum[i] = fibSum[i - 1] + fibSum[i - 2] + fib[i - 2] - 1;
        }
    }

    private void generateFibonacci() {
        // First two number of fibonacci sequence
        fib[1] = 1;
        fib[2] = 2;

        for(int i = 3; i <= MAX_FIB_NUM; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
    }

    private long nearestSmallerEqFib(long n) {
        // Corner cases
        if (n == 0 || n == 1)
            return n;

        // Find the greatest Fibonacci Number smaller
        // than n.
        int f1 = 0, f2 = 1, f3 = 1;
        while (f3 <= n) {
            f1 = f2;
            f2 = f3;
            f3 = f1 + f2;
        }
        return f2;
    }

    private int ZeckendorfCount(long n) {
        int count = 0;
        while (n > 0) {
            // Find the greates Fibonacci Number smaller
            // than or equal to n
            long f = nearestSmallerEqFib(n);
            count++;

            // Reduce n
            n = n - f;
        }
        return count;
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=297\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("zeckendorf", "fibonacci");
    }
}

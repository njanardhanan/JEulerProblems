package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Map;

public class JEulerProblem_0745 extends EulerSolver {
    private final long LIMIT = (long)Math.pow(10,14);
    private final long MOD = 1_000_000_007L;
    public JEulerProblem_0745(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        if (S(10) != 24) System.out.println("Test failed");
        if (S(100) != 767) System.out.println("Test failed");
        if (S(1000) != 22606) System.out.println("Test failed");
        return Long.toString(S(LIMIT));
    }

    private long S(long n) {
        /***
        Formula:
        S(n) = Sum_{k=1..floor(sqrt(n))} k^2 * M(floor(n/k^2))

        Where M(n) is the number of square-free numbers <= n:
        M(n) = Sum_{k=1..floor(sqrt(n))} mobius(k) * floor(n/k^2)
        **/

        long ans = 0;
        for (long i = 1; i <= (long)Math.floor(Math.sqrt(n)); i++) {
            ans += NumericHelper.mulmod(i * i, NumericHelper.noOfSquareFreeNumber((long)Math.floor(n/(i*i))), MOD);
            ans %= MOD;
        }
        return ans;
    }

    private long S_Slow(long n) {
        long ans = 0;
        for (long x=1; x<=n; x++) {
            ans += g(x);
            ans %= MOD;
        }
        return ans;
    }

    private long g(long n) {
        long r = 1;
        Map<Long, Integer> primeFactors = NumericHelper.getPrimeFactors(n);
        for(Map.Entry<Long, Integer> e : primeFactors.entrySet()) {
            if (e.getValue() % 2 == 1) {
                r *= e.getKey();
            }
        }
        return n/r;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=745\n"
                + "https://oeis.org/A008833";
    }
}

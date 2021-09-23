package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0407 extends EulerSolver {

    public JEulerProblem_0407(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        final int LIMIT = 10000000;
        /**
         * https://oeis.org/A182665
         *
         * Explanation:
         *  a^2 = a (mod n)
         *  a^2 - a = 0 (mod n)
         *  a(a - 1) = 0 (mod n)
         *
         * 1. Factorize all the integers up to 10^7 by sieving.
         * 2. For each integer n:
         *     if n is a prime or prime power, then a=1;
         *      otherwise n is a product of two or more distinct primes, so:
         *     for each way to factorize n=uv with u, v co-prime.
         *       find uw = 1 mod v.
         *       i.e., w = u^-1 mod v
         *     a is the maximum of uw for all such uv.
         *
         */
        Map<Integer, List<Integer>> factor = factorize(LIMIT);
        long ans = 0;
        for(int i=2; i<=LIMIT; i++) {
            List<Integer> f = factor.get(i);
            //f will null if i is a prime
            //f.size will be 1 if i is some power of prime (p^k)
            if (f == null || f.size() == 1) {
                ans += 1;
                continue;
            }
            int maxUW = 0;
            for(int j=1; j<f.size(); j++ ) {
                for(List<Integer> c : Generator.combination(f).simple(j)) {
                    int u = c.stream().reduce(1, (a,b) -> a*b);
                    int v = i / u;
                    //Find w
                    int w = NumericHelper.multiplicativeInverse(u, v);
                    if (maxUW < u*w) {
                        maxUW = u*w;
                    }
                }
            }
            //System.out.println(i  + " : " + maxUW );
            ans += maxUW;
        }
        return Long.toString(ans);
    }

    /**
     * This method will factorize from 2 to n.
     * Example:
     *  6  : 2,3
     *  12 : 4,3
     *  90 : 2,9,5
     */
    private Map<Integer, List<Integer>> factorize(int n) {
        Map<Integer, List<Integer>> factors = new HashMap<>();
        for(int x = 2; x <= n; x++) {
            if (factors.containsKey(x)) continue;

            for(int p=x+x; p<=n; p+=x) {
                int j = p;
                int k = 1;
                while (j%x == 0) {
                    j /= x;
                    k *= x;
                }
                if (factors.containsKey(p)) {
                    factors.get(p).add(k);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(k);
                    factors.put(p, list);
                }
            }
        }
        return factors;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=407\n\n" +
                "If we calculate a^2 mod 6 for 0 ≤ a ≤ 5 we get: 0,1,4,3,4,1.\n" +
                "\n" +
                "The largest value of a such that a^2 ≡ a mod 6 is 4.\n" +
                "Let's call M(n) the largest value of a < n such that a^2 ≡ a (mod n).\n" +
                "So M(6) = 4.\n" +
                "\n" +
                "Find ∑M(n) for 1 ≤ n ≤ 10^7.";
    }
}

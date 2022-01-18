package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

public class JEulerProblem_0643 extends EulerSolver {

    private int MOD = 1_000_000_007;

    public JEulerProblem_0643(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * If gcd(a,b) = 1
         * then gcd(xa, xb) = x
         *
         * http://oeis.org/A002088
         *
         */

        //bruteForce(20);

        long LIMIT = (long)Math.pow(10,11);
        long ans = solve(LIMIT, MOD);


        //968274154
        return Long.toString(ans);

        /**
         * Another method:
         * System.out.println(f(LIMIT));
         */
    }

    private int bruteForce(int n) {
        int ans = 0;
        for(int q=2; q<=n; q++) {
            int phi = NumericHelper.phi(q);
            int maxCombination = (int)(Math.log(n/q) / Math.log(2));
            ans += (phi * maxCombination);
            System.out.println(q + " => " + phi + " * " + maxCombination + " = " + ans);
        }
        return ans;
    }

    private long solve(long n, long mod) {
        long ans = 0;
        long e = n/2;
        long s = e/2;
        long multiplier = 1;
        long maxMultiplier = (long)(Math.log(n/2) / Math.log(2));
        while(multiplier <= maxMultiplier) {
            long r = (totientSummatory(e) - totientSummatory(s))%mod * multiplier%mod;
            r %= mod;
            ans += r;
            ans %= mod;
            e = s;
            s = e/2;
            multiplier++;
        }
        return ans;
    }

    /**
     * https://mathproblems123.wordpress.com/2018/05/10/sum-of-the-euler-totient-function/
     */
    Map<Long, Long> anotherMemo = new HashMap<>();
    private long totientSummatory(long n) {
        if (n==1) {
            return 1;
        }
        if(anotherMemo.containsKey(n)) {
            return anotherMemo.get(n);
        }

        long sq = (long)Math.sqrt(n);
        long sum = ((n%MOD)*(n%MOD+1)/2- LongStream.range(2,sq+1).map(m->totientSummatory(
                (long)Math.floor(n/m))).reduce(0L, (a,b)->(a+b)%MOD)-
                LongStream.range(1,sq+1).filter(d->d!=(long)Math.floor(n/d)).
                        map(d->((((long)Math.floor(n/d)-(long)Math.floor(n/(d+1)))%MOD)*
                                totientSummatory(d))%MOD).reduce(0L, (a,b)->(a+b)%MOD))%MOD;
        anotherMemo.putIfAbsent(n, sum);
        return sum;
    }


    private long f(long n) {
        long limit = (long)Math.floor(Math.log(n)/Math.log(2));
        long ans = 0;
        for(long k=1; k<=limit; k++) {
            long v = totientSummatory(n/(long)Math.pow(2,k)) - 1;
            ans = (ans + v)%MOD;
        }
        return ans%MOD;

//        return (int)(LongStream.range(1, (long)Math.floor(Math.log(n)/Math.log(2))+1).map(
//                k->totientSummatory(n/(long)Math.pow(2,k))-1).reduce(0L, (a,b)->(a+b)%MOD));
    }

    @Override
    public String getProblemStatement() {
        /**
         * This problem is related to
         * https://projecteuler.net/problem=351
         * https://projecteuler.net/problem=388
         * https://projecteuler.net/problem=512
         * https://projecteuler.net/problem=625
         *
         *
         * https://math.stackexchange.com/questions/316376/how-to-calculate-these-totient-summation-sums-efficiently
         * https://mathproblems123.wordpress.com/2018/05/10/sum-of-the-euler-totient-function/
         */
        return "https://projecteuler.net/problem=643\n\n" +
                "<p>Two positive integers $a$ and $b$ are <em>2-friendly</em> when $\\gcd(a,b) = 2^t, t&gt;0$. For example, 24 and 40 are 2-friendly because $\\gcd(24,40) = 8 = 2^3$ while 24 and 36 are not because $\\gcd(24,36) = 12 = 2^2\\cdot 3$ not a power of 2.</p>\n" +
                "\n" +
                "<p>Let $f(n)$ be the number of pairs, $(p,q)$, of positive integers with $1\\le p\\lt q\\le n$ such that $p$ and $q$ are 2-friendly. You are given $f(10^2) = 1031$ and $f(10^6) = 321418433$ modulo $1\\,000\\,000\\,007$.</p>\n" +
                "\n" +
                "<p>Find $f(10^{11})$ modulo $1\\,000\\,000\\,007$.</p>";
    }
}

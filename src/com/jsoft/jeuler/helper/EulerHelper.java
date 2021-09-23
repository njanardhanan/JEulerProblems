package com.jsoft.jeuler.helper;

import java.util.Map;

public class EulerHelper {

    /**
     * https://mathproblems123.wordpress.com/2018/05/10/sum-of-the-euler-totient-function/
     *
     */
    public static long totientSummatory(long n, Map<Long, Long> memo) {
        if (n==1) {
            return 1;
        }
        if(memo.containsKey(n)) {
            return memo.get(n);
        }

        long sq = (long)Math.sqrt(n);
        long x = n * (n+1)/2;
        long y = 0;
        for(long m=2; m<=sq; m++) {
            y += totientSummatory((long)Math.floor(n/m), memo);
        }
        long z = 0;
        for(long d=1; d<=sq; d++) {
            if (d == (long)Math.floor(n/d)) continue;

            z += ((long) Math.floor(n/d) - (long) Math.floor(n/(d+1))) * totientSummatory(d, memo);
        }

        long sum = x - y - z;

        memo.putIfAbsent(n, sum);
        return sum;
    }
}

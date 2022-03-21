package com.jsoft.jeuler.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimeCountingTest {
    /**
     * def tabulate_pis(N):
     *     if N <= 1:
     *       raise ValueError(N)
     *     v = int(N ** 0.5)
     *     smalls = [i - 1 for i in range(v + 1)]
     *     larges = [0 if i == 0 else N // i - 1 for i in range(v + 1)]
     *
     *     for p in range(2, v + 1):
     *       if smalls[p - 1] == smalls[p]:
     *         continue
     *
     *       p_cnt = smalls[p - 1]
     *       q = p * p
     *       end = min(v, N // q)
     *       for i in range(1, end + 1):
     *         d = i * p
     *         if d <= v:
     *           larges[i] -= larges[d] - p_cnt
     *         else:
     *           larges[i] -= smalls[N // d] - p_cnt
     *       for i in range(v, q - 1, -1):
     *         smalls[i] -= smalls[i // p] - p_cnt
     *     return smalls, larges
     */


    public static void primeCount(long N) {
        long[] smalls;
        long[] larges;
        int v = (int)Math.sqrt(N);
        smalls = new long[v+1];
        larges = new long[v+1];
        for(int i=0; i<=v; i++) {
            smalls[i] = (long)i-1;
            if (i==0) {
                larges[i] = 0L;
            } else {
                larges[i] = N/i -1;
            }
        }

        System.out.println(Arrays.toString(smalls));
        System.out.println(Arrays.toString(larges));

        for(int p=2; p<=v; p++) {
            if (smalls[p - 1] == smalls[p]) {
                continue;
            }

            long pCount = smalls[p - 1];
            long q = p * p;
            long end = (v < N/q) ? v : N/q;

            for(int i=1; i<=end; i++) {
                int d = i * p;
                if (d <= v) {
                    larges[i] -= larges[d] - pCount;
                } else {
                    larges[i] -= smalls[(int)N/d] - pCount;
                }
            }

            for(int i=v; i>=q; i--) {
                smalls[i] -= smalls[i / p] - pCount;
            }
        }

        System.out.println(Arrays.toString(smalls));
        System.out.println(Arrays.toString(larges));
    }

    public static Map<Long, Long> primeCountWithCache(long N) {
        long v = (long)Math.sqrt(N);
        Map<Long, Long> smalls = new HashMap<>();
        Map<Long, Long> larges = new HashMap<>();
        for(long i=0L; i<=v; i++) {
            smalls.put(i, i-1);
            if (i==0L) {
                larges.put(i, 0L);
            } else {
                larges.put(i, N/i -1);
            }
        }

        System.out.println(smalls);
        System.out.println(larges);

        for(long p=2; p<=v; p++) {
            if (smalls.get(p - 1) == smalls.get(p)) {
                continue;
            }

            long pCount = smalls.get(p - 1);
            long q = p * p;
            long end = (v < N/q) ? v : N/q;

            for(long i=1; i<=end; i++) {
                long d = i * p;
                if (d <= v) {
                    long l = larges.get(i);
                    l -= larges.get(d) - pCount;
                    larges.put(i, l);
                } else {
                    long l = larges.get(i);
                    l -= smalls.get(N/d) - pCount;
                    larges.put(i, l);
                }
            }

            for(long i=v; i>=q; i--) {
                long s = smalls.get(i);
                s -= smalls.get(i/p) - pCount;
                smalls.put(i, s);
            }
        }

        System.out.println("Small : " + smalls);
        System.out.println("Large : " + larges);

        for(Map.Entry<Long, Long> e : larges.entrySet()) {
            if (e.getKey() == 0) continue;
            smalls.putIfAbsent(N/e.getKey(), e.getValue());
        }
        return smalls;
    }
    public static void main(String[] args) {
        primeCount(100);
        System.out.println("Done");
        Map<Long, Long> smalls = primeCountWithCache(100);
        System.out.println(smalls);
    }
}

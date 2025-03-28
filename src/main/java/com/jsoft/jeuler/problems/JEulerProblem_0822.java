package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0822 extends EulerSolver {

    public JEulerProblem_0822(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        BigInteger MOD = BigInteger.valueOf(1234567891L);

        long n = 10_000L;
        long m = 10_000_000_000_000_000L;
        long targetExpo = m / (n-1);
        targetExpo--;
        System.out.println("Target Expo : " + targetExpo);
        long targetM = m - ((n-1) * targetExpo);
        System.out.println("Target M : " + targetM);

        Map <Integer, Long> map = new HashMap<>();
        for (int i=2; i<=n; i++) {
            map.put(i, targetExpo);
        }
        for (int i=1; i<=targetM; i++) {
            int smallValue = 0;
            long smallExponent = 0;
            for(Map.Entry<Integer, Long> e : map.entrySet()) {
                if (smallValue == 0) {
                    smallValue = e.getKey();
                    smallExponent = e.getValue();
                } else {
                    if(isSmaller(e.getKey(), e.getValue(), smallValue, smallExponent)) {
                        smallValue = e.getKey();
                        smallExponent = e.getValue();
                    }
                }
            }
            long v = map.get(smallValue);
            map.put(smallValue, v+1L);

        }
        BigInteger ans = BigInteger.ZERO;
        for(Map.Entry<Integer, Long> e : map.entrySet()) {
            ans = ans.add(powerPowMod(e.getKey(), 2, e.getValue(), MOD));
        }
        return ans.mod(MOD).toString();
    }

    /**
     * powerPowMod using https://en.wikipedia.org/wiki/Euler%27s_theorem
     *
     * Example:
     * If a number v is squared 2^k times, then we need to compute:
     * => v ^ (2^k) mod 1234567891L
     * where k is greater than 10^12. We can do that using Euler's Theorem. Since
     * 1234567891L is prime
     * phi(1234567891L) = 1234567890L
     * and we have
     *
     * v ^ (2^k) mod 1234567891L
     * => v ^ ( (2^k) mod phi(1234567891L) ) mod 1234567891L
     * => v ^ ( (2^k) mod 1234567890L ) mod 1234567891L
     *
     * Note : MOD must be a prime, for this to work.
     *
     */
    private BigInteger powerPowMod(int v, int y, long k, BigInteger MOD) {
        //phi(p) = p-1
        BigInteger pMod = MOD.subtract(BigInteger.ONE);
        BigInteger power = BigInteger.valueOf(y).modPow(BigInteger.valueOf(k), pMod);
        BigInteger ans = BigInteger.valueOf(v).modPow(power, MOD);
        return ans;
    }

    private boolean isSmaller(int v, long e, int sv, long se) {
        /**
         *   a^(2^b) < x^(2^y)
         *   Now taking logs...
         *   (2^b) * log(a) < (2^y) * log(x)
         *   taking logs again...
         *   b*log(2) + log(log(a)) < y*log(2) + log(log(b))
         */
        double left = (Math.log10(2) * e) + Math.log10(Math.log10(v));
        double right = (Math.log10(2) * se) + Math.log10(Math.log10(sv));

        return left < right;
    }


    public String solveBruteForce() {
        long[] n = {2,3,4,5,6,7,8,9,10};
        long[] p = {0,0,0,0,0,0,0,0,0};
        for (int i=1; i<=30; i++) {
            long small = n[0];
            int smallIndex = 0;
            for(int j=1; j<9; j++) {
                if (small > n[j]) {
                    small = n[j];
                    smallIndex = j;
                }
            }

            n[smallIndex] *= n[smallIndex];
            p[smallIndex] += 1;
            System.out.printf("%d : %s\n", i, Arrays.toString(n));
        }
        System.out.println();
        System.out.println(Arrays.toString(n));
        System.out.println(Arrays.toString(p));
        return "0";
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=822";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("log", "log of log", "euler's theorem", "fermet's little theorem", "powerPowMod");
    }
}

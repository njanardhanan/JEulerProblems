package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class JEulerProblem_0521 extends EulerSolver {

    public JEulerProblem_0521(int problemNumber) {
        super(problemNumber);
    }

    private long LIMIT = (long)Math.pow(10, 12);
    private long MOD = (long)Math.pow(10, 9);
    private BigInteger BIG_MOD = BigInteger.TEN.pow(9);

    @Override
    public String solve() {
        long ans = solveByAlgo();
        return Long.toString(ans);
    }

    public long solveByAlgo() {
        long ans = 0;
        int sqrtNum = (int)Math.sqrt(LIMIT);
        List<Long> V = LongStream.range(1, sqrtNum+1).map(i -> LIMIT/i).boxed().collect(Collectors.toList());
        long lastItem = V.get(V.size() - 1);
        for (long i = lastItem-1; i > 0; --i)
            V.add(i);

        Map<Long, BigInteger> S = new HashMap<>();
        for(long n : V) {
            BigInteger b = BigInteger.valueOf(n).multiply(BigInteger.valueOf(n+1)).divide(BigInteger.valueOf(2)).subtract(BigInteger.ONE);
            S.put(n, b);
        }

        Map<Long, Long> C = new HashMap<>();
        for(long n : V) {
            C.put(n, n-1);
        }

        for (long p = 2; p <= sqrtNum; p++) {
            if (S.get(p).compareTo(S.get(p-1)) > 0) {   //p is prime
                long cp = C.get(p-1);            // number of primes smaller than p
                BigInteger sp = S.get(p-1);      // sum of primes smaller than p
                long p2 = p*p;
                for(long v : V) {
                    if (v < p2) break;

                    long count = C.get(v);
                    count -= C.get(v/p) - cp;
                    C.put(v, count);

                    BigInteger sum = S.get(v);
                    BigInteger vp = S.get(v/p).subtract(sp);
                    sum = sum.subtract(BigInteger.valueOf(p).multiply(vp));
                    S.put(v, sum);

                    if (v == LIMIT) {
                        ans += BigInteger.valueOf(p).multiply(BigInteger.valueOf(C.get(v/p) - cp)).mod(BIG_MOD).longValue();
                        //ans %= MOD;
                    }
                }
            }
        }

        //System.out.println(S.get(LIMIT));
        //System.out.println(C.get(LIMIT));
        //System.out.println(ans);

        return (ans + S.get(LIMIT).mod(BIG_MOD).longValue()) % MOD;
    }

    public String solveByInclusionExclusion() {
        System.out.println("NOTE, THIS SOLUTION IS VERY SLOW, WILL TAKE AROUND 4 HOURS TO FINISH IN A GOOD DECENT COMPUTER");
        int sqrtLimit = (int)Math.sqrt(LIMIT);
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(sqrtLimit);
        System.out.println("Total primes : " + primes.size());
        //calculate combination for prime 2
        long prime2Combination = LIMIT / 2;

        //calculate combination for prime 3
        // |A U B| = |A| + |B| - |A intersection B|
        long prime3Combination = LIMIT / 3;
        prime3Combination -= LIMIT / 6;

        long ans = (2 * prime2Combination)%MOD + (3 * prime3Combination)%MOD;

        SortedSet<Long> excludeList = new TreeSet<>();
        excludeList.add(2L);
        excludeList.add(3L);
        SortedSet<Long> includeList = new TreeSet<>();
        includeList.add(2L * 3L);

        //Include or exclude
        //Calculate combination from prime 5 onwards.
        //  - |A intersection B|
        /**
         *   |A U B U C| =  |A| + |B| + |C|  //Include
         *                 -|A intersect B| - |A intersect C| - |B intersect C| //Exclude
         *                 + |A intersect B intersect C| //Include
         *
         *   Example for prime 7
         *   only |p7| =  |p7|
         *               -|p2p7| - |p3p7| - |p5p7|
         *               +|p2p3p7| + |p2p5p7| + |p3p5p7|
         *               - |p2p3p5p7|
         *
         */
        int status = 100;
        for(int i=2; i<primes.size(); i++) {
            if (i%status == 0) {
                System.out.printf("(%d, %d, %d)\n",excludeList.size(), includeList.size(), ans);
                System.out.println("Reached : " + i);
                //status *= 10;
            }
            int currPrime = primes.get(i);
            int nextPrime = (i < primes.size() - 1) ? primes.get(i+1) : currPrime;
            SortedSet<Long> tmpExcludeList = new TreeSet<>();
            List<Long> tmpExcludeListRemove = new ArrayList<>();
            SortedSet<Long> tmpIncludeList = new TreeSet<>();
            List<Long> tmpIncludeListRemove = new ArrayList<>();
            long curCombination = LIMIT / currPrime;
            long nextComination = LIMIT / nextPrime;
            long actualCombination = LIMIT / currPrime;

            //Exclude
            for (long n : excludeList) {
                if ((curCombination/n) == 0) {
                    tmpExcludeListRemove.add(n);
                }
                actualCombination -= (curCombination/n);
                if (nextComination/(currPrime * n) > 0) {
                    tmpIncludeList.add(currPrime * n);
                }
            }

            //Include
            for (long n : includeList) {
                if ((curCombination/n) == 0) {
                    tmpIncludeListRemove.add(n);
                }
                actualCombination += (curCombination/n);
                actualCombination %= MOD;
                if (nextComination/(currPrime * n) > 0) {
                    tmpExcludeList.add(currPrime * n);
                }
            }
            ans += (currPrime * actualCombination)%MOD;
            ans %= MOD;

            tmpExcludeList.add((long)currPrime);
            excludeList.removeAll(tmpExcludeListRemove);
            excludeList.addAll(tmpExcludeList);
            includeList.removeAll(tmpIncludeListRemove);
            includeList.addAll(tmpIncludeList);
        }

        ans += getPrimeSum(sqrtLimit, LIMIT).mod(BigInteger.valueOf(MOD)).longValue();

        System.out.println();
        System.out.println(ans%MOD);
        return "0";
    }

    public String solveBySieve() {
        System.out.println("NOTE, THIS SOLUTION IS VERY SLOW, WILL TAKE MANY DAYS TO FINISH IN A GOOD DECENT COMPUTER");

        int ARRAY_LIMIT = (int)Math.pow(10, 8);
        int sqrtLimit = (int)Math.sqrt(LIMIT);
        boolean[] smpf = new boolean[ARRAY_LIMIT + 1];
        List<Integer> primes = new ArrayList<>();
        int ans = 0;
        for (int i=2; i<=sqrtLimit; i++) {
            if (smpf[i]) continue;

            for(long j=i; j<=LIMIT; j+=i) {
                if (j<=ARRAY_LIMIT) {
                    int jj = (int)j;
                    if (smpf[jj]) continue;
                    smpf[jj] = true;
                    ans += i;
                    ans %= MOD;
                } else {
                    boolean isValid = true;
                    for (int p : primes) {
                        if (j%p == 0) {
                            isValid = false;
                            break;
                        }
                    }
                    if (isValid) {
                        ans += i;
                        ans %= MOD;
                    }
                }
            }

            //i must be a prime.
            primes.add(i);
        }
        System.out.println(ans);
        BigInteger b = getPrimeSum(sqrtLimit, LIMIT).mod(BigInteger.valueOf(MOD));
        System.out.println(b);
        System.out.println(ans + b.intValue());
        return "0";
    }

    private BigInteger getPrimeSum(long from, long to) {
        //https://www.wolframalpha.com/input/?i=sum+of+all+the+primes+%3C%3D+10%5E1
        Map<Long, BigInteger> sumOfPrimes = new HashMap<>();
        sumOfPrimes.put((long)Math.pow(10, 1), new BigInteger("17"));
        sumOfPrimes.put((long)Math.pow(10, 2), new BigInteger("1060"));
        sumOfPrimes.put((long)Math.pow(10, 3), new BigInteger("76127"));
        sumOfPrimes.put((long)Math.pow(10, 4), new BigInteger("5736396"));
        sumOfPrimes.put((long)Math.pow(10, 5), new BigInteger("454396537"));
        sumOfPrimes.put((long)Math.pow(10, 6), new BigInteger("37550402023"));
        sumOfPrimes.put((long)Math.pow(10, 7), new BigInteger("3203324994356"));
        sumOfPrimes.put((long)Math.pow(10, 8), new BigInteger("279209790387276"));
        sumOfPrimes.put((long)Math.pow(10, 9), new BigInteger("24739512092254535"));
        sumOfPrimes.put((long)Math.pow(10, 10), new BigInteger("2220822432581729238"));
        sumOfPrimes.put((long)Math.pow(10, 11), new BigInteger("201467077743744681014"));
        sumOfPrimes.put((long)Math.pow(10, 12), new BigInteger("18435588552550705911377"));

        return sumOfPrimes.get(to).subtract(sumOfPrimes.get(from));
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=521\n\n" +
                "Let smpf(n) be the smallest prime factor of n.\n" +
                "smpf(91)=7 because 91=7×13 and smpf(45)=3 because 45=3×3×5.\n" +
                "Let S(n) be the sum of smpf(i) for 2 ≤ i ≤ n.\n" +
                "E.g. S(100)=1257.\n" +
                "\n" +
                "Find S(10^12) mod 10^9.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime sum", "prime counting", "inclusion-exclusion", "slowmode", "wolframalpha");
    }
}

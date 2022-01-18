package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0521 extends EulerSolver {

    public JEulerProblem_0521(int problemNumber) {
        super(problemNumber);
    }

    private long LIMIT = (long)Math.pow(10, 12);
    private long MOD = (long)Math.pow(10, 9);

    @Override
    public String solve() {
        System.out.println("NOT SOLVED YET, THIS IS TOO SLOW");
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

        //Include or exclude
        boolean include = false;
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
        int status = 10;
        for(int i=2; i<primes.size(); i++) {
            if (i%status == 0) {
                System.out.println("Reached : " + i);
                status *= 10;
            }
            include = false;
            int currPrime = primes.get(i);
            long curCombination = LIMIT / currPrime;
            long tmpCombination = curCombination;
            for(int len = 1; len <= i; len++) {
                for (List<Integer> c : Generator.combination(primes.subList(0, i)).simple(len)) {
                    long tmpValue = tmpCombination;
                    for (int x : c) {
                        tmpValue /= x;
                    }
                    if (include) {
                        curCombination += tmpValue;
                    } else {
                        curCombination -= tmpValue;
                    }
                }
                include = !include;
            }
            ans += (currPrime * curCombination)%MOD;
            ans %= MOD;
        }

        ans += getPrimeSum(sqrtLimit, LIMIT).mod(BigInteger.valueOf(MOD)).longValue();

        System.out.println(ans%MOD);
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
}

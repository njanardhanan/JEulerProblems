package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.HashMapPersistence;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0708 extends EulerSolver {

    public JEulerProblem_0708(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long N = 100_000_000_000_000L;
        int maxK = (int)Math.floor(Math.log(N) / Math.log(2));
        System.out.println("Max K: " + maxK);
        int SQRT_N = (int)Math.sqrt(N);
        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList(SQRT_N + 100); //with some buffer

        //Map<Long, Long> primeCount = PrimeNumberHelper.sievePrimeCounts(N);
        //HashMapPersistence.saveHashMap(primeCount, "data/primeCountUpto1e14.data");
        Map<Long, Long> primeCount = HashMapPersistence.loadHashMap("data/primeCountUpto1e14.data");

        //long N = 1_00_000_000L;
        //Map<Long, Long> primeCount = PrimeNumberHelper.sievePrimeCounts(N);

        System.out.printf("K = %d => %d%n", 1, primeCount.get(N));
        long ans = 1L; //As per the problem f(1)=1

        //K=1
        ans += replacePrimesWithTwo(1, primeCount.get(N));

        for (int k = 2; k <= maxK; k++) {
            long tmpAns = calculateKAlmostPrimeCount(1, k, N, primeCount, primes, 0);
            if (tmpAns == 0L) {
                break;
            }
            tmpAns = replacePrimesWithTwo(k, tmpAns);
            ans += tmpAns;
            System.out.printf("K = %d => %d%n", k, tmpAns);
        }

        System.out.printf("Ans => %d%n", ans);
        return "0";
    }

    //https://mathworld.wolfram.com/AlmostPrime.html
    private long calculateKAlmostPrimeCount(int currK, int targetK, long n, Map<Long, Long> primeCount, List<Long> primes, int primeIndex) {
        if (currK == targetK) {
            return primeCount.get(n) - (primeIndex + 1) + 1;
        }

        long ans = 0;
        for (int i = primeIndex; i < primes.size(); i++) {
            long currN = n/primes.get(i);
            if (currN < primes.get(i)) {
                return ans;
            }
            ans += calculateKAlmostPrimeCount(currK+1, targetK, currN, primeCount, primes, i);
        }
        return ans;
    }

    private long replacePrimesWithTwo(int k, long x) {
        long ans = (long)Math.pow(2, k);
        ans = ans * x;
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=708" +
                "<p>A positive integer, $n$, is factorised into prime factors. We define $f(n)$ to be the product when each prime factor is replaced with $2$. In addition we define $f(1)=1$.</p>\n" +
                "<p>For example, $90 = 2\\times 3\\times 3\\times 5$, then replacing the primes, $2\\times 2\\times 2\\times 2 = 16$, hence $f(90) = 16$.</p> \n" +
                "<p>Let $\\displaystyle S(N)=\\sum_{n=1}^{N} f(n)$. You are given $S(10^8)=9613563919$.</p> \n" +
                "<p>Find $S(10^{14})$.</p>\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime counting", "prime summation");
    }
}

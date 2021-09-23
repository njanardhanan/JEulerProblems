package com.jsoft.jeuler.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PrimeNumberHelper {

    public static boolean checkPrime(long n) {
        BigInteger b = BigInteger.valueOf(n);
        return b.isProbablePrime(1);
    }

    public static long nextPrime(long n) {
        BigInteger b = BigInteger.valueOf(n);
        return b.nextProbablePrime().longValue();
    }

    /**
     * Counts the number of prime from 2 to n.
     * @param n - Upper limit inclusive.
     * @return - Number of prime between 2 to n.
     */
    public static long primeCount(long n) {
        if(n<1) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        /**
         * http://mathworld.wolfram.com/PrimeCountingFunction.html
         * key is x
         * value is primeCount for 10^x
         */
//        Map<Integer, Long> primeCountCache = new HashMap<>();
//        primeCountCache.put( 1,                  4L);
//        primeCountCache.put( 2,                 25L);
//        primeCountCache.put( 3,                168L);
//        primeCountCache.put( 4,              1_229L);
//        primeCountCache.put( 5,              9_592L);
//        primeCountCache.put( 6,             78_498L);
//        primeCountCache.put( 7,            664_579L);
//        primeCountCache.put( 8,          5_761_455L);
//        primeCountCache.put( 9,         50_847_534L);
//        primeCountCache.put(10,        455_052_511L);
//        primeCountCache.put(11,      4_118_054_813L);
//        primeCountCache.put(12,     37_607_912_018L);

        long count = 1;
        BigInteger limit = BigInteger.valueOf(n);
        BigInteger curPrime = BigInteger.valueOf(2L);
        while(curPrime.compareTo(limit) <= 0) {
            curPrime = curPrime.nextProbablePrime();
            count++;
        }
        return count;
    }

    public static boolean isPrime (int a) {
        for(int b=2; b<=(int)Math.sqrt(a); b++) {
            if(a%b==0) {
                return false;
            }
        }
        return true;
    }
    public static boolean isPrime (long a) {
        for(int b=2; b<=(long)Math.sqrt(a); b++) {
            if(a%b==0) {
                return false;
            }
        }
        return true;
    }

    public static List<Long> sieveLargePrimes(long from, long to) {
        return LongStream.rangeClosed(from, to).filter(v ->
                BigInteger.valueOf(v).isProbablePrime(1)).boxed().collect(Collectors.toList());
    }

    public static boolean[] sieveOfEratosthenes(int n)
    {
        // Create a boolean array "prime[0..n]" and initialize
        // all entries it as true. A value in prime[i] will
        // finally be false if i is Not a prime, else true.
        boolean prime[] = new boolean[n+1];
        for(int i=0;i<n;i++)
            prime[i] = true;

        prime[0] = prime[1] = false;

        for(int p = 2; p*p <=n; p++) {
            // If prime[p] is not changed, then it is a prime
            if(prime[p] == true) {
                // Update all multiples of p
                for(int i = p*2; i <= n; i += p)
                    prime[i] = false;
            }
        }

        return prime;

    }

    public static List<Integer> sieveOfEratosthenesAsList(int n) {
        boolean[] primes = sieveOfEratosthenes(n);
        List<Integer> primeList = new ArrayList();
        for(int i=0; i<n; i++) {
            if(primes[i]) {
                primeList.add(i);
            }
        }
        return primeList;
    }

    public static List<Long> sieveOfEratosthenesAsLongList(int n) {
        boolean[] primes = sieveOfEratosthenes(n);
        List<Long> primeList = new ArrayList();
        for(int i=0; i<n; i++) {
            if(primes[i]) {
                primeList.add((long)i);
            }
        }
        return primeList;
    }
}

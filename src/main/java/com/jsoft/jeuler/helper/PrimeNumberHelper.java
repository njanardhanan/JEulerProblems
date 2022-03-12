package com.jsoft.jeuler.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PrimeNumberHelper {

    public static boolean checkPrime(long n) {
        BigInteger b = BigInteger.valueOf(n);
        return b.isProbablePrime(1);
    }

    public static boolean checkPrime(long n, int certainty) {
        BigInteger b = BigInteger.valueOf(n);
        return b.isProbablePrime(certainty);
    }

    public static long nextPrime(long n) {
        BigInteger b = BigInteger.valueOf(n);
        return b.nextProbablePrime().longValue();
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
        if (a < 2) return false;
        for(int b=2; b<=(long)Math.sqrt(a); b++) {
            if(a%b==0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrime(long n, List<Long> primes) {
        long sqrt = (long)Math.sqrt(n);
        for (long p : primes) {
            if (p > sqrt) break;
            if (n % p == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Long> sieveLargePrimes(long from, long to) {
        return LongStream.rangeClosed(from, to).filter(v ->
                BigInteger.valueOf(v).isProbablePrime(5)).boxed().collect(Collectors.toList());
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

    public static boolean[] sieveByRange(long low, long high, List<Integer> primeSieves) {
        if (high - low + 1 > (long)Integer.MAX_VALUE) {
            return new boolean[1];
        }

        int range = (int)(high - low + 1);
        boolean[] primes = new boolean [range];
        Arrays.fill(primes,true);

        for(long i : primeSieves) {
            long x = (low/i);

            if(x <= 1) {
                x = i+i;
            } else if(low%i != 0) {
                x = (x*i)+i;
            } else {
                x = (x*i);
            }

            for(long j=x;j<=high;j=j+i) {
                int index = (int)(j - low);
                primes[index] = false;
            }
        }
        return primes;
    }

    public static Set<Long> primesFromSieveByRange(long low, long high, boolean[] p) {
        Set<Long> primes = new HashSet<>();
        for(long i=low; i<=high; i++) {
            int index = (int)(i - low);
            if (p[index]) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     *
     * https://projecteuler.net/thread=10;page=5
     *
     * Solution by Lucy_Hedgehog
     *
     * Here is a solution that is more efficient than the sieve of Eratosthenes. It is derived from similar algorithms for http://en.wikipedia.org/wiki/Prime-counting_function.
     * The advantage is that there is no need to find all the primes to find their sum.
     *
     * The main idea is as follows: Let S(v,m) be the sum of integers in the range 2..v that remain after sieving with all primes smaller or equal than m.
     * That is S(v,m) is the sum of integers up to v that are either prime or the product of primes larger than m.
     *
     * S(v, p) is equal to S(v, p-1) if p is not prime or v is smaller than p*p. Otherwise (p prime, p*p<=v) S(v,p) can be computed from S(v,p-1) by finding the sum of integers that are removed while sieving with p.
     * An integer is removed in this step if it is the product of p with another integer that has no divisor smaller than p. This can be expressed as
     *
     * S(v, p) = S(v, p-1) - p * (S(v/p, p-1) - S(p -1, p-1))
     *
     * Dynamic programming can be used to implement this. It is sufficient to compute S(v,p) for all positive integers v that are representable as floor(n/k) for some integer k and all p <= SQRT(v).
     *
     * @param num
     * @return
     */
    public static long getPrimeSum(long num) {
        /**
         * This method guaranteed to work till 10^10.
         * num > 10^10 will overflow, consider using the method with BigInteger.
         */
        int sqrtNum = (int)Math.sqrt(num);
        List<Long> V = LongStream.range(1, sqrtNum+1).map(i -> num/i).boxed().collect(Collectors.toList());
        long lastItem = V.get(V.size() - 1);
        for (long i = lastItem-1; i > 0; --i)
            V.add(i);

        Map<Long, Long> S = new HashMap<>();
        for(long n : V) {
            BigInteger b = BigInteger.valueOf(n).multiply(BigInteger.valueOf(n+1)).divide(BigInteger.valueOf(2)).subtract(BigInteger.ONE);
            S.put(n, b.longValue());
        }

        for (long p = 2; p <= sqrtNum; p++) {
            if (S.get(p) > S.get(p-1)) {   //p is prime
                long sp = S.get(p-1);      // sum of primes smaller than p
                long p2 = p*p;
                for(long v : V) {
                    if (v < p2) break;
                    long sum = S.get(v);
                    sum -= p * (S.get(v/p) - sp);
                    S.put(v, sum);
                }
            }
        }

        return S.get(num);
    }

    public static BigInteger getPrimeSumWithBigInteger(long num) {
        /**
         * This method guaranteed to work till 10^10.
         * num > 10^10 will overflow, consider using the method with BigInteger.
         */
        long sqrtNum = (long)Math.sqrt(num);
        List<Long> V = LongStream.range(1, sqrtNum+1).map(i -> num/i).boxed().collect(Collectors.toList());
        long lastItem = V.get(V.size() - 1);
        for (long i = lastItem-1; i > 0; --i)
            V.add(i);

        Map<Long, BigInteger> S = new HashMap<>();
        for(long n : V) {
            BigInteger b = BigInteger.valueOf(n).multiply(BigInteger.valueOf(n+1)).divide(BigInteger.valueOf(2)).subtract(BigInteger.ONE);
            S.put(n, b);
        }

        for (long p = 2; p <= sqrtNum; p++) {
            if (S.get(p).compareTo(S.get(p-1)) > 0) {   //p is prime
                BigInteger sp = S.get(p-1);      // sum of primes smaller than p
                long p2 = p*p;
                for(long v : V) {
                    if (v < p2) break;
                    BigInteger sum = S.get(v);
                    BigInteger vp = S.get(v/p).subtract(sp);
                    sum = sum.subtract(BigInteger.valueOf(p).multiply(vp));
                    S.put(v, sum);
                }
            }
        }

        return S.get(num);
    }

    public static long getPrimeCount(long num) {
        int sqrtNum = (int)Math.sqrt(num);
        List<Long> V = LongStream.range(1, sqrtNum+1).map(i -> num/i).boxed().collect(Collectors.toList());
        long lastItem = V.get(V.size() - 1);
        for (long i = lastItem-1; i > 0; --i)
            V.add(i);

        Map<Long, Long> C = new HashMap<>();
        for(long n : V) {
            C.put(n, n-1);
        }

        for (long p = 2; p <= sqrtNum; p++) {
            if (C.get(p) > C.get(p-1)) {   //p is prime
                long cp = C.get(p-1);            // number of primes smaller than p
                long p2 = p*p;
                for(long v : V) {
                    if (v < p2) break;

                    long count = C.get(v);
                    count -= C.get(v/p) - cp;
                    C.put(v, count);

                }
            }
        }

        return C.get(num);
    }
}

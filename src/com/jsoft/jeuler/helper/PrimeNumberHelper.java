package com.jsoft.jeuler.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumberHelper {

    public static boolean checkPrime(long n) {
        BigInteger b = new BigInteger(String.valueOf(n));
        return b.isProbablePrime(1);
    }

    public static long nextPrime(long n) {
        BigInteger b = new BigInteger(String.valueOf(n));
        return Long.parseLong(b.nextProbablePrime().toString());
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

    public static boolean[] sieveOfEratosthenes(int n)
    {
        // Create a boolean array "prime[0..n]" and initialize
        // all entries it as true. A value in prime[i] will
        // finally be false if i is Not a prime, else true.
        boolean prime[] = new boolean[n+1];
        for(int i=0;i<n;i++)
            prime[i] = true;

        prime[0] = prime[1] = false;

        for(int p = 2; p*p <=n; p++)
        {
            // If prime[p] is not changed, then it is a prime
            if(prime[p] == true)
            {
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

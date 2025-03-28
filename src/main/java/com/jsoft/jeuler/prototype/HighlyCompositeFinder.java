package com.jsoft.jeuler.prototype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HighlyCompositeFinder {
    static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29}; // First few primes
    static long bestNumber = 1, maxDivisors = 1;
    static HashMap<Long, Integer> divisorCache = new HashMap<>();

    public static int countDivisors(long x) {
        if (divisorCache.containsKey(x)) return divisorCache.get(x);

        int count = 1;
        long num = x;
        for (int p = 2; (long) p * p <= x; p++) {
            if (num % p == 0) {
                int exp = 0;
                while (num % p == 0) {
                    num /= p;
                    exp++;
                }
                count *= (exp + 1);
            }
        }
        if (num > 1) count *= 2; // Account for remaining prime factor

        divisorCache.put(x, count); // Cache result
        return count;
    }

    public static void findBest(long num, int divisors, int primeIndex, long limit) {
        if (num > limit || primeIndex >= primes.length) return; // Stop if out of bounds

        // Update best found number
        if (divisors > maxDivisors) {
            maxDivisors = divisors;
            bestNumber = num;
        }

        long prime = primes[primeIndex];
        int power = 1;

        // Prevent overflow when multiplying num * prime
        while (num <= limit / prime) { // Safe multiplication
            num *= prime;
            power++;
            findBest(num, divisors * (power + 1), primeIndex + 1, limit);
        }
    }

    public static long findHighlyComposite(long n) {
        bestNumber = 1;
        maxDivisors = 1;
        divisorCache.clear(); // Clear cache for fresh calculation
        findBest(1, 1, 0, n);
        System.out.println("Max divisors: " + maxDivisors);
        return bestNumber;
    }

    private static void splitForPrimeFactors() {
        int n = 32; // Number to be split
        int x = 2;  // Number of parts

        List<List<Integer>> results = new ArrayList<>();
        findSplits(n, x, 2, new ArrayList<>(), results);

        // Print the results
        for (List<Integer> result : results) {
            System.out.println(result);
        }
    }

    private static void findSplits(int n, int parts, int start, List<Integer> current, List<List<Integer>> results) {
        if (parts == 1) {
            if (n >= start) { // Ensure the last number is at least 'start'
                current.add(n);
                current.sort(Comparator.reverseOrder());
                results.add(new ArrayList<>(current));
                current.remove(current.size() - 1);
            }
            return;
        }

        for (int i = start; i <= Math.sqrt(n); i++) {
            if (n % i == 0) { // Ensure it divides evenly
                current.add(i);
                findSplits(n / i, parts - 1, i, current, results);
                current.remove(current.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
//        long n = (long)1e3; // Example large input
//        long v = findHighlyComposite(n);
//        System.out.println("N : " + (n- v));
//        System.out.println("Number with most divisors ≤ " + n + ": " + v);
        splitForPrimeFactors();
    }
}



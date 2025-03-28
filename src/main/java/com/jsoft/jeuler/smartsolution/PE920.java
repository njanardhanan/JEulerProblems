package com.jsoft.jeuler.smartsolution;

import java.util.HashMap;
import java.util.Map;

public class PE920 {
    private static final long MAX_VALUE = (long) 1e3;
    private static final long INF = (long) 1e18;
    private static final int[] PRIME = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};
    private static final int PRIME_COUNT = PRIME.length - 1;

    private static long totalCalls;
    private static Map<Long, Long> minValues = new HashMap<>();

    private static void calculateFactors(int position, long currentProduct, long previousFactors, long lastFactors) {
        if (currentProduct > MAX_VALUE) {
            return;
        }

        totalCalls++;

        if (currentProduct % (previousFactors * lastFactors) == 0) {
            long product = previousFactors * lastFactors;
            minValues.putIfAbsent(product, INF);
            minValues.put(product, Math.min(minValues.get(product), currentProduct));
        }

        for (int i = position; i <= position; i++) {
            calculateFactors(i, PRIME[i] * currentProduct, previousFactors, lastFactors + 1);
        }

        for (int i = position + 1; i <= PRIME_COUNT; i++) {
            calculateFactors(i, PRIME[i] * currentProduct, previousFactors * lastFactors, 2);
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        totalCalls = 0;

        calculateFactors(0, 1, 1, 1);

        long sum = 0;
        for (long i=0; i < 50000; i++) {
            if (minValues.containsKey(i)) {
                System.out.println(i + ", " + minValues.get(i));
                sum += minValues.get(i);
            }
        }
//        for (long value : minValues.values()) {
//            sum += value;
//        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(totalCalls + " " + sum + " in " + elapsedTime + "ms");
    }
}


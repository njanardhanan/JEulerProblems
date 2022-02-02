package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0111 extends EulerSolver {

    public JEulerProblem_0111(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT_MIN = 1000000000L;
        long LIMIT_MAX = 9999999999L;
        long splitSize = 100;
        long splitStep = (LIMIT_MAX - LIMIT_MIN + 1) / splitSize;
        System.out.println(Integer.MAX_VALUE);
        System.out.println(splitStep);
        List<Integer> primeSieves = PrimeNumberHelper.sieveOfEratosthenesAsList((int)Math.sqrt(LIMIT_MAX));

        int[] maxDigits = new int[10];
        Map<Integer, List<Long>> listOfPrimes = new HashMap<>();
        for (long i=LIMIT_MIN-1; i<LIMIT_MAX; i+= splitStep) {
            long low = i+1;
            long high = i+splitStep;
            //System.out.printf("Range : %d - %d\n", low, high);
            boolean[] primesByRange = PrimeNumberHelper.sieveByRange(low, high, primeSieves);
            Set<Long> primes = PrimeNumberHelper.primesFromSieveByRange(low, high, primesByRange);
            for (long p : primes) {
                Map<Integer, Integer> map = getDigitsCount(p);
                for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                    if (maxDigits[e.getKey()] < e.getValue()) {
                        maxDigits[e.getKey()] = e.getValue();
                        listOfPrimes.put(e.getKey(), new ArrayList<>());
                        listOfPrimes.get(e.getKey()).add(p);
                    } else if (maxDigits[e.getKey()] == e.getValue()) {
                        listOfPrimes.get(e.getKey()).add(p);
                    }
                }
            }
        }

        long ans = 0;
        for(int i=0; i<10; i++) {
            //System.out.printf("%d - %d - %d - %s\n", i, maxDigits[i], listOfPrimes.get(i).size(), listOfPrimes.get(i));
            Long s = listOfPrimes.get(i).stream().reduce(0L, Long::sum);
            ans += s;
        }
        return Long.toString(ans);
    }

    private Map<Integer, Integer> getDigitsCount(long prime) {
        Map<Integer, Integer> map = new HashMap<>();
        while(prime > 0) {
            int d = (int)(prime%10L);
            if (map.containsKey(d)) {
                map.put(d, map.get(d) + 1);
            } else {
                map.put(d, 1);
            }
            prime /= 10L;
        }
        return map;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=111\n";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

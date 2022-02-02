package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class JEulerProblem_0293 extends EulerSolver {

    public JEulerProblem_0293(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Explanation:
         * Admissible number is "even" positive number, so we should
         * include prime 2 in consecutive primes.
         * The logic here is to generate prime factors with consecutive primes
         * starting from prime 2 till less than LIMIT.
         * And find pseudoFortunate from it.
         */
        final long LIMIT = 1000000000L;
        Queue<Long> queue = new LinkedList<>();
        int maxPower = (int)Math.ceil(Math.log(LIMIT)/Math.log(2));

        for(int i=1; i<=maxPower; i++) {
            queue.add((long)Math.pow(2, i));
        }

        List<Integer> primeList = PrimeNumberHelper.sieveOfEratosthenesAsList(100);
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes((int)LIMIT);
        Set<Integer> pseudoFortunateSet = new HashSet<>();
        int primeIndex = 1; // Start with prime 3
        while (!queue.isEmpty()) {
            int size = queue.size();
            int p = primeList.get(primeIndex);
            for(int x=0; x<size; x++) {
                long num = queue.remove();
                if (num < LIMIT) {
                    pseudoFortunateSet.add(getPseudoFortunate(primes, (int)num));

                    maxPower = (int) Math.ceil(Math.log(LIMIT / num) / Math.log(p));
                    for (int i = 1; i <= maxPower; i++) {
                        long v = num * (long) Math.pow(p, i);
                        if (v < LIMIT) {
                            queue.add(v);
                        }
                    }
                }
            }
            primeIndex++;
        }

        long ans = pseudoFortunateSet.stream().mapToInt(Integer::intValue).sum();
        return Long.toString(ans);
    }

    private int getPseudoFortunate(boolean[] primes, int key) {
        int minPF = 3;
        while (!primes[key + minPF]) {
            minPF+= 2;
        }
        return minPF;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=293\n\n" +
                "An even positive integer N will be called admissible, if it is a power of 2 or its distinct prime factors are consecutive primes.\n" +
                "The first twelve admissible numbers are 2,4,6,8,12,16,18,24,30,32,36,48.\n" +
                "\n" +
                "If N is admissible, the smallest integer M > 1 such that N+M is prime, will be called the pseudo-Fortunate number for N.\n" +
                "\n" +
                "For example, N=630 is admissible since it is even and its distinct prime factors are the consecutive primes 2,3,5 and 7.\n" +
                "The next prime number after 631 is 641; hence, the pseudo-Fortunate number for 630 is M=11.\n" +
                "It can also be seen that the pseudo-Fortunate number for 16 is 3.\n" +
                "\n" +
                "Find the sum of all distinct pseudo-Fortunate numbers for admissible numbers N less than 10^9.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class JEulerProblem_0273 extends EulerSolver {

    public JEulerProblem_0273(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        private long a;
        private long b;

        public Data(long a, long b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "[" + a +","+ b + "]";
        }
    }

    private static final int LIMIT = 150;

    @Override
    public String solve() {
        /**
         * https://en.wikipedia.org/wiki/Fermat%27s_theorem_on_sums_of_two_squares
         */
        List<Integer> primeOneModFour = sievePrimeOneModFour(LIMIT);

        Map<Integer, Data> sumOfTwoSquare = new HashMap<>();
        for(int p : primeOneModFour) {
            sumOfTwoSquare.put(p, getSumOfTwoSquareForPrimeOneModFour(p));
        }

        long result = 0;
        for(int len=1; len<=primeOneModFour.size(); len++) {
            Iterator<List<Integer>> combination = Generator
                    .combination(primeOneModFour)
                    .simple(len)
                    .iterator();

            while (combination.hasNext()) {
                long r = processSumOfTwoSquare(combination.next(), sumOfTwoSquare);
                result += r;
            }
        }

        return String.valueOf(result);
    }

    private long processSumOfTwoSquare(List<Integer> primes, Map<Integer, Data> primesumOfTwoSquare) {
        Data first = primesumOfTwoSquare.get(primes.get(0));
        if (primes.size() == 1) {
            return first.a;
        }

        Data second = primesumOfTwoSquare.get(primes.get(1));
        List<Data> dataList = getBrahmaguptaFibonacciIdentity(first, second);
        if (primes.size() == 2) {
            return dataList.get(0).a + dataList.get(1).a;
        }

        Queue<Data> queue = new LinkedList<>();
        queue.add(dataList.get(0));
        queue.add(dataList.get(1));

        for(int i=2; i<primes.size(); i++) {
            Queue<Data> tmpQueue = new LinkedList<>();
            first = primesumOfTwoSquare.get(primes.get(i));
            while(!queue.isEmpty()) {
                second = queue.poll();
                dataList = getBrahmaguptaFibonacciIdentity(first, second);
                tmpQueue.addAll(dataList);
            }
            queue = tmpQueue;
        }

        long result = 0;
        while(!queue.isEmpty()) {
            first = queue.poll();
            result += first.a;
        }

        return result;
    }

    private List<Data> getBrahmaguptaFibonacciIdentity(Data first, Data second) {
        /**
         * https://en.wikipedia.org/wiki/Brahmagupta%E2%80%93Fibonacci_identity
         */

        List<Data> data = new ArrayList<>();

        //(ac-bd)^2 + (ad + bc)^2
        long x = first.a * second.a - first.b * second.b;
        long y = first.a * second.b + first.b * second.a;

        x = Math.abs(x);
        y = Math.abs(y);

        if (x<y) {
            data.add(new Data(x, y));
        } else {
            data.add(new Data(y, x));
        }

        //(ac+bd)^2 + (ad - bc)^2
        x = first.a * second.a + first.b * second.b;
        y = first.a * second.b - first.b * second.a;

        x = Math.abs(x);
        y = Math.abs(y);

        if (x<y) {
            data.add(new Data(x, y));
        } else {
            data.add(new Data(y, x));
        }

        return data;
    }

    private Data getSumOfTwoSquareForPrimeOneModFour(int p) {
        /**
         * https://en.wikipedia.org/wiki/Fermat%27s_theorem_on_sums_of_two_squares
         * As per Fermat's theorem on sums of two squares,
         * every prime of form 4k+1 have a unique solution.
         */
        int sqrtP = (int)Math.sqrt(p);
        for (int a=1; a<=sqrtP; a++) {
            for (int b=1; b<=sqrtP; b++) {
                if (a*a + b*b == p) {
                    return new Data(a, b);
                }
            }
        }
        return new Data(0, 0);
    }

    private List<Integer> sievePrimeOneModFour(int limit) {
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(limit);
        List<Integer> primeOneModFour = new ArrayList<>();
        for(int p : primes) {
            if (p%4 == 1) {
                primeOneModFour.add(p);
            }
        }
        return primeOneModFour;
    }

    @Override
    public String getProblemStatement() {
        return "Consider equations of the form: a2 + b2 = N, 0 ≤ a ≤ b, a, b and N integer.\n" +
                "For N=65 there are two solutions:\n" +
                "a=1, b=8 and a=4, b=7.\n" +
                "We call S(N) the sum of the values of a of all solutions of a2 + b2 = N, 0 ≤ a ≤ b, a, b and N integer.\n" +
                "Thus S(65) = 1 + 4 = 5.\n" +
                "Find ∑(N), for all squarefree N only divisible by primes of the form 4k+1 with 4k+1 < 150.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("fermat", "sum of two square", "brahmagupta", "fibonacci", "identity");
    }
}

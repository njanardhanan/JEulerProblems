package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class JEulerProblem_0615 extends EulerSolver {

    public JEulerProblem_0615(int problemNumber) {
        super(problemNumber);
    }

    static class NaturalNumber implements Comparable<NaturalNumber> {
        private Map<Integer, Integer> primeFactors;
        private double logValue;

        public NaturalNumber() {
            primeFactors = new HashMap<>();
        }

        //Copy constructor
        public NaturalNumber(NaturalNumber that) {
            primeFactors = new HashMap<>(that.primeFactors);
        }

        public void put(int primeIndex, int power) {
            primeFactors.put(primeIndex, primeFactors.getOrDefault(primeIndex, 0) + power);
        }

        public void increment(int primeIndex) {
            primeFactors.put(primeIndex, primeFactors.getOrDefault(primeIndex, 0) + 1);
        }

        public void decrement(int primeIndex) {
            if(primeFactors.containsKey(primeIndex) && primeFactors.get(primeIndex) == 1) {
                primeFactors.remove(primeIndex);
            } else if(primeFactors.containsKey(primeIndex)) {
                primeFactors.put(primeIndex, primeFactors.get(primeIndex) - 1);
            }
        }

        public void calculateLogValue(List<Integer> primes) {
            logValue = 0.0;
            for(Map.Entry<Integer, Integer> e : primeFactors.entrySet()) {
                int primeNumber = primes.get(e.getKey());
                logValue += (Math.log(primeNumber) * e.getValue());
            }
        }

        public boolean isEqual(NaturalNumber that) {
            return this.compareTo(that) == 0;
        }

        @Override
        public int compareTo(NaturalNumber that) {
            return Double.compare(this.logValue, that.logValue);
        }

        public String toString(List<Integer> primes) {
            StringBuilder sb = new StringBuilder();
            int size = primeFactors.size();
            for(Map.Entry<Integer, Integer> e : primeFactors.entrySet()) {
                int primeNumber = primes.get(e.getKey());
                sb.append(primeNumber);
                sb.append("^");
                sb.append(e.getValue());
                if(size > 1) {
                    sb.append(" * ");
                }
                size--;
            }
            return sb.toString();
        }

        public long getActualValue(List<Integer> primes, long module) {
            BigInteger MOD = BigInteger.valueOf(module);
            BigInteger ans = BigInteger.ONE;
            for(Map.Entry<Integer, Integer> e : primeFactors.entrySet()) {
                BigInteger primeNumber = BigInteger.valueOf(primes.get(e.getKey()));
                BigInteger curValue = primeNumber.modPow(BigInteger.valueOf(e.getValue()), MOD);
                ans = ans.multiply(curValue).mod(MOD);
            }
            return ans.longValue();
        }
    }

    @Override
    public String solve() {
        long MOD = 123454321L;
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(2 * NumericHelper.ONE_MILLION_INT);

        PriorityQueue<NaturalNumber> queue = new PriorityQueue<>();

        NaturalNumber number = new NaturalNumber();
        number.put(0, NumericHelper.ONE_MILLION_INT);
        number.calculateLogValue(primes);
        queue.add(number);

        for(int i=0; i<NumericHelper.ONE_MILLION_INT-1; i++) {
            NaturalNumber n = queue.poll();

            //Remove duplicate
            while(!queue.isEmpty() && n.isEqual(queue.peek())) {
                n = queue.poll();
            }

            /**
             * Replace a prime number
             * For Example:
             * 1. Replace 2^5 => 2^4 * 3^1
             * 2. Replace 2^5 * 3^1 => 2^4 * 3^0 * 5^1 => 2^4 * 5^1
             */
            for(int p : n.primeFactors.keySet()) {
                NaturalNumber newN = new NaturalNumber(n);
                newN.decrement(p);
                newN.increment(p+1);
                newN.calculateLogValue(primes);
                queue.add(newN);
            }

            /**
             * Add a prime 2
             */
            NaturalNumber newN = new NaturalNumber(n);
            //Since 2 is the first prime, i.e, 0th prime index
            newN.increment(0);
            newN.calculateLogValue(primes);
            queue.add(newN);
        }

        NaturalNumber ans = queue.poll();

        System.out.println(ans.toString(primes));

        return String.valueOf(ans.getActualValue(primes, MOD));
    }

    @Override
    public String getProblemStatement() {
        return "Consider the natural numbers having at least 5 prime factors, which don't have to be distinct.\n" +
                "Sorting these numbers by size gives a list which starts with:\n" +
                "\n" +
                "32=2⋅2⋅2⋅2⋅2\n" +
                "48=2⋅2⋅2⋅2⋅3\n" +
                "64=2⋅2⋅2⋅2⋅2⋅2\n" +
                "72=2⋅2⋅2⋅3⋅3\n" +
                "80=2⋅2⋅2⋅2⋅5\n" +
                "96=2⋅2⋅2⋅2⋅2⋅3\n" +
                "  ...\n" +
                "So, for example, the fifth number with at least 5 prime factors is 80.\n" +
                "\n" +
                "Find the millionth number with at least one million prime factors.\n" +
                "Give your answer modulo 123454321.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

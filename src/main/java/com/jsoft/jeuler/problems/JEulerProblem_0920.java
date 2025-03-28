package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class JEulerProblem_0920 extends EulerSolver {

    private static final int TARGET_NUMBER_OF_DIGITS = 16;
    private static final double MAX_VALUE_LOG = TARGET_NUMBER_OF_DIGITS * Math.log(10);

    class Data {
        public List<Integer> divisors = new ArrayList<>();
        public Map<Integer, Integer> primeFactors = new HashMap<>();
    }

    class TauNumber {
        public int n;
        public Map<Integer, Integer> primeFactors = new HashMap<>();
        public long value = 0;
        public double logValue = 0.0;
        public double MAX_TARGET = 0.0;
        public int divisors;

        public TauNumber() {
        }

        public TauNumber(int n, int prime, int expo, double maxTarget) {
            this.n = n;
            primeFactors.put(prime, expo);
            divisors = expo + 1;
            logValue = expo * Math.log(prime);
            value = (long) Math.pow(prime, expo);
            MAX_TARGET = maxTarget;
        }

        public TauNumber deepCopy() {
            TauNumber t = new TauNumber();
            t.n = this.n;
            t.divisors = divisors;
            t.value = value;
            t.logValue = logValue;
            t.MAX_TARGET = MAX_TARGET;
            for (Map.Entry<Integer, Integer> entry : primeFactors.entrySet()) {
                t.primeFactors.put(entry.getKey(), entry.getValue());
            }
            return t;
        }

        public boolean isValid() {
            if (Double.compare(MAX_TARGET, logValue) < 0) {
                return false;
            }
            if (divisors > n) {
                return false;
            }
            return true;
        }

        public boolean isPrimeAlreadyAdded(int p) {
            return primeFactors.containsKey(p);
        }

        public boolean doesContainsAllPrimes(List<Integer> primes) {
            for (int prime : primes) {
                if (!isPrimeAlreadyAdded(prime)) {
                    return false;
                }
            }
            return true;
        }

        public int getRemainingDivisor() {
            return this.n / this.divisors;
        }

        public boolean put(int prime, int expo) {
            if (primeFactors.containsKey(prime)) {
                return false;
            }
            primeFactors.put(prime, expo);
            this.divisors *= (expo + 1);
            logValue += (expo * Math.log(prime));
            value *= (long)Math.pow(prime, expo);
            return isValid();
        }

        @Override
        public String toString() {
            return this.divisors + " : " + this.value + " => " + this.primeFactors;
        }
    }

    public JEulerProblem_0920(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Ans : 1154027691000533893

        /**
         * Why the limit is 43010?
         * As per OEIS for Highly composite numbers
         * 10108248702552000 is the 136th composite number with 43008 divisors.
         * Refer :
         * https://oeis.org/A002182
         * https://oeis.org/A002182/b002182.txt
         *
         * Note that 10108248702552000 is a 17 digits number,
         * that means, 16 digits number will have less than 43008 divisors
         * so I took 43010 as limit.
         *
         */
        int LIMIT = 43010;
        /**
         * Why only primes less than 100?
         * Because numbers with
         */
        List<Integer> primeFactors = PrimeNumberHelper.sieveOfEratosthenesAsList(100);
        Map<Integer, Data> memo = sieveDivisorAndPrimeFactors(LIMIT);
        long ans = 0;
        //int count = 0;
        //Map<Integer, Long> resultAlgo = new HashMap<>();
        for (int i=2; i<=LIMIT; i++) {
            long x = findTauNumber(memo, i, primeFactors);
            //resultAlgo.put(i, x);
            ans += x;
            //count++;
        }

        ans += 1;
        //System.out.println("Count: " + count);
        //compareResults(resultByBruteForce, resultAlgo);

        return Long.toString(ans);
    }

    private long findTauNumber(Map<Integer, Data> data, int n, List<Integer> primeFactors) {
        //135 - 396900
        List<Integer> mustHavePrime = new ArrayList<>(data.get(n).primeFactors.keySet());
        mustHavePrime.sort(Comparator.naturalOrder());
        Queue<TauNumber> dataQ = new LinkedList<>();

        //Include the first "must have" prime
        int firstPrime = mustHavePrime.get(0);
        for (int d : data.get(n).divisors) {
            int expo = d-1;
            if (expo >= data.get(n).primeFactors.get(firstPrime)) {
                double v = expo * Math.log(firstPrime);
                if (Double.compare(MAX_VALUE_LOG, v) >= 1) {
                    TauNumber t = new TauNumber(n, firstPrime, expo, MAX_VALUE_LOG);
                    dataQ.add(t);
                }
            }
        }

        //Include the remaining "must have" primes
        for (int i=1; i< mustHavePrime.size(); i++) {
            putData(data, dataQ, mustHavePrime.get(i), data.get(n).primeFactors.get(mustHavePrime.get(i)));
        }

        //Short circuit
        if (dataQ.isEmpty()) {
            return 0L;
        }

        //Short circuit
        if (dataQ.size() == 1 && dataQ.peek().getRemainingDivisor() == 1) {
            //System.out.println(dataQ.peek());
            return dataQ.peek().value;
        }


        PriorityQueue<TauNumber> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.value));
        int size = dataQ.size();
        for (int i=0; i<size; i++) {
            TauNumber t = dataQ.poll();

            //Check if all "must have" primes are included, and remove the data without "must have" primes.
            if (!t.doesContainsAllPrimes(mustHavePrime)) {
                continue;
            }

            if (t.getRemainingDivisor() == 1) {
                pq.add(t);
            } else {
                dataQ.add(t);
            }
        }

        while(!dataQ.isEmpty()) {
            int nextPrime = 0;
            for (int p : primeFactors) {
                if (dataQ.peek().isPrimeAlreadyAdded(p)) {
                    continue;
                }
                nextPrime = p;
                break;
            }
            putData(data, dataQ, nextPrime, 1);
            size = dataQ.size();
            for (int i=0; i<size; i++) {
                TauNumber t = dataQ.poll();
                if (t.getRemainingDivisor() == 1) {
                    pq.add(t);
                } else {
                    dataQ.add(t);
                }
            }
        }

        if (pq.isEmpty()) {
            return 0L;
        }

        //System.out.println(pq.peek());
        return pq.peek().value;
    }

    private int putData(Map<Integer, Data> data, Queue<TauNumber> dataQ, int prime, int minExpo) {
        int size = dataQ.size();
        int count = 0;
        for (int j=0; j<size; j++) {
            TauNumber t = dataQ.poll();
            int remainingDivisor = t.getRemainingDivisor();
            if (remainingDivisor <= 1) {
                dataQ.add(t);
                continue;
            }
            for (int d : data.get(remainingDivisor).divisors) {
                int expo = d-1;
                if (expo >= minExpo) {
                    TauNumber newT = t.deepCopy();
                    boolean valid = newT.put(prime, expo);
                    if (valid) {
                        dataQ.add(newT);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private Map<Integer, Data> sieveDivisorAndPrimeFactors(int n) {
        Map<Integer, Data> memo = new HashMap<>();

        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= n; j+=i) {
                if (j % i == 0) {
                    Data data;
                    if (memo.containsKey(j)) {
                        data = memo.get(j);
                    } else {
                        data = new Data();
                    }

                    data.divisors.add(i);
                    memo.put(j, data);
                }
            }
        }

        //Sieve prime factors
        for (int i = 2; i <= n; i++) {
            int t = i;
            Data data = memo.get(i);
            while (t != 1) {
                int smpf = memo.get(t).divisors.get(0);
                int exponent = data.primeFactors.getOrDefault(smpf, 0);
                data.primeFactors.put(smpf, exponent + 1);
                t /= smpf;
            }
        }

        return memo;
    }

    public Map<Integer, Long> solveBF(int INPUT) {
        List<List<Integer>> divisors = sieveDivisors(INPUT);
        Set<Integer> mSet = new HashSet<>();
        Map<Integer, Long> result = new HashMap<>();
        int ans = 0;
        for (int i=2; i<=INPUT; i++) {
            int d = divisors.get(i).size() + 1;
            if (mSet.contains(d)) {
                continue;
            }
            if (divisors.get(i).contains(d)) {
                result.put(d, (long)i);
                //System.out.println(d + " : " + i + " : " + NumericHelper.getPrimeFactors(d) + " => " +NumericHelper.getPrimeFactors(i));
                mSet.add(d);
                ans += i;
            }
        }
        System.out.println("Ans from BruteForce : " + (ans + 1));
        //System.out.println(primeFactors);
        return result;
    }

    private List<List<Integer>> sieveDivisors(int n) {
        List<List<Integer>> divisors = new ArrayList<>();
        for (int i = 1; i <= n+1; i++) {
            List<Integer> divisor = new ArrayList<>();
            divisors.add(divisor);
        }
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= n; j+=i) {
                divisors.get(j).add(i);
            }
        }
        return divisors;
    }

    private void compareResults(Map<Long, Long> resultByBruteForce, Map<Integer, Long> resultByAlgo) {
        for (long k : resultByBruteForce.keySet()) {
            if (resultByAlgo.containsKey((int)k) && Objects.equals(resultByBruteForce.get(k), resultByAlgo.get((int)k))) {
                continue;
            }
            if (!resultByAlgo.containsKey((int)k)) {
                System.out.println("Algo result does not contain " + k + " : " + resultByBruteForce.get(k));
                break;
            }
            if (!Objects.equals(resultByAlgo.get((int)k), resultByBruteForce.get(k))) {
                System.out.println("Algo result does not match " + k + " : " + resultByBruteForce.get(k) + " vs " + resultByAlgo.get((int)k));
                break;
            }
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=920\n" +
                "<p>For a positive integer $n$ we define $\\tau(n)$ to be the count of the divisors of $n$. For example, the divisors of $12$ are $\\{1,2,3,4,6,12\\}$ and so $\\tau(12) = 6$.</p>\n" +
                "<p>\n" +
                "A positive integer $n$ is a <b>tau number</b> if it is divisible by $\\tau(n)$. For example $\\tau(12)=6$ and $6$ divides $12$ so $12$ is a tau number.</p>\n" +
                "<p>\n" +
                "Let $m(k)$ be the smallest tau number $x$ such that $\\tau(x) = k$. For example, $m(8) = 24$, $m(12)=60$ and $m(16)=384$.</p>\n" +
                "<p>\n" +
                "Further define $M(n)$ to be the sum of all $m(k)$ whose values do not exceed $10^n$. You are given $M(3) = 3189$.</p>\n" +
                "<p>\n" +
                "Find $M(16)$.</p>\n" +
                "\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("divisor", "tau", "prime");
    }
}

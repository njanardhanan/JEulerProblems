package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class JEulerProblem_0417 extends EulerSolver {

    public JEulerProblem_0417(int problemNumber) {
        super(problemNumber);
    }

    class NumberPair implements Comparable<NumberPair> {
        private final int key;
        private int value;

        NumberPair(int k, int v) {
            this.key = k;
            this.value = v;
        }

        public void setValue(int v) {
            this.value = v;
        }

        @Override
        public String toString() {
            return "(" + key + ", " + value + ")";
        }

        @Override
        public int compareTo(NumberPair o) {
            return key - o.key;
        }
    }

    @Override
    public String solve() {
        /**
         * Used the algo described in wiki - https://en.wikipedia.org/wiki/Repeating_decimal#Table_of_values
         * There are 5 cases:
         *
         * Case 1 : Handling for prime 'p' denominators (excluding 2 and 5) which have repetend cycle length of p-1. Example 1/7 have a cycle length of 6
         * Case 2 : Handling for prime 'p' denominators (excluding 2 and 5) which does not have repetend cycle length of p-1. Example 1/11 have a cycle length of 2
         * Case 3 : Handling for non prime 'n' denominators which is in the form of p^k (excluding 2 and 5). Example 1/49 = 1/(7^2) have a cycle length of 42
         * Case 4 : Handling for non prime 'n' denominators which is in the form of p1^k1 * p2^k2 * ... * pn^kn (excluding 2 and 5). Example 1/21 = 1/(3^1*7^1)
         * Case 5 : Handling for non prime 'n' denominators which is in the form of p1^k1 * p2^k2 * ... * pn^kn , including 2 and/or 5. Example 1/6 = 1/(2^1*3^1)
         */
        int LIMIT = 100000000;
        long ans = 0;
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(LIMIT);
        int[] spf = PrimeNumberHelper.smallestPrimeFactors(LIMIT);

        //Generate primes, prime powers and totient value - other than 2 and 5 (co-primes to 10)
        //https://en.wikipedia.org/wiki/Repeating_decimal#Totient_rule
        //Used TreeSet to sort the objects, this can also done using List and then sorting later.
        TreeSet<NumberPair> numberPairList = new TreeSet<>();
        for (int p : primes) {
            if (p == 2 || p == 5) {
                continue;
            }

            for(long pp=p; pp<(long)LIMIT; pp*=p) {
                long t = (pp/p) * (p-1);
                numberPairList.add(new NumberPair((int)pp, (int)t));
            }
        }

        //Calculate repetend for prime and its power.
        //To handle CASE 1, 2 and 3.
        for(NumberPair numberPair : numberPairList) {
            int n = numberPair.key;
            int t = numberPair.value;
            int tmp = t;
            while (tmp > 1) {
                int s = spf[tmp];
                if (isPowModOne(t/s, n)) {
                    t /= s;
                }
                tmp /= s;
            }

            numberPair.setValue(t);
        }

        //Now calculate repetend for numbers in the form of p1 * p2 * p3, where prime p is not equal to 2 and/or 5.
        //i.e., number without 2 or 5 in its prime factor.
        //To handle CASE 4
        int[] repetend = new int[LIMIT + 1];
        repetend[1] = 1;
        for(NumberPair numberPair : numberPairList) {
            for (int i=0; i <= LIMIT / numberPair.key; i++) {
                if (repetend[i] != 0) {
                    repetend[i * numberPair.key] = NumericHelper.lcm(repetend[i], numberPair.value);
                }
            }
        }
        repetend[1] = 0;

        //Now calculate the sum along with repetend for numbers with 2 and 5 in its prime factors
        //This will handle CASE 5
        long sum = 0;
        for (int i=3; i<=LIMIT; i++) {

            //Calculate repetend for numbers with 2 and 5 in its prime factors
            int tmp = i;
            while (tmp%10 == 0) {
                tmp /= 10;
            }

            while (tmp%2 == 0) {
                tmp /= 2;
            }

            while (tmp%5 == 0) {
                tmp /= 5;
            }

            repetend[i] = repetend[tmp];
            sum += repetend[tmp];
        }

        return Long.toString(sum);
    }

    private boolean isPowModOne(int val, int m) {
        return BigInteger.TEN.modPow(BigInteger.valueOf(val), BigInteger.valueOf(m)).equals(BigInteger.ONE);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=417";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("unit fraction", "repented", "totient", "prime");
    }
}

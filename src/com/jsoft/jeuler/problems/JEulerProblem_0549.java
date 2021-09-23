package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0549 extends EulerSolver {

    private final int LIMIT = 100000000;
    //private final int LIMIT = 100;
    private List<Integer> primeList;

    public JEulerProblem_0549(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        return Long.toString(solveBySieve());
        //return Long.toString(solveByLoop());
    }

    private long solveByLoop() {
        /**
         * THIS IS A SLOW METHOD
         *          * Takes 614182 ms to output the answer.
         *          * Improved to 223076 from 614182
         *          * Need improvement.
         *
         *
         * https://en.wikipedia.org/wiki/Kempner_function
         * Also called Smarandache function
         * https://oeis.org/A002034
         *
         * The following identities were given by Kempner (1918):
         *
         * a(1) = 1.
         * a(n!) = n.
         * a(p) = p for p prime.
         * a(p1 * p2 * ... * pu) = pu if p1 < p2 < ... < pu are distinct primes.
         * a(p^k) = p * k for p prime and k <= p.
         * Let n = p1^e1 * p2^e2 * ... * pu^eu be the canonical factorization of n, then a(n) = max( a(p1^e1), a(p2^e2), ..., a(pu^eu) )
         */
        long ans = 0;
        for(int i=2; i<=LIMIT; i++) {
            Map<Integer, Integer> primeFactors = NumericHelper.getPrimeFactors(i);
            long v = getKempnerNumber(primeFactors);
            //System.out.println(i + " : " + v);
            ans += v;
            if (i % 1000000 == 0) {
                System.out.print('.');
            };
            if (i % 10000000 == 0) {
                System.out.println("R : " + i);
            }
        }

        return ans;
    }

    private long solveBySieve() {
        /**
         * Takes ~18 sec to output the answer.
         */
        primeList = PrimeNumberHelper.sieveOfEratosthenesAsList(LIMIT);
        int[] smallestNumberDividesMFactorial = new int[LIMIT + 1];
        //Start with prime 2
        sievePrimeFactors(0, 1, smallestNumberDividesMFactorial);
        System.out.println();
        long ans = 0;
        for(int i : smallestNumberDividesMFactorial) {
            ans += i;
        }
        //System.out.println(ans);
        return ans;
    }

    private void sievePrimeFactors(int currPrimeIndex, int currNumber, int[] smallestNumberDividesMFactorial) {
        //return if currNumber * currentPrime > Limit
        if(currPrimeIndex >= primeList.size() || currNumber > (LIMIT/primeList.get(currPrimeIndex))) {
            return;
        }

        for(int i=currPrimeIndex; i<primeList.size(); i++) {
            int p = primeList.get(i);
            if(currNumber > (LIMIT/p)) {
                break;
            }
            int maxExponent = (int)Math.floor(Math.log(LIMIT)/Math.log(p));
            for(int e=1; e<=maxExponent; e++) {
                if(currNumber > (LIMIT/(int)Math.pow(p, e))) {
                    break;
                }
                int n = currNumber * (int)Math.pow(p, e);
                smallestNumberDividesMFactorial[n] = Math.max(smallestNumberDividesMFactorial[currNumber], getKempnerNumber(p, e));
                sievePrimeFactors(i + 1, n, smallestNumberDividesMFactorial);
            }
        }
    }

    private int getKempnerNumber(Map<Integer, Integer> pFactors) {
        return pFactors.entrySet().stream().map(e -> getKempnerNumber(e.getKey(), e.getValue())).mapToInt(Integer::intValue).max().getAsInt();
    }

    private int getKempnerNumber(int p, int f) {
        if (f <= p) {
            return p*f;
        }
        /**
         * The special case is if factor is greater than the prime number.
         * Refer the attached document - Smarandache Function.pdf
         */
        /**
         * Step #1 - Recursively generate a(j) till a(v) <= factor < a(v+1)
         *  a(1) = 1
         *  a(j + 1) = p * a(j) + 1
         */
        int alpha = f;
        List<Integer> a = new ArrayList<>();
        a.add(1);
        int i = 1;
        while(true) {
            int v = p * a.get(i-1) + 1;
            if (v > alpha) {
                break;
            }
            a.add(v);
            i++;
        }

        /**
         * Step #2 - compute the sequences k_i and r_i according to the Euclidean algorithm-like procedure.
         *  k_v=floor(factor/a_v)
         *  r_v=factor - k_v * a_v
         */
        int sumOfK = 0;
        i = a.size() - 1;
        while(true) {
            int k = (int) Math.floor(alpha / a.get(i));
            int r = alpha - (k * a.get(i));
            sumOfK += k;
            if (r == 0) {
                break;
            }
            alpha = r;
            i--;
        }

        /**
         * Step #3 -
         *  S(p^f) = (p-1)f + sum_(i=v)k_i
         */
        return (p-1) * f + sumOfK;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=549\n\n" +
                "The smallest number m such that 10 divides m! is m=5.\n" +
                "The smallest number m such that 25 divides m! is m=10.\n" +
                "Let s(n) be the smallest number m such that n divides m!.\n" +
                "So s(10)=5 and s(25)=10.\n" +
                "Let S(n) be ∑s(i) for 2 ≤ i ≤ n.\n" +
                "S(100)=2012.\n" +
                "\n" +
                "Find S(108).";
    }
}

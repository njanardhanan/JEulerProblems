package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0249 extends EulerSolver {

    public JEulerProblem_0249(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * Dynamaic programming
         *
         * Example :
         * With the initial set {2, 3, 5}
         * the sub-set combination with its sum will be as follows
         *
         * {}           => 0
         * {2}          => 2
         * {3}          => 3
         * {2, 3}       => 5
         * {5}          => 5
         * {2, 5}       => 7
         * {3, 5}       => 8
         * {2, 3, 5}    => 10
         *
         * in other words, the sum of the subset and no. of subset with that sum will be as follows
         *
         * noOfSubset [ sumOfSubset] = count
         *
         * noOfSubset [ 0 ] = 1
         * noOfSubset [ 1 ] = 0
         * noOfSubset [ 2 ] = 1
         * noOfSubset [ 3 ] = 1
         * noOfSubset [ 4 ] = 0
         * noOfSubset [ 5 ] = 2
         * noOfSubset [ 6 ] = 0
         * noOfSubset [ 7 ] = 1
         * noOfSubset [ 8 ] = 1
         * noOfSubset [ 9 ] = 0
         * noOfSubset [ 10 ] = 1
         *
         */

        int LIMIT = 5000;
        long CUT_OFF = (long)Math.pow(10, 16);
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(LIMIT);
        int arrayLimit = (LIMIT * (LIMIT+1)) / 2;
        long[] noOfSubsets = new long[arrayLimit];

        noOfSubsets[0] = 1;

        int primeSumSoFar = 0;
        for(int p : primes) {
            //We have to do this in reverse order, else the collision will create duplicate values.
            //Do these steps manually will prime 7 to understand the collision.
            for(int i=primeSumSoFar+1; i>=0; i--) {
                noOfSubsets[i+p] += noOfSubsets[i];
                noOfSubsets[i+p] %= CUT_OFF;
            }
            primeSumSoFar += p;
        }

        primes = PrimeNumberHelper.sieveOfEratosthenesAsList(primeSumSoFar + 1 );

        long sum = 0;
        for(int p : primes) {
            sum += noOfSubsets[p];
            sum %= CUT_OFF;
        }

        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=249";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

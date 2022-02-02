package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JEulerProblem_0250 extends EulerSolver {

    public JEulerProblem_0250(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int LIMIT = 250250;
        //LIMIT = 5;
        int MOD = 250;
        //MOD = 5;
        long CUT_OFF = (long)Math.pow(10, 16);

        long[] noOfSubsets = new long[MOD];
        noOfSubsets[0] = 1L;

        for(int i=1; i<=LIMIT; i++) {
            int v = BigInteger.valueOf(i).modPow(BigInteger.valueOf(i), BigInteger.valueOf(MOD)).intValue();

            long[] nextRow = new long[MOD];
            for(int x=0; x<MOD; x++) {
                nextRow[(x+v)%MOD] = (noOfSubsets[x] + noOfSubsets[(x+v)%MOD]) % CUT_OFF;
            }
            noOfSubsets = nextRow;
        }

        //1425480602091519
        return Long.toString(noOfSubsets[0] - 1);
    }
    public String solveTry() {

        /**
         *
         * Refer Problem 249
         *
         * Note: This will work, but will take long time because 'sumSoFar'
         * will get bigger and bigger.
         *
         * Dynamaic programming
         *
         * Example :
         *
         * Lets change the question easier,
         *
         * Find the number of non-empty subsets of {1^1, 2^2, 3^3},
         * the sum of whose elements is divisible by 5.
         *
         * With the initial set {1^1, 2^2, 3^3} = {1, 4, 9}
         * the sub-set combination with its sum will be as follows
         *
         * {}           => 0 % 5 = 0
         * {1}          => 1 % 5 = 4
         * {4}          => 4 % 5 = 1
         * {1, 4}       => 5 % 5 = 0
         * {9}          => 9 % 5 = 4
         * {1, 9}       => 10 % 5 = 0
         * {4, 9}       => 13 % 5 = 3
         * {1, 4, 9}    => 15 % 5 = 0
         *
         * in other words, the sum of the subset mod 5 and no. of subset with that sum will be as follows
         *
         * noOfSubset [ sumOfSubset % 5] = count
         *
         * noOfSubset [ 0 ] = 4
         * noOfSubset [ 1 ] = 1
         * noOfSubset [ 2 ] = 0
         * noOfSubset [ 3 ] = 1
         * noOfSubset [ 4 ] = 2
         *
         * Ans will be noOfSubset [ 0 ] = 4
         *
         */

        int LIMIT = 250250;
        int MOD = 250;
        long CUT_OFF = (long)Math.pow(10, 16);
        int maxSum = 0;

        List<Integer> set = new ArrayList();
        for(int i=1; i<=LIMIT; i++) {
            //Performing (i^i) % 250
            int v = BigInteger.valueOf(i).modPow(BigInteger.valueOf(i), BigInteger.valueOf(MOD)).intValue();
            maxSum += v;
            set.add(v);
        }

        Collections.sort(set);

        System.out.println(maxSum);

        //Just one is enough
        maxSum += 100;

        long[] noOfSubsets = new long[maxSum];

        noOfSubsets[0] = 1;

        int sumSoFar = 0;
        for(int z=1; z<=LIMIT; z++) {
            int x = set.get(z-1);
            //We have to do this in reverse order, else the collision will create duplicate values.
            //Do these steps manually till 4^4 to understand the collision.
            for(int i=sumSoFar+1; i>=0; i--) {
                noOfSubsets[i+x] += noOfSubsets[i];
                noOfSubsets[i+x] %= CUT_OFF;
            }
            sumSoFar += x;
            if(z%1000 == 0) {
                System.out.printf("(%d-%d)", z, sumSoFar);
                if(z%10000 == 0) {
                    System.out.println();
                }
            }
        }

        System.out.println("Done");

        long sum = 0;
        for(int i=0; i<=maxSum; i+=5) {
            sum += noOfSubsets[i];
            sum %= CUT_OFF;
        }

        return Long.toString(sum);
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=250";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

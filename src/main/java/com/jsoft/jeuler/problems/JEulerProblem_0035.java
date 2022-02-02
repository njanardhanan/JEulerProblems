package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0035 extends EulerSolver {

    public JEulerProblem_0035(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int maxLimit = 1000000;
        //int maxLimit = 100;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(maxLimit);

        long count = 0;
        for(int i=2; i<maxLimit; i++) {
            if (isCircularPrime(i, primes)) {
                count++;
            }
        }

        return Long.toString(count);
    }

    private boolean isCircularPrime(int i, boolean[] primes) {

        Set<Integer> circularList = getCircularList(i);
        for(int x : circularList) {
            if (!primes[x]) {
                return false;
            }
        }
        //System.out.println(i);
        return true;
    }

    private Set<Integer> getCircularList(int n) {
        String noAsString = Integer.toString(n);
        int len = noAsString.length();

        Set<Integer> circularList = new HashSet();

        noAsString = noAsString + noAsString;

        for (int i=0; i<noAsString.length()-len; i++) {
            circularList.add(Integer.parseInt(noAsString.substring(i, len + i)));
            //System.out.print(" " + Integer.parseInt(noAsString.substring(i, len + i)) + ", ");
        }
        //System.out.println();

        return circularList;

    }


    @Override
    public String getProblemStatement() {
        return "How many circular primes are there below one million?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "sieve");
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;
import sun.nio.cs.ext.MacHebrew;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0037 extends EulerSolver {

    public JEulerProblem_0037(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int maxLimit = 1000000;
        //int maxLimit = 100;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(maxLimit);

        long ans = 0;
        for(int i=9; i<maxLimit; i++) {
            if(isRightTrancatablePrime(i, primes) && isLeftTrancatablePrime(i, primes)) {
                System.out.println(i);
                ans+=i;
            }
        }

        return Long.toString(ans);
    }

    private boolean isLeftTrancatablePrime(int n, boolean[] primes) {
        if (!primes[n]) {
            return false;
        }

        while (n>0) {
            if (n<10 && primes[n]) {
                return true;
            }
            n = n/10;
            if (!primes[n]) return false;
        }
        return false;
    }

    private boolean isRightTrancatablePrime(int n, boolean[] primes) {

        if (!primes[n]) {
            return false;
        }

        while (n>0) {
            if (n<10 && primes[n]) {
                return true;
            }
            int len = Integer.toString(n).length() - 1;
            n = n % (int) Math.pow(10, len);
            if (!primes[n]) return false;
        }
        return false;
    }



    @Override
    public String getProblemStatement() {
        return "Find the sum of the only eleven primes that are both truncatable from left to right and right to left.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

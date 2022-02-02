package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0010 extends EulerSolver {

    public JEulerProblem_0010(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long MAX = 2000000;

        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes((int)MAX);
        long sum = 0;
        for(int i=2; i<MAX; i++) {
            if (primes[i]) {
                sum += i;
            }
        }

        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all the primes below two million.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}

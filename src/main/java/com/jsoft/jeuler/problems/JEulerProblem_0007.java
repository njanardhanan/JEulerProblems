package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;
import java.util.stream.LongStream;

public class JEulerProblem_0007 extends EulerSolver {

    public JEulerProblem_0007(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int MAX = 10001;

        int index = 1;
        long currentPrime = 2;
        for(int i=2; i<=MAX; i++) {
            currentPrime = PrimeNumberHelper.nextPrime(currentPrime);
        }

        return Long.toString(currentPrime);
    }

    @Override
    public String getProblemStatement() {
        return "What is the 10 001st prime number?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}

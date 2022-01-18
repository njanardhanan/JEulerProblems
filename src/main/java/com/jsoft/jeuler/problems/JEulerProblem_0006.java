package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.stream.LongStream;

public class JEulerProblem_0006 extends EulerSolver {

    public JEulerProblem_0006(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int MAX = 101;
        long sum = LongStream.range(1, MAX).sum();
        sum *= sum;
        long SumOfSq = LongStream.range(1, MAX).map(x -> x*x).sum();

        return Long.toString(sum - SumOfSq);
    }

    @Override
    public String getProblemStatement() {
        return "Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.";
    }

}

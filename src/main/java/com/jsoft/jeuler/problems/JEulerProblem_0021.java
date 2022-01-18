package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.stream.LongStream;

public class JEulerProblem_0021 extends EulerSolver {

    public JEulerProblem_0021(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long answer = LongStream
                .range(1, 10001)
                .filter(x -> NumericHelper.isAmicableNumber(x))
                .sum();

        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "Evaluate the sum of all the amicable numbers under 10000";
    }
}

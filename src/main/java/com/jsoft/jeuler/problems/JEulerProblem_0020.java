package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

public class JEulerProblem_0020 extends EulerSolver {

    public JEulerProblem_0020(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        String hundredFactorial = IntStream
                .range(1,101)
                .mapToObj(x -> new BigInteger(Integer.toString(x)))
                .reduce(BigInteger::multiply)
                .get()
                .toString();
        return Long.toString(NumericHelper.sumOfDigits(hundredFactorial));
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of the digits in the number 100!.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

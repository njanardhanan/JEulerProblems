package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.Permutations;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JEulerProblem_0024 extends EulerSolver {

    public JEulerProblem_0024(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int sum = 0;
        List<String> items = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        String output = Permutations.permutation(1000000-1, items).stream().collect(Collectors.joining());
        return output;
    }

    @Override
    public String getProblemStatement() {
        return "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.\n" +
                "\n" +
                "Find the sum of all the multiples of 3 or 5 below 1000.";
    }
}

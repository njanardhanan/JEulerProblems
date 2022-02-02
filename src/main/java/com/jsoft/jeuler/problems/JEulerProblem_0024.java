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
        return "A permutation is an ordered arrangement of objects. For example, 3124 is one possible permutation of the digits 1, 2, 3 and 4." +
                "\nIf all of the permutations are listed numerically or alphabetically, we call it lexicographic order." +
                "\nThe lexicographic permutations of 0, 1 and 2 are:\n" +
                "\n" +
                "012   021   102   120   201   210\n" +
                "\n" +
                "What is the millionth lexicographic permutation of the digits 0, 1, 2, 3, 4, 5, 6, 7, 8 and 9?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("permutation");
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0000 extends EulerSolver {

    public JEulerProblem_0000(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        return "0";
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}

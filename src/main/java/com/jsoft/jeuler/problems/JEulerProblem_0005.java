package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class JEulerProblem_0005 extends EulerSolver {

    public JEulerProblem_0005(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long x = NumericHelper.lcm(2,3);
        for(int i=4; i<=20; i++) {
            x = NumericHelper.lcm(x, i);
        }

        return Long.toString(x);
    }

    @Override
    public String getProblemStatement() {
        return "2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.\n" +
                "\n" +
                "What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}

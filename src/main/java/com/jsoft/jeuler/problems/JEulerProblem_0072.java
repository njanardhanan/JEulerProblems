package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.Permutations;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;
import java.util.Map;

public class JEulerProblem_0072 extends EulerSolver {

    public JEulerProblem_0072(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Refer- https://en.wikipedia.org/wiki/Euler%27s_totient_function

        long answerN = 0;
        for(int i = 2; i<= NumericHelper.ONE_MILLION_INT; i++) {
            answerN += NumericHelper.phi(i);
        }
        return Long.toString(answerN);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=72";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

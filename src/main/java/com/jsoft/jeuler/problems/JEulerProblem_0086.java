package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0086 extends EulerSolver {

    public JEulerProblem_0086(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int count = 0;
        int answer = 0;
        boolean isDone = false;
        for(int a=1; !isDone; a++) {
            for(int b=1; b<=a && !isDone; b++) {
                for(int c=1; c<=b && !isDone; c++) {
                    int dSquared = a * a + (b+c) * (b+c);
                    int d = (int) Math.sqrt(dSquared);
                    if (dSquared == d * d) {
                        count++;
                    }
                    if(count > NumericHelper.ONE_MILLION_INT) {
                        answer = a;
                        isDone = true;
                    }
                }
            }
        }
        return Integer.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=86";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

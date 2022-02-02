package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0009 extends EulerSolver {

    public JEulerProblem_0009(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long ans = 0;
        for(int i=1; i<1000; i++) {
            for(int j=1; j<1000; j++) {
                for(int k=1; k<1000; k++) {
                    if((i+j+k) == 1000) {
                        if(i*i + j*j == k*k) {
                            return Integer.toString(i*j*k);
                        }
                    }
                }

            }

        }

        return Integer.toString(0);
    }

    @Override
    public String getProblemStatement() {
        return "There exists exactly one Pythagorean triplet for which a + b + c = 1000. Find the product abc.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}

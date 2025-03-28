package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_9999 extends EulerSolver {

    public JEulerProblem_9999(int problemNumber) {
        super(problemNumber);
    }

    public static void test(int x) {
        System.out.println();
        for (int i=1; i<=x; i++) {
            if (Integer.toBinaryString(i).contains("111")) {
                System.out.print(i + "(" + Integer.toBinaryString(i) + "), ");
            }
        }
        System.out.println();

    }



    @Override
    public String solve() {
        test(100);
        return Long.toString(0L);
    }

    @Override
    public String getProblemStatement() {
        return "just like that";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

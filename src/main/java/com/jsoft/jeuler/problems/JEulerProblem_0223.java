package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0223 extends EulerSolver {

    public JEulerProblem_0223(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        System.out.println("NOT SOLVED YET");

        int LIMIT = 100;
        for(int a=1; a<LIMIT; a++) {
            for(int b=a; b<LIMIT; b++) {
                for(int c=b; c<LIMIT; c++) {
                    int aSq = a * a;
                    int bSq = b * b;
                    int cSq = c * c;
                    if (aSq + bSq == cSq + 1) {
                        System.out.printf("{%d, %d, %d} - %d\n", a, b, c, a+b+c);
                    }
                }
            }
        }


        return Long.toString(0);
    }

    @Override
    public String getProblemStatement() {
        String problem = "\n" +
                "Let us call an integer sided triangle with sides a ≤ b ≤ c barely acute if the sides satisfy\n" +
                "a^2 + b^2 = c^2 + 1.\n" +
                "\n" +
                "How many barely acute triangles are there with perimeter ≤ 25,000,000?\n";
        return problem + "\nhttps://projecteuler.net/problem=223";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

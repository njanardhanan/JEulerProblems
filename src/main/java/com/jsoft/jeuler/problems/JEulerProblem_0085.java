package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0085 extends EulerSolver {

    public JEulerProblem_0085(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long diff = Long.MAX_VALUE;
        long TARGET = 2000000l;
        long area = 0;
        long N = 0, M = 0;
        for(long n=1; n<100; n++) {
            for(long m=1; m<100; m++) {
                long noOfRect = rectCount(n, m);
                long d = Math.abs(TARGET - noOfRect);
                if(diff > d) {
                    diff = d;
                    N = n;
                    M = m;
                    area = n*m;
                }
            }
        }

        System.out.println("N : " + N);
        System.out.println("M : " + M);

        return Long.toString(area);
    }

    private static long  rectCount(long n, long m)
    {
        return (m * n * (n + 1) * (m + 1)) / 4;
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=85";
    }
}

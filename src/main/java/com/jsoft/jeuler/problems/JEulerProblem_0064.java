package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;


public class JEulerProblem_0064 extends EulerSolver {

    public JEulerProblem_0064(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Algorithm
        int count = 0;
        for(int n=2; n<=10000; n++) {
            int S = n;
            int limit = (int) Math.sqrt(S);
            //continue if it is perfect square.
            if(limit * limit == S) continue;

            int m = 0;
            int d = 1;
            int a = limit;

            int period = 0;

            while (a != 2 * limit) {
                m = d * a - m;
                d = (S - (m * m)) / d;
                a = (limit + m) / d;
                period++;
            }
            if(period%2 != 0) count++;
        }


        //System.out.println(period);

        return Integer.toString(count);
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=75";
    }
}

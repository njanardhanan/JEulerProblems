package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0173 extends EulerSolver {

    public JEulerProblem_0173(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT = 1000000;
        int count = 0;

        for(long i=3; i<= LIMIT; i++) {
            for(long j=i-2; j>=1; j-=2) {
                long tiles = i*i - j*j;
                if (tiles > LIMIT) {
                    break;
                } else {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }

    public String solve1() {
        long LIMIT = 1000000;
        int count = 0;

        for(long i=3; ; i++) {
            long perimeter = (i-1) * 4;
            if(perimeter > LIMIT) break;
            long area = i * i;
            long remainingArea = area - perimeter;
            for(long x=1; x<i; x++) {
                if(i%2 != x%2) continue;
                long innerArea = x * x;
                if(innerArea <= remainingArea && area-innerArea <= LIMIT) {
                    count++;
                }
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=173";
    }
}

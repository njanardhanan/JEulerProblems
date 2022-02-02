package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0142 extends EulerSolver {

    public JEulerProblem_0142(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int ans = 0;
        for(int x=3; x<10000; x++){
            for(int y=2; y<x; y++){
                for(int z=1; z<y; z++){
                    if(!isSquare(x+y)) continue;
                    if(!isSquare(x-y)) continue;
                    if(!isSquare(x+z)) continue;
                    if(!isSquare(x-z)) continue;
                    if(!isSquare(y+z)) continue;
                    if(!isSquare(y-z)) continue;
                    ans = x+y+z;
                    break;
                }

            }

        }

        return Integer.toString(ans);
    }

    private boolean isSquare(int n) {
        int m = (int)Math.sqrt(n);
        return m*m == n;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=142";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

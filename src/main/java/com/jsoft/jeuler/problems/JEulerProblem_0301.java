package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0301 extends EulerSolver {

    public JEulerProblem_0301(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Nim game is solved by using XOR.
         * Find nim-sum, if it is zero then first player can force a win.
         *
         * Refer :
         *
         * https://www.geeksforgeeks.org/combinatorial-game-theory-set-2-game-nim/
         * https://mathstrek.blog/2012/08/02/combinatorial-game-theory-i/
         *
         **/

        //2^30
        int LIMIT = 1 << 30;
        int count = 0;
        for(int n=1; n<=LIMIT; n++) {
            //XOR
            int x = n ^ (2*n) ^ (3*n);
            if(x == 0) {
                count++;
            }
        }

        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=301";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

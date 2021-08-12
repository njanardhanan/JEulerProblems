package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0229 extends EulerSolver {

    private final int LIMIT = 2 * (int)Math.pow(10, 9);

    public JEulerProblem_0229(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {


        int count;

        byte[] aSqPlusNbSq = new byte[LIMIT + 1];


        count = calculate(aSqPlusNbSq, 7, (byte) 0, (byte) 1);
        count = calculate(aSqPlusNbSq, 3, (byte) 1, (byte) 2);
        count = calculate(aSqPlusNbSq, 2, (byte) 2, (byte) 3);
        count = calculate(aSqPlusNbSq, 1, (byte) 3, (byte) 4);

        /**
         * Set size for 10^7
         * 7 count : 1409320
         * 3 count : 437557
         * 2 count : 166355
         * 1 count : 75373
         */

        return Integer.toString(count);
    }

    private int calculate(byte[] byteArray, int d, byte from, byte to) {
        int count = 0;
        int yLimit = (int)Math.floor(Math.sqrt(LIMIT/d));
        for(int y=1; y <= yLimit; y++) {
            int dYSq = d*y*y;
            int xLimit = (int)Math.floor(Math.sqrt(LIMIT - dYSq));
            for(int x=1; x <= xLimit; x++) {
                int v = x*x + dYSq;
                if(byteArray[v] == from) {
                    byteArray[v] = to;
                    count++;
                }
            }
        }
        System.out.println(count);
        return count;
    }

    @Override
    public String getProblemStatement() {
        return  "\nhttps://projecteuler.net/problem=229";
    }
}

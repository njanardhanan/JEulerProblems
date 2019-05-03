package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0229 extends EulerSolver {

    public JEulerProblem_0229(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        System.out.println("NOT SOLVED YET");

        byte ONE_CONDITION   = 1 << 0;
        byte TWO_CONDITION   = 1 << 1;
        byte THREE_CONDITION = 1 << 2;
        byte FOUR_CONDITION  = 1 << 3;
        //int ALL_CONDITION   = ONE_CONDITION | TWO_CONDITION | THREE_CONDITION | FOUR_CONDITION;
        byte ALL_CONDITION   = 15;

        int LIMIT = 2 * (int)Math.pow(10, 9);
//        LIMIT = (int)Math.pow(10, 7);
        byte[] values = new byte[LIMIT + 1];

        int SQRT_LIMIT = (int) Math.sqrt(LIMIT) + 1000;

        for(int a=1; a<=SQRT_LIMIT; a++) {
            for(int b=1; a*a + b*b < LIMIT; b++) {

                int v = a*a + b*b;
                if(v > 0 && v < LIMIT) {
                    values[v] |= ONE_CONDITION;

                    v = a*a + 2*b*b;
                    if(v > 0 && v < LIMIT) {
                        values[v] |= TWO_CONDITION;

                        v = a*a + 3*b*b;
                        if(v > 0 && v < LIMIT) {
                            values[v] |= THREE_CONDITION;

                            v = a*a + 7*b*b;
                            if(v > 0 && v < LIMIT) {
                                values[v] |= FOUR_CONDITION;
                            }
                        }
                    }
                }
            }
        }

        int count=0;
        for(int v=1; v<LIMIT; v++) {
            if(values[v] == ALL_CONDITION) {
                //System.out.printf("%d,",v);
                if(v == 3600) System.out.println("Testcase #1 3600 Passed");
                if(v == 88201) System.out.println("Testcase #1 88201 Passed");
                count++;
            }
        }
        //10^8 = 660576 T-4162
        //10^7 = 75373 T-359
        //10^6 = 8637 T-42

        //11442963 T-107482
        //14517172 T-210214
        //17459402
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return  "\nhttps://projecteuler.net/problem=229";
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_9998 extends EulerSolver {

    public JEulerProblem_9998(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int limit = 915;
        //4804107136
        //4783071020
        System.out.println(getEngineer(limit));
        return "0";
    }

    private long getEngineer(int limit) {
        long count=0;
        for(int e3=3; e3<=limit; e3+=3) {
            for(int n2=2; n2<=(limit-e3); n2+=2) {
                for(int g=1; g<=(limit-e3-n2); g++) {
                    for(int i=1; i<(limit-e3-n2-g); i++) {
                        //int r = limit - (e3 + n2 + g + i);
                        //if(r == 0) continue;
                        //System.out.printf("%d,%d,%d,%d,%d = %d\n", e3, n2, g, i, r, (e3+n2+g+i+r));
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }
}

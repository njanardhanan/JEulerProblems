package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0039 extends EulerSolver {

    public JEulerProblem_0039(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int MAX = 420;

        Map<Integer, Integer> count = new HashMap();
        for(int x=1; x<MAX; x++) {
            for(int y=x+1; y<MAX; y++) {
                for(int z=y+1; z<MAX; z++) {
                    int p = x+y+z;
                    if (p <= 1000) {
                        if (isRightAngleTriangle(x, y, z)) {
                            //System.out.format("p : %d - x : %d, y : %d, z : %d\n",p,x,y,z);
                            if (count.containsKey(p)) {
                                count.put(p, count.get(p) + 1);
                            } else {
                                count.put(p, 1);
                            }
                        }
                    }
                }
            }
        }

        long answer = 0;
        int currentCount = 0;

        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (currentCount < entry.getValue()) {
                currentCount = entry.getValue();
                answer = entry.getKey();
            }
        }

        System.out.println(currentCount);

        return Long.toString(answer);
    }

    private boolean isRightAngleTriangle(int x, int y, int z) {

        if(x*x + y*y == z*z) {
//            if(x+y+z == 840) {
//                System.out.format("x : %d, y : %d, z : %d\n",x,y,z);
//            }
            return true;
        }

        return false;
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=39";
    }
}

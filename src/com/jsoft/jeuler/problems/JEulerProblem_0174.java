package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_0174 extends EulerSolver {

    public JEulerProblem_0174(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Refer - Problem 173
         */

        long LIMIT = 1000000;
        int count = 0;
        Map<Long, Integer> distinctLaminae = new HashMap();

        for(long i=3; i<= LIMIT; i++) {
            for(long j=i-2; j>=1; j-=2) {
                long tiles = i*i - j*j;
                if (tiles > LIMIT) {
                    break;
                } else {
                    if(distinctLaminae.containsKey(tiles)) {
                        distinctLaminae.put(tiles, distinctLaminae.get(tiles)+1);
                    } else {
                        distinctLaminae.put(tiles, 1);
                    }
                }
            }
        }

        for(Map.Entry<Long, Integer> e : distinctLaminae.entrySet()) {
            if(e.getValue() >= 1 && e.getValue() <= 10) {
                count++;
            }
            //System.out.println(e.getKey() + " : " + e.getValue());
        }

        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=174";
    }
}

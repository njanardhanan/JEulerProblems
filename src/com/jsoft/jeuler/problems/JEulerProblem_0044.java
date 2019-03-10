package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class JEulerProblem_0044 extends EulerSolver {

    public JEulerProblem_0044(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long output = Long.MAX_VALUE;
        Map<Long, Long> pentagonal = new HashMap();
        Map<Integer, Long> pentagonalBackup = new HashMap();
        long maxPentagonal = 0;
        for(int i=1; i<10000; i++) {
            long val = i * (3 * i - 1) / 2;
            pentagonal.put(val, val);
            pentagonalBackup.put(i, val);

            if(maxPentagonal < val) maxPentagonal = val;
        }

        for(int i=1; i<pentagonal.size(); i++) {
            for(int j=i+1; j<pentagonal.size(); j++) {
                long plus = pentagonalBackup.get(i) + pentagonalBackup.get(j);
                long diff = pentagonalBackup.get(j) - pentagonalBackup.get(i);
                if (plus > maxPentagonal) break;
                if (pentagonal.containsKey(plus) && pentagonal.containsKey(diff)) {
                    if (output > Math.abs(diff)) {
                        output = Math.abs(diff);
                    }
                    //System.out.println(pentagonalBackup.get(j) - pentagonalBackup.get(i));
                }
            }
        }

        return Long.toString(output);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=44";
    }
}

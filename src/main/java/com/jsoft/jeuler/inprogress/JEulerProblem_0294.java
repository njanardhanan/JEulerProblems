package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0294 extends EulerSolver {

    public JEulerProblem_0294(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Map<Integer, Map<Integer, Long>> memoCache = new HashMap<>();
        solveBruteForce();
        return "0";
    }
    public String solveBruteForce() {
        int count = 0;
        System.out.println(Integer.MAX_VALUE);
        System.out.println((((Long)(long)Math.pow(11L,12L))).toString());
        Map<Long, Long> map = new HashMap<>();
        Map<Long, Long> map1 = new HashMap<>();
        for (long i=23; i<1000000L; i+=23) {
            boolean flag = i%23 == 0;
            if (NumericHelper.sumOfDigits(i) == 23L) {
                if (flag) {

                    count++;
                    //System.out.printf("%d, ", NumericHelper.sumOfDigits(i/23));
                    long sum = NumericHelper.sumOfDigits(i/23L);
                    if ((i/23)%100 == 35) System.out.printf("%d (%d), ", i, i/23);
                    map.put(sum, map.getOrDefault(sum, 0L) + 1);
                    map1.put((i/23)%100, map1.getOrDefault((i/23)%100, 0L) + 1);

                } else {
                   // System.out.printf("%d, ", i);
                }

            }
        }
        System.out.println();
        for(Map.Entry<Long, Long> e : map.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        System.out.println();
        for(Map.Entry<Long, Long> e : map1.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        System.out.println();
        System.out.println(count);
        return "0";
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}

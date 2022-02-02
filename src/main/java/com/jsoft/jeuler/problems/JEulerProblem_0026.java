package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0026 extends EulerSolver {

    public JEulerProblem_0026(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int count = 0, n = 0;
        for(int i=2; i<=1000; i++) {
            int x = getReciprocalCycleCount(i);
            //System.out.printf("%d - %d\n", i, x);
            if (count < x) {
                count = x;
                n = i;
            }
        }

        System.out.printf("%d - %d\n", n, count);

        return Integer.toString(n);
    }

    private int getReciprocalCycleCount(int n) {
        Set<Integer> remainderSet = new HashSet();
        int nearestWholeNumber = 1;
        while(nearestWholeNumber < n) {
            nearestWholeNumber *= 10;
        }
        remainderSet.add(nearestWholeNumber/n);

        int remainder = nearestWholeNumber % n;
        while(remainder !=0 && !remainderSet.contains(remainder)) {
            remainderSet.add(remainder);
            if(remainder < nearestWholeNumber) {
                remainder = remainder * nearestWholeNumber;
            }
            remainder = remainder % n;
        }

        return remainderSet.size();
    }

    @Override
    public String getProblemStatement() {
        return "Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its decimal fraction part.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

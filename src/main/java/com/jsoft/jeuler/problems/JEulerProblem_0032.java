package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0032 extends EulerSolver {

    public JEulerProblem_0032(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int MAX = 2000;
        Set<Integer> products = new HashSet();
        for(int i=1; i<MAX; i++) {
            for(int j=1; j<MAX; j++) {
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(j);
                sb.append(i*j);
                if(sb.toString().length() < 9) continue;
                if(sb.toString().length() > 9) break;
                if(NumericHelper.isPandigital1to9(sb.toString())) {
                    //System.out.format("%d * %d = %d\n", i, j, i*j);
                    products.add(i*j);
                }
            }
        }

        return Integer.toString(products.stream().mapToInt(i -> i).sum());
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all products whose multiplicand/multiplier/product identity can be written as a 1 through 9 pandigital.";
    }
}

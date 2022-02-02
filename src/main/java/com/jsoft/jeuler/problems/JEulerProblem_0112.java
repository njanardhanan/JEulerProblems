package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0112 extends EulerSolver {

    public JEulerProblem_0112(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int count = 0;
        int ans = 0;
        for(int i=1; i<Integer.MAX_VALUE;i++){
            if(NumericHelper.isBouncyNumber(i)) {
                count++;
            }
            if((count*100/i) == 99) {
                //System.out.println(i);
                ans = i;
                break;
            }
        }
        return Integer.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=112";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("bouncy", "count", "counting");
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0038 extends EulerSolver {

    public JEulerProblem_0038(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long answer = 0;
        for(long x=1; x<10000; x++) {
            StringBuilder sb = new StringBuilder();
            for(long n=1; n<10; n++) {
                sb.append(x*n);
                if (sb.toString().length() > 9) {
                    break;
                } else if (sb.toString().length() == 9 && NumericHelper.isPandigital1to9(sb.toString())) {
                    //System.out.format("x : %d, n : %d, pandigital : %s\n", x, n, sb.toString());
                    long tmp = Long.parseLong(sb.toString());
                    if (answer < tmp) {
                        answer = tmp;
                    }
                }
            }
        }
        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "What is the largest 1 to 9 pandigital 9-digit number that can be formed as the concatenated product of an integer with (1,2, ... , n) where n > 1?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

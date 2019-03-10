package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.stream.Collectors;

public class JEulerProblem_0055 extends EulerSolver {

    public JEulerProblem_0055(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int count = 0;
        for(int i =1; i<10000; i++) {
            boolean isLychrel = true;
            BigInteger n = BigInteger.valueOf(i);
            for(int j=1; j<=50; j++) {
                BigInteger nReverse = new BigInteger(StringHelper.reverse(n.toString()));
                BigInteger m = n.add(nReverse);
                if (StringHelper.isPalindrome(m.toString())) {
                    isLychrel = false;
                    break;
                } else {
                    n = m;
                }
            }

            if (isLychrel) {
                //System.out.println(i);
                count++;
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "How many Lychrel numbers are there below ten-thousand?";
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;

public class JEulerProblem_0057 extends EulerSolver {

    public JEulerProblem_0057(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int maxSum = 0;

        //NOTE : if p/q is one convergent, then (p+2q) / (p+q) will be next.
        BigInteger p = BigInteger.valueOf(3);
        BigInteger q = BigInteger.valueOf(2);

        for(int i=0; i<1000; i++) {
            if (p.toString().length() > q.toString().length()) {
                //System.out.printf("%d : ( %s / %s )\n", i+1, p.toString(), q.toString());
                maxSum++;
            }

            //Next convergent
            BigInteger tmp = new BigInteger(p.toString());
            p = tmp.add(BigInteger.valueOf(2).multiply(q));
            q = tmp.add(q);
        }


        return Integer.toString(maxSum);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=57";
    }
}

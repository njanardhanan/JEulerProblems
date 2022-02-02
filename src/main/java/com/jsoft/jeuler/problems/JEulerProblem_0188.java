package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0188 extends EulerSolver {

    public JEulerProblem_0188(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        BigInteger a = BigInteger.valueOf(1777l);
        BigInteger limit = BigInteger.valueOf(10l).pow(8);
        BigInteger x = BigInteger.valueOf(1777l);
        //Just for optimization.
        BigInteger prevX = BigInteger.valueOf(1777l);
        for(long k=2; k<=1855l; k++) {
            x = a.modPow(x, limit);

            //Just for optimization.
            if(prevX.compareTo(x) == 0) {
                System.out.printf("No need to iterate till 1855, K : %d\n",  k);
                break;
            }
            prevX = x;
        }
        return x.toString();
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=188";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

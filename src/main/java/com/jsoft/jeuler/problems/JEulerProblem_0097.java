package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;


public class JEulerProblem_0097 extends EulerSolver {

    public JEulerProblem_0097(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long mod = 10000000000l;
        long ans = 28433l;
        for(int i=0; i<7830457; i++) {
            ans *= 2;
            ans %= mod;
        }
        ans += 1;

        //Method-2
//        BigInteger b1 = new BigInteger("28433");
//        BigInteger b2 = new BigInteger("2");
//        BigInteger b3 = b2.pow(7830457);
//        BigInteger b4 = b1.multiply(b3);
//        BigInteger b5 = b4.add(BigInteger.ONE);
//        BigInteger b6 = b5.mod(new BigInteger("10000000000"));


        return Long.toString(ans);
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=92";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

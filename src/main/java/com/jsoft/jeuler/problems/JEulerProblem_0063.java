package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0063 extends EulerSolver {

    public JEulerProblem_0063(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int count = 0;
        for(int pow=1; pow<50; pow++) {
            for(int val=1; val<50; val++) {
                BigInteger b = BigInteger.valueOf(val).pow(pow);
                int len = b.toString().length();
                if (len == pow) {
                    count++;
                    //System.out.printf("%d ^ %d = %s\n", val, pow, b.toString());
                }
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=63";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

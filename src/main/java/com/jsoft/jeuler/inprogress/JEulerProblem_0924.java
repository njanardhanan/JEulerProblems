package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0924 extends EulerSolver {

    public JEulerProblem_0924(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        BigInteger a = BigInteger.ZERO;
        BigInteger TWO = BigInteger.valueOf(2);
        BigInteger MOD = BigInteger.TEN.pow(9).add(BigInteger.valueOf(7));
        for (int i=1; i<=10; i++) {
            BigInteger b = a.pow(2).add(TWO);
            a = b;
            System.out.println(i + " : " + b + " : " + b.mod(MOD));
        }
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

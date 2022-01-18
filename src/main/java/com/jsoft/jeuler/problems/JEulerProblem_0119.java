package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.*;

public class JEulerProblem_0119 extends EulerSolver {

    public JEulerProblem_0119(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<BigInteger> list = new ArrayList();
        for(int x=2; x<=100; x++) {
            for(int y=2; y<=100; y++) {
                BigInteger b = BigInteger.valueOf(x).pow(y);
                if(NumericHelper.sumOfDigits(b.toString()) == x) {
                    list.add(new BigInteger(b.toString()));
                }
            }
        }
        int c = 1;
        Collections.sort(list);
        for(BigInteger x : list) {
            System.out.println(c++ + " : " + x.toString());
        }
        return list.get(29).toString();
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=75";
    }
}

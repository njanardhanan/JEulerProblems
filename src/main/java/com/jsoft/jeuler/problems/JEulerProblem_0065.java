package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0065 extends EulerSolver {

    public JEulerProblem_0065(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //http://oeis.org/A113873
        //Haros-Farey Series

        int LIMIT = 100 + 1;
        BigInteger TWO = new BigInteger("2");
        BigInteger THREE = new BigInteger("3");

        BigInteger[] a = new BigInteger[LIMIT + 1];
        a[0] = BigInteger.ONE;
        a[1] = BigInteger.ONE;
        a[2] = BigInteger.ONE.add(BigInteger.ONE);

        for(int n=3; n<=LIMIT; n++) {
            if(n%3 == 0) {
                //a[n] = a[n-1] + a[n-2];
                a[n] = a[n-1].add(a[n-2]);
            } else if (n%3 == 1) {
                //a[n] = 2*(n-1) * a[n-1]/3 + a[n-2];
                BigInteger x = TWO.multiply(BigInteger.valueOf(n-1)).multiply(a[n-1]).divide(THREE);
                a[n] = x.add(a[n-2]);
            } else {
                a[n] = a[n-1].add(a[n-2]);
            }
        }

        for(int n=0; n<=LIMIT; n++) {
            System.out.println(n + " : " + a[n]);
        }

        return Long.toString(NumericHelper.sumOfDigits(a[LIMIT].toString()));
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=65";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

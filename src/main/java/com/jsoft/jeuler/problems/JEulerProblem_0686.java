package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0686 extends EulerSolver {

    public JEulerProblem_0686(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        System.out.println(p(12, 1));
        System.out.println(p(12, 2));
        System.out.println(p(123, 45));
        return String.valueOf(p(123, 678910));
    }

    private int p(int L, int n) {
        int count = 0;
        int i = 0;
        int noOfDigits = String.valueOf(L).length();
        while(count < n) {
            i++;
            if(doesStartWith(i, L, noOfDigits)) {
                count++;
            }
        }
        return i;
    }

    private boolean doesStartWith(int j, int L, int noOfdigit) {
        /**
         * Based on the formula between power and log
         * a = 2^n
         * log10(a) = n * log10(2)
         *
         * by taking anti-log
         *     a  = 10^(n*log10(2))
         *
         */
        double d = Math.log10(2.0) * j;
        d = d - Math.floor(d) + noOfdigit - 1;
        int val = (int)Math.pow(10, d);
        return val == L;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=686";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

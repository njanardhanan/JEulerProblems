package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;


public class JEulerProblem_0092 extends EulerSolver {

    public JEulerProblem_0092(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 10000000;

        Map<Integer, Integer> map = new HashMap();

        //Max 2nd digit in the chain is going to be 9^2*7=567
        for(int i=1; i<=570; i++) {
            int x = getSquareOfDigits(i);
            while(true) {
                if (x == 89 || x == 1) {
                    map.put(i, x);
                    break;
                }
                x = getSquareOfDigits(x);
            }
        }

        int result = 0;
        for(int i=1; i<=LIMIT; i++) {
            int x = getSquareOfDigits(i);
            if(map.get(x) == 89) {
                result++;
            }
        }

        return Integer.toString(result);
    }

    private int getSquareOfDigits(int n){
        int res = 0;
        while(n>0) {
            int x = n%10;
            res += x*x;
            n /= 10;
        }

        return res;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=92";
    }
}

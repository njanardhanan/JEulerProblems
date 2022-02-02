package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0145 extends EulerSolver {

    public JEulerProblem_0145(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int total = 0;

        for(long i=1; i<Math.pow(10, 9); i++) {
            if(i%10 == 0) continue;
            if(isReversiableNumber(i)) {
                total++;
            }
        }
        return Long.toString(total);
    }

    public long reverseLong(long input) {
        long reversedNum = 0;
        long input_long = input;

        while (input_long != 0) {
            reversedNum = reversedNum * 10 + input_long % 10;
            input_long = input_long / 10;
        }

        if (reversedNum > Long.MAX_VALUE || reversedNum < Long.MIN_VALUE) {
            throw new IllegalArgumentException();
        }
        return reversedNum;
    }

    private boolean isReversiableNumber(long n) {
        long x = n + reverseLong(n);
        return isAllOdd(x);
    }

    private boolean isAllOdd(long n) {
        while(n > 0) {
            long d = n % 10;
            if(d%2 == 0) return false;
            n /= 10;
        }
        return true;
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=145";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0052 extends EulerSolver {

    public JEulerProblem_0052(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long n = 10;
        long ans = -1;
        while (ans == -1) {
            long tmp = n;
            int len = Long.toString(tmp).length();
            while (Long.toString(tmp*6).length() == len) {
                if(solve(tmp)) {
                    System.out.println(tmp);
                    ans = tmp;
                    break;
                } else {
                    tmp++;
                }
            }
            n = n * 10;
        }

        return "";
    }

    private boolean solve(long n) {
        if (doesContainSameDigit(2*n, 3*n)) {
            if (doesContainSameDigit(3*n, 4*n)) {
                if (doesContainSameDigit(4*n, 5*n)) {
                    if (doesContainSameDigit(5*n, 6*n)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean doesContainSameDigit(long x, long y) {
        return toDigits(x).equals(toDigits(y));
    }

    private Map<Integer, Integer> toDigits(long n) {
        Map<Integer, Integer> digits = new HashMap();
        while (n>0) {
            int x = (int)n%10;
            if (digits.containsKey(x)) {
                digits.put(x, digits.get(x) + 1);
            } else {
                digits.put(x, 1);
            }
            n = n / 10;
        }
        return digits;
    }

    @Override
    public String getProblemStatement() {
        return "Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x, contain the same digits.";
    }
}

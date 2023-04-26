package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0168 extends EulerSolver {

    public JEulerProblem_0168(int problemNumber) {
        super(problemNumber);
    }

    private static final int MOD = 100000;

    @Override
    public String solve() {
        int ans = 0;
        int LIMIT = 100;
        for (int numOfDigits=2; numOfDigits<=LIMIT; numOfDigits++) {
            for (int multipler=1; multipler<=9; multipler++) {
                for (int lastDigit=1; lastDigit<=9; lastDigit++) {
                    int num = solve(numOfDigits, multipler, lastDigit);
                    ans += num;
                    ans %= MOD;
                }
            }
        }

        return Integer.toString(ans%MOD);
    }

    private int solve(int numOfDigits, int multipler, int lastDigit) {
        int carry = 0;
        int result = lastDigit;
        int currentNum = lastDigit;
        int shift = 10;
        for (int i=1; i<numOfDigits; i++) {
            int val = (multipler * currentNum) + carry;
            currentNum = val % 10;
            carry = val / 10;

            if (shift < MOD) {
                result = (currentNum * shift) + result;
                shift *= 10;
            }
        }

        int firstDigit = (multipler * currentNum) + carry;

        if (currentNum == 0 || firstDigit != lastDigit) {
            return 0;
        }

        //System.out.printf("%d - (%d, %d, %d)\n", result, numOfDigits, multipler, lastDigit);

        return result;
    }

    @Override
    public String getProblemStatement() {
        return "Consider the number 142857. We can right-rotate this number by moving the last digit (7) to the front of it, giving us 714285.\n" +
                "It can be verified that 714285=5×142857.\n" +
                "This demonstrates an unusual property of 142857: it is a divisor of its right-rotation.\n" +
                "\n" +
                "Find the last 5 digits of the sum of all integers n, 10 < n < 10^100, that have this property.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}

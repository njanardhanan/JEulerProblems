package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0148 extends EulerSolver {

    public JEulerProblem_0148(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //This will run almost instantly
        /**
         * This is extension of Lucas theorem.
         *
         * For divisiblity of 7 in pascal triangle is given by.
         *
         * a(n) = T(n) * coefficient * 28^i
         *
         * Where:
         * T(n) is nth triangular number
         * coefficient is product of (pi+1), where pi is ith digit in n base 7.
         * 28^i : ith digit in n base 7.
         */
        int LIMIT = (int)Math.pow(10, 9);
        String base7Limit = Integer.toString(LIMIT, 7);
        long ans = 0;
        int len = base7Limit.length();
        for(int i=0; i<len; i++) {
            int x = Character.getNumericValue(base7Limit.charAt(i));
            long triangularNumber = (x * (x+1)) / 2;
            long coefficient = 1;
            for(int j=0; j<i; j++) {
                coefficient *= (Character.getNumericValue(base7Limit.charAt(j)) + 1);
            }
            ans += triangularNumber * coefficient * (long)Math.pow(28, len-1-i);
        }
        return String.valueOf(ans);
    }

    public String solveBruteForce() {
        //This will take around 2 mins.
        /**
         * This is Lucas theorem
         * https://en.wikipedia.org/wiki/Lucas%27s_theorem
         *
         */
        int LIMIT = (int)Math.pow(10, 9);
        long ans = 0;
        for (int i=0; i<LIMIT; i++) {
            if (i%10000000 == 0) {
                System.out.println("Reached " + i);
            }
            String base7 = Integer.toString(i, 7);
            long t = 1;
            for(char c : base7.toCharArray()) {
                t *= (Character.getNumericValue(c) + 1);
            }
            ans += t;
        }
        return String.valueOf(ans);
    }

    @Override
    public String getProblemStatement() {
        return "We can easily verify that none of the entries in the first seven rows of Pascal's triangle are divisible by 7:\n" +
                "\n" +
                " \t \t \t \t \t \t 1\n" +
                " \t \t \t \t \t 1\t \t 1\n" +
                " \t \t \t \t 1\t \t 2\t \t 1\n" +
                " \t \t \t 1\t \t 3\t \t 3\t \t 1\n" +
                " \t \t 1\t \t 4\t \t 6\t \t 4\t \t 1\n" +
                " \t 1\t \t 5\t \t10\t \t10\t \t 5\t \t 1\n" +
                "1\t \t 6\t \t15\t \t20\t \t15\t \t 6\t \t 1\n" +
                "However, if we check the first one hundred rows, we will find that only 2361 of the 5050 entries are not divisible by 7.\n" +
                "Find the number of entries which are not divisible by 7 in the first one billion (109) rows of Pascal's triangle.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("pascal", "triangular number", "lucas theorem");
    }
}

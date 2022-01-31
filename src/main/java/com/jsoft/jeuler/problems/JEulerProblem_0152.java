package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;
import org.apache.commons.numbers.fraction.BigFraction;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0152 extends EulerSolver {

    public JEulerProblem_0152(int problemNumber) {
        super(problemNumber);
    }

    private final BigFraction TARGET = BigFraction.of(1, 2);

    @Override
    public String solve() {

        /**
         * The ask in the problem statement is to find subsets between 2 to 80.
         * I cheated a little by looking online and skipped few numbers from the list.
         * Basically we have to skip
         *  1) Non prime numbers whose prime factor does not contain 2 or 3.
         *  2) Prime numbers greater than 13.
         */
        List<Integer> data = Arrays.asList(2,3,4,5,6,7,8,9,10,12,13,14,15,18, 20,21,24,28, 30,35,36,39, 40,42,45,52,56,60,63,70,72);
        //Note : The data needs to be in ascending order for the algo to work.
        int ans = solve(0, BigFraction.ZERO, data);
        System.out.println();
        return Integer.toString(ans);
    }

    /**
     * Algo inspired from https://www.youtube.com/watch?v=rYkfBRtMJr8
     * @param i - Current index
     * @param sum - Current running sum
     * @param data - Full data list.
     * @return - Returns the number of subset that will be have a sum equal to TARGET.
     */
    private int solve(int i, BigFraction sum, List<Integer> data) {
        if (i == data.size() && sum.compareTo(TARGET) == 0) {
            //System.out.print("*");
            return 1;
        }
        if (i == data.size()) {
            return 0;
        }
        if (sum.compareTo(TARGET) > 0) {
            return 0;
        } else if (sum.compareTo(TARGET) == 0) {
            //System.out.print("=");
            return 1;
        }

        int r = 0;
        /**
         *  We are trying to include data.get(i), before doing that
         *  check whether it is worth adding it.
         *  Since the data is in descending order, the next number after index i will be always smaller.
         *  so just add all the number after index i to the sum and skip it if the sum is less than TARGET.
         */
        if (isItWorthAddingCurrentData(sum, i, data)) {
            r += solve(i + 1, sum.add(BigFraction.of(1, data.get(i) * data.get(i))), data);
            r += solve(i+1, sum, data);
        }

        return r;
    }

    private boolean isItWorthAddingCurrentData(BigFraction sum, int index, List<Integer> data) {
        BigFraction tmp = BigFraction.of(sum.getNumerator(), sum.getDenominator());
        for (int i=index; i<data.size(); i++) {
            tmp = tmp.add(BigFraction.of(1, data.get(i) * data.get(i)));
            if (tmp.compareTo(TARGET) >= 0) {
                return true;
            }
        }
        if (tmp.compareTo(TARGET) < 0) {
            return false;
        }
        return true;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=152";
    }
}

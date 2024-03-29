package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0002 extends EulerSolver {

    public JEulerProblem_0002(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int first = 1;
        int second = 2;
        int third = 3;
        int sum = 2;

        while(true) {
            //if (third > 4000000) {
            if (third > 4000000) {
                break;
            }

            third = first + second;
            System.out.println(third);
            if (third%2 == 0) {
                sum += third;
            }

            first = second;
            second = third;
        }
        return Integer.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2, the first 10 terms will be:\n" +
                "\n" +
                "1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...\n" +
                "\n" +
                "By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }


}

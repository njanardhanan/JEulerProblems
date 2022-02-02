package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class JEulerProblem_0004 extends EulerSolver {

    public JEulerProblem_0004(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<Integer> list = new ArrayList();
        for (int i=999; i>=100; i--) {
            for (int j=999; j>=100; j--) {
                if (StringHelper.isPalindrome(Integer.toString(i*j))) {
                    list.add(i*j);
                }
            }
        }

        OptionalInt ans = list.stream().mapToInt(Integer::intValue).max();

        return Integer.toString(ans.orElse(-1));
    }

    @Override
    public String getProblemStatement() {
        return "A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.\n" +
                "\n" +
                "Find the largest palindrome made from the product of two 3-digit numbers.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}

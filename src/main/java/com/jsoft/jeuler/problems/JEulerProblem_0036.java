package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0036 extends EulerSolver {

    public JEulerProblem_0036(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long sum = 0;
        for(int i =1; i<1000000; i++) {
            if(StringHelper.isPalindrome(Integer.toString(i)) && StringHelper.isPalindrome(NumericHelper.toBinary(i))) {
                sum += i;
            }
        }
        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

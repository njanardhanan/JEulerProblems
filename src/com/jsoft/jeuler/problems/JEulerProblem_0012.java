package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Set;

public class JEulerProblem_0012 extends EulerSolver {

    public JEulerProblem_0012(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long trinangleNumber = 28;
        long k = 8;

        while(true) {
            trinangleNumber += k;
            k++;
            Set<Long> listOfDivisors = NumericHelper.getDivisors(trinangleNumber);

            if(listOfDivisors.size() > 500) {
                return Long.toString(trinangleNumber);
            }
        }

    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all the primes below two million.";
    }

}

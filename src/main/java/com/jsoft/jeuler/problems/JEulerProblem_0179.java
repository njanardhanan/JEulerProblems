package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;


public class JEulerProblem_0179 extends EulerSolver {

    public JEulerProblem_0179(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = (int)Math.pow(10, 7);
        int[] noOfDivisors = new int[LIMIT + 1];
        int count = 0;
        for(int x=1; x<=LIMIT; x++) {
            for(int y=x; y<=LIMIT; y+=x) {
                noOfDivisors[y]++;
            }
            if(noOfDivisors[x] == noOfDivisors[x-1]) {
                count++;
            }
        }

        return Integer.toString(count);
    }

    public String solve1() {
        int count = 0;

        int prevNumberOfDivisors = NumericHelper.getDivisors(2).size();
        for(int x=3; x<Math.pow(10, 7); x++) {
            int currentNumberOfDivisors = NumericHelper.getDivisors(x).size();
            if(prevNumberOfDivisors == currentNumberOfDivisors) {
                //System.out.println(x-1);
                count++;
            }
            prevNumberOfDivisors = currentNumberOfDivisors;
        }

        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=75";
    }
}

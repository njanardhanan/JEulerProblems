package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;


public class JEulerProblem_0058 extends EulerSolver {

    public JEulerProblem_0058(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int diagonalValue = 1;
        int increamentBy = 2;
        int sideLength = 1;

        int primeCount = 0;

        int diagonalSize = 1;

        double percentage;

        while(true) {
            for(int c=1; c<=4; c++) {
                diagonalSize++;
                diagonalValue += increamentBy;
                if(PrimeNumberHelper.isPrime(diagonalValue)) {
                    primeCount++;
                }
            }
            increamentBy += 2;
            sideLength += 2;

            percentage = (primeCount*100.0) / diagonalSize;
            if(percentage < 10.0) {
                break;
            }
        }

        System.out.println(percentage);

        return Integer.toString(sideLength);
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=53";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

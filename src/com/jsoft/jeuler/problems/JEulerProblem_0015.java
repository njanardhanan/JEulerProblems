package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JEulerProblem_0015 extends EulerSolver {

    public JEulerProblem_0015(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int noOfGrid = 20;

        //Also it is possible to get the answer by finding middle value for 40 row in Pascal's Triangle.
        List<Long> pascalRow = NumericHelper.getPascalRow(noOfGrid * 2);
        System.out.println("Answer by Pascal Triangle method : " + pascalRow.stream().max(Comparator.naturalOrder()).get());

        //Using combination formulae
        //C(n, r) = n! / (r! (n-r)!
        BigInteger nFact = NumericHelper.factorialBigNumber(noOfGrid * 2);
        BigInteger rFact =  NumericHelper.factorialBigNumber(noOfGrid);
        BigInteger nMinusrFact =  NumericHelper.factorialBigNumber(noOfGrid * 2 - noOfGrid);

        BigInteger answer = nFact.divide( rFact.multiply(nMinusrFact) );

        return answer.toString();
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=15";
    }
}

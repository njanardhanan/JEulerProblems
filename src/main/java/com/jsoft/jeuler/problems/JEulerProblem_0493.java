package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class JEulerProblem_0493 extends EulerSolver {

    public JEulerProblem_0493(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //System.out.println(getExpectedValueA(4, 5, 7));
        //a.bcdefghij
        DecimalFormat df = new DecimalFormat("#.#########");
        df.setRoundingMode(RoundingMode.FLOOR);
        double ans = getExpectedValueA(7, 10, 20);
        return df.format(ans);
    }

    //https://www.wolframalpha.com/input/?i=%281-+%2860+choose+20%29+%2F+%2870+choose+20%29%29+*+7
    private double getExpectedValueA(int noOfColor, int noOfBallsPerColor, int noOfBallsSelected) {
        int totalBalls = noOfColor * noOfBallsPerColor;
        double noOfFavourableOutput = 1.0 * NumericHelper.binomialCoeff(totalBalls - noOfBallsPerColor, noOfBallsSelected);
        double totalNumberOfOutput = 1.0 * NumericHelper.binomialCoeff(totalBalls, noOfBallsSelected);
        return noOfColor * ( 1.0 - noOfFavourableOutput / totalNumberOfOutput);
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

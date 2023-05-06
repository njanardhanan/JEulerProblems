package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0222 extends EulerSolver {

    public JEulerProblem_0222(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        double pipeRadius = 50.0;
        /**
         * The order of the ball is important, try with smaller set of balls.
         * Try putting the balls into the pipe with different permutation, then you can get the pattern.
         * The order of balls gives the smalles pipe length.
         */
        List<Integer> ballRadius = Arrays.asList(50, 48, 46, 44, 42, 40, 38, 36, 34, 32, 30, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49);
        double totalHeight = 0.0;
        for(int i = 0; i < ballRadius.size(); i++){
            double firstBall = (double) ballRadius.get(i);
            if (ballRadius.size() > i+1) {
                double secondBall = (double) ballRadius.get(i+1);
                System.out.printf("%f - %f\n", firstBall, secondBall);
                totalHeight += getHeight(pipeRadius, firstBall, secondBall);
            }
        }
        //Add the first ball radius
        totalHeight += ballRadius.get(0);
        //Add the last ball radius
        totalHeight += ballRadius.get(ballRadius.size() - 1);

        //mm to micrometer as per problem statement.
        totalHeight *= 1000;
        long ans = Math.round(totalHeight);
        return Long.toString(ans);
    }

    private double getHeight(double pipeRadius, double firstBallRadius, double secondBallRadius) {
        double h = firstBallRadius + secondBallRadius;
        double c = 2*pipeRadius - h;

        double d = Math.sqrt(Math.abs(h*h - c*c));
        System.out.println(d);
        return d;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=222";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("geomentry", "pythagorean");
    }
}

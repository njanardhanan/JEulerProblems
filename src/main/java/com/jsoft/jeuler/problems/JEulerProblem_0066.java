package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;


public class JEulerProblem_0066 extends EulerSolver {

    public JEulerProblem_0066(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         *
         * Similar to Problem 64
         *
         * https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Algorithm
         *
         * Refer the Pell's equation in documents folder.
         *
         * x^2 - n y^2 = 1
         *
         * Algo:
         *
         * a = 0, b = 1, c = n
         * ai = 1, bi = 0, ci = 1  => for i = 0
         *
         * iterate => qi * ai + ai-1, qi * bi + bi-1, ci+1
         *
         * with qi = Floor of SQRT(n - ci-1 * ci) + SQRT(n) / ci
         *
         * ci+1 = 2qi * SQRT(n - ci-1 * ci) + ci-1 - qi^2 * ci
         *
         *
         * Algo #2 (Which I used for this problem)
         *
         * n_i = ai * ni-1 + ni-2
         * d_i = ai * di-1 + di-2
         *
         * n(-1) = 1
         * n0 = a0
         *
         * d(-1) = 0
         * d0 = 1
         *
         *
         */

        int answer = 0;
        BigInteger xMax = BigInteger.ZERO;

        for(int n=2; n<=1000; n++) {
            int D = n;
            int limit = (int) Math.sqrt(D);

            //continue if it is perfect square.
            if(limit * limit == D) continue;

            BigInteger dBig = BigInteger.valueOf(D);

            BigInteger m = BigInteger.ZERO;
            BigInteger d = BigInteger.ONE;
            BigInteger a = BigInteger.valueOf(limit);

            BigInteger numMinusOne = BigInteger.ONE;
            BigInteger denomMinusOne = BigInteger.ZERO;

            BigInteger numZero = a;
            BigInteger denomZero = BigInteger.ONE;

            //Calculate the equation.
            BigInteger xSquare = numZero.pow(2);
            BigInteger ySquare = denomZero.pow(2);
            BigInteger xSquareMinusDintoYSquare = xSquare.subtract(dBig.multiply(ySquare));

            while (xSquareMinusDintoYSquare.intValue() != 1) {
                //m = d * a - m;
                m = d.multiply(a).subtract(m);
                //d = (D - (m * m)) / d;
                d = dBig.subtract(m.pow(2)).divide(d);
                //a = (limit + m) / d;
                a = BigInteger.valueOf(limit).add(m).divide(d);

                BigInteger num = numMinusOne;
                numMinusOne = numZero;

                BigInteger denom = denomMinusOne;
                denomMinusOne = denomZero;

                numZero = a.multiply(numMinusOne).add(num);
                denomZero = a.multiply(denomMinusOne).add(denom);

                //Calculate the equation.
                xSquare = numZero.pow(2);
                ySquare = denomZero.pow(2);
                xSquareMinusDintoYSquare = xSquare.subtract(dBig.multiply(ySquare));

            }

            if(xMax.compareTo(numZero) == -1) {
                xMax = new BigInteger(numZero.toString());
                answer = n;
            }

        }

        System.out.printf("D : %d, x : %s\n", answer, xMax.toString());

        return Integer.toString(0);
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=75";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}

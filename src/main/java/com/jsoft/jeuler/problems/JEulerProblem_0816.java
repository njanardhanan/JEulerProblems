package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.ClosestPair;
import com.jsoft.jeuler.helper.Point2D;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0816 extends EulerSolver {

    public JEulerProblem_0816(int problemNumber) {
        super(problemNumber);
    }

    BigInteger MOD = BigInteger.valueOf(50515093);
    BigInteger TWO = BigInteger.valueOf(2);

    @Override
    public String solve() {
        /**
         * This problem uses Closest pair of points with divide-and-conquer algorithms.
         * I used the code provided in algo.jar by Priceton university.
         *
         * Refer : https://algs4.cs.princeton.edu/code/
         */
        int target = 2000000;
        //int target = 14;
        Point2D[] p = generatePoints(target);
        ClosestPair closest = new ClosestPair(p);
        System.out.println(closest.distance() + " from " + closest.either() + " to " + closest.other());

        DecimalFormat df = new DecimalFormat("#.#########");

        //20.8806130188211
        return df.format(closest.distance());
    }

    private Point2D[] generatePoints(int size) {
        Point2D[] points = new Point2D[size];

        int[] s = new int[size*2];
        s[0] = 290797;
        for (int i=1; i<size*2; i++) {
            s[i] = calculateNextS(s[i-1]);
        }

        for(int i=0; i<size; i++) {
            points[i] = new Point2D(s[2*i], s[2*i + 1]);
        }

        return points;

    }

    private int calculateNextS(int x) {
        BigInteger s = BigInteger.valueOf(x);
        int r = s.modPow(TWO, MOD).intValue();
        return r;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=816" +
                "" +
                "<p>We create an array of points  $P_n$ in a two dimensional plane using the following random number generator:<br />\n" +
                "$s_0=290797$<br />\n" +
                "$s_{n+1}={s_n}^2 \\bmod 50515093$\n" +
                "<br /> <br />\n" +
                "$P_n=(s_{2n},s_{2n+1})$</p>\n" +
                "<p>\n" +
                "Let $d(k)$  be the shortest distance of any two (distinct) points among $P_0, \\cdots, P_{k - 1}$.<br />\n" +
                "E.g. $d(14)=546446.466846479$\n" +
                "</p>\n" +
                "<p>\n" +
                "Find $d(2000000)$. Give your answer rounded to 9 places after the decimal point.\n" +
                "</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("Closest pair", "divide and conquer", "algorithm");
    }
}

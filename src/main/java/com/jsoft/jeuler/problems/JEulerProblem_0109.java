package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0109 extends EulerSolver {

    public JEulerProblem_0109(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         *   1. The player uses three dart to game, and the score after playing three darts is called checkout.
         *   2. As per the problem statement, the third must land on a double, it can be a red/green outer ring,
         *   or inner bull.
         *   3. We need to find how many distinct way a player can checkout (score after playing three darts) less than 100.
         *
         *   Solution:
         *   1. List out all possible ways to score.
         *   2. Calculate the checkout score after playing three darts.
         *   3. There only three cases.
         *
         *      Dart1  Dart2  Dart3
         *      MISS   MISS   DOUBLE
         *      MISS   HIT   DOUBLE
         *      HIT    HIT   DOUBLE
         *
         */

        int LIMIT = 100;
        int ans = 0;
        List<Integer> scores = new ArrayList<>();

        //Black and Cream scores
        for (int i = 1; i <= 20; i++) {
            scores.add(i);
        }

        //red/green outer ring score double
        for (int i = 1; i <= 20; i++) {
            scores.add(2 * i);
        }

        //red/green inner ring score treble.
        for (int i = 1; i <= 20; i++) {
            scores.add(3 * i);
        }

        // At the centre of the board are two concentric circles called the bull region, or bulls-eye.
        // The outer bull is worth 25 points and the inner bull is a double, worth 50 points.
        scores.add(25);
        scores.add(50);

        //make all the possible doubles
        List<Integer> doubles = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            doubles.add(2 * i);
        }
        doubles.add(25 * 2);

        //Calculate the checkout score, for the case MISS, MISS AND DOUBLE
        for (int thirdDart : doubles) {
            if (thirdDart < LIMIT)
                ans++;
        }

        //Calculate the checkout score, for the case MISS, HIT AND DOUBLE
        for (int secondDart : scores) {
            for (int thirdDart : doubles) {
                if (secondDart + thirdDart < LIMIT)
                    ans++;
            }
        }

        //Calculate the checkout score, for the case MISS, HIT AND DOUBLE
        for (int i=0; i<scores.size(); i++) {
            for (int j=i; j<scores.size(); j++) {
                for (int thirdDart : doubles) {
                    if (scores.get(i) + scores.get(j) + thirdDart < LIMIT)
                        ans++;
                }
            }
        }


        return Integer.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=109\n";
    }
}
